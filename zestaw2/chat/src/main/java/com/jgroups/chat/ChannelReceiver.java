package com.jgroups.chat;

import com.google.common.eventbus.EventBus;
import com.google.protobuf.InvalidProtocolBufferException;
import com.jgroups.chat.messages.RegularChatMessage;
import com.jgroups.chat.operations.ChatOperationProtos.ChatMessage;
import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

/**
 * Created by novy on 25.03.15.
 */
public class ChannelReceiver extends ReceiverAdapter {

    private final String channel;
    private final EventBus eventBus;

    public ChannelReceiver(String channel, EventBus eventBus) {
        this.channel = channel;
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    @Override
    public void receive(Message msg) {
        final Address messageSource = msg.getSrc();
        try {
            final ChatMessage message = ChatMessage.parseFrom(msg.getBuffer());
            eventBus.post(
                    new RegularChatMessage(channel, messageSource.toString(), message.getMessage())
            );
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
