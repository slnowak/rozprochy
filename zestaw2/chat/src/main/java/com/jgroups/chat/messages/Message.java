package com.jgroups.chat.messages;

/**
 * Created by novy on 26.03.15.
 */
public interface Message {

    String channel();
    String nickname();
}
