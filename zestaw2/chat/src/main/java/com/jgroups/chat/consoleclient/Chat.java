package com.jgroups.chat.consoleclient;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.jgroups.chat.*;
import com.jgroups.chat.operations.ChatOperationProtos;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by novy on 25.03.15.
 */
public class Chat {
    // todo: so fucking ugly
    private final EventBus globalEventBus = new EventBus();
    private final Map<String, STDOutMessageWriter> channelWriters = Maps.newHashMap();
    private static final int GETTING_STATE_TIMEOUT = 0;
    private static final String CHAT_MANAGEMENT_CHANNEL_NAME = "ChatManagement768264";
    private static Scanner scanner = new Scanner(System.in);
    private String userName;
    private ChatManager chatManager;
    private Map<String, JChannel> connectedChannels = new HashMap<String, JChannel>();
    private State state = new State();

    public Chat(String userName) {
        this.userName = userName;
    }

    public static void main(String[] args) {
        System.out.println("Type your nick:");
        String userName = scanner.nextLine();
        while (userName.length() > 20) {
            System.out.println("Your nick is too long (max 20 characters length). Please try again:");
            userName = scanner.next();
        }

        Chat chat = new Chat(userName);

        try {
            chat.initManagementChannelAndReceiver();
            while (true) {
                System.out.println("Choose action:\n1 - join channel\n" +
                        "2 - show channels\n3 - send message\n[others] - quit");

                int chosenOperation = scanner.nextInt();
                scanner.nextLine();
                switch (chosenOperation) {
                    case 1:
                        chat.joinChannel();
                        break;
                    case 2:
                        chat.showChannels();
                        break;
                    case 3:
                        chat.sendMessage();
                        break;
                    default:
                        throw new Exception();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ending...");
            chat.cleanUp();
        }
    }

    private void initManagementChannelAndReceiver() throws Exception {
        final ManagementChannelReceiver managementChannelReceiver = new ManagementChannelReceiver(state, globalEventBus);
        JChannel managerChannel = buildChannel(userName, managementChannelReceiver);
        managerChannel.connect(CHAT_MANAGEMENT_CHANNEL_NAME);
        managerChannel.getState(null, GETTING_STATE_TIMEOUT);
        chatManager = new ChatManager(managerChannel);
    }

    private void joinChannel() {
        try {
            System.out.println("Type channel name:");
            String channelName = scanner.nextLine();

            if (connectedChannels.containsKey(channelName)) {
                System.out.println("Already connected to that channel.");
                return;
            }
            JChannel channel = buildChannel(userName, channelName, new ChannelReceiver(channelName, globalEventBus));
            channelWriters.put(channelName, new STDOutMessageWriter(channelName, globalEventBus));
            channel.connect(channelName);
            chatManager.sendJoinMessage(userName, channelName);
            connectedChannels.put(channelName, channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JChannel buildChannel(String channelName, String hostName, Receiver receiver) throws Exception {
        return JChannelFactory.channelFor(channelName, hostName, receiver);
    }

    private JChannel buildChannel(String channelName, Receiver receiver) throws Exception {
        return JChannelFactory.channelFor(channelName, receiver);
    }


    private void showChannels() {
        for (Map.Entry<String, Collection<String>> actionsEntry : state.channelsWithUsers().entrySet()) {
            System.out.println("Channel: " + actionsEntry.getKey());
            for (String nick : actionsEntry.getValue()) {
                System.out.println("\t" + nick);
            }
        }
    }


    private void sendMessage() {
        System.out.println("Type channel name");
        String channelName = scanner.nextLine();
        if (channelName.isEmpty() || !connectedChannels.containsKey(channelName)) {
            System.out.println("Unknown channel");
            return;
        }

        System.out.println("Type message:");
        String msgContent = scanner.nextLine();
        while (msgContent.isEmpty()) {
            System.out.println("Cannot send empty message!");
            msgContent = scanner.nextLine();
        }

        Message message = new Message(null, null,
                ChatOperationProtos.ChatMessage.newBuilder().setMessage(msgContent).build().toByteArray()
        );

        try {
            connectedChannels.get(channelName).send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanUp() {
        for (Map.Entry<String, JChannel> channel : connectedChannels.entrySet()) {
            try {
                chatManager.sendLeaveMessage(userName, channel.getKey());
                channel.getValue().close();
                channelWriters.remove(channel.getKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            chatManager.cleanUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
