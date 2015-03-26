package com.jgroups.chat.messages;

import java.util.Collection;
import java.util.Map;

/**
 * Created by novy on 26.03.15.
 */
public class ChatViewChanged {

    public ChatViewChanged(Map<String, Collection<String>> newChatView) {
        this.newChatView = newChatView;
    }

    private final Map<String, Collection<String>> newChatView;

    public Map<String, Collection<String>> newChatView() {
        return newChatView;
    }
}
