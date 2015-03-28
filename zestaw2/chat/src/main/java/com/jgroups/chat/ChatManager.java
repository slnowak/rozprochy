package com.jgroups.chat;

import com.jgroups.chat.operations.ChatOperationProtos.ChatAction;
import com.jgroups.chat.operations.ChatOperationProtos.ChatAction.ActionType;
import org.jgroups.JChannel;
import org.jgroups.Message;

/**
 * Created by novy on 25.03.15.
 */
public class ChatManager {

    private JChannel chatManagementChannel;

    public ChatManager(JChannel chatManagementChannel) {
        this.chatManagementChannel = chatManagementChannel;
    }

    public void cleanUp() {
        if (chatManagementChannel != null) {
            chatManagementChannel.close();
        }
    }

    public void sendJoinMessage(String userName, String channelName) throws Exception {
        sendInfo(userName, channelName, ActionType.JOIN);
    }

    public void sendLeaveMessage(String userName, String channelName) throws Exception {
        sendInfo(userName, channelName, ActionType.LEAVE);
    }

    private void sendInfo(String userName, String channelName, ActionType actionType) throws Exception {
        final ChatAction action = ChatAction
                .newBuilder()
                .setNickname(userName)
                .setChannel(channelName)
                .setAction(actionType)
                .build();

        final Message message = new Message(null, null, action.toByteArray());

        chatManagementChannel.send(message);
    }
}