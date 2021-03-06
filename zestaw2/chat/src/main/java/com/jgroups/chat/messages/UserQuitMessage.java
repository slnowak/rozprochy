package com.jgroups.chat.messages;

/**
 * Created by novy on 26.03.15.
 */
public class UserQuitMessage implements Message {

    private final String channel;
    private final String nickname;

    public UserQuitMessage(String channel, String nickname) {
        this.channel = channel;
        this.nickname = nickname;
    }

    @Override
    public String channel() {
        return channel;
    }

    @Override
    public String nickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "<" + nickname + "> has quit!";
    }
}
