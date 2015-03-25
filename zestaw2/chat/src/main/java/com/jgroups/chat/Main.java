package com.jgroups.chat;


import com.jgroups.chat.operations.ChatOperationProtos;

/**
 * Created by novy on 23.03.15.
 */
public class Main {

    public static void main(String[] args) {
        ChatOperationProtos.ChatMessage message = ChatOperationProtos.ChatMessage.newBuilder()
                .setMessage("simple message")
                .build();

        System.out.println(message);
    }
}
