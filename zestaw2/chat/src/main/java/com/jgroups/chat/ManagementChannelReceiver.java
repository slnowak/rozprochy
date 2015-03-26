package com.jgroups.chat;

import com.google.common.eventbus.EventBus;
import com.google.protobuf.InvalidProtocolBufferException;
import com.jgroups.chat.messages.ChatViewChanged;
import com.jgroups.chat.messages.UserJoinMessage;
import com.jgroups.chat.messages.UserQuitMessage;
import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.jgroups.chat.operations.ChatOperationProtos.ChatAction;
import static com.jgroups.chat.operations.ChatOperationProtos.ChatAction.ActionType;
import static com.jgroups.chat.operations.ChatOperationProtos.ChatAction.parseFrom;
import static com.jgroups.chat.operations.ChatOperationProtos.ChatState;


public class ManagementChannelReceiver extends ReceiverAdapter {

    private final State state;
    private final EventBus eventBus;

    public ManagementChannelReceiver(State state, EventBus eventBus) {
        this.state = state;
        this.eventBus = eventBus;
    }

    @Override
    public void viewAccepted(View newView) {
        state.retainOnly(
                newView.getMembers()
                        .stream()
                        .map(Address::toString)
                        .collect(Collectors.toList())
        );

        eventBus.post(
                new ChatViewChanged(state.channelsWithUsers())
        );
    }

    @Override
    public void receive(Message msg) {
        try {

            final ChatAction chatAction = parseFrom(msg.getBuffer());
            final String channel = chatAction.getChannel();
            final String nickname = chatAction.getNickname();

            if (chatAction.getAction() == ActionType.JOIN) {
                state.registerUser(channel, nickname);
                eventBus.post(
                        new UserJoinMessage(channel, nickname)
                );
            } else {
                state.unregisterUser(channel, nickname);
                eventBus.post(
                        new UserQuitMessage(channel, nickname)
                );
            }

            eventBus.post(
                    new ChatViewChanged(state.channelsWithUsers())
            );
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        final ChatState chatState = ChatState
                .newBuilder()
                .addAllState(state.allActions())
                .build();

        output.write(
                chatState.toByteArray()
        );
    }

    @Override
    public void setState(InputStream input) throws Exception {
        Collection<ChatAction> allActions = ChatState.parseFrom(input).getStateList();
        state.update(allActions);
    }
}
