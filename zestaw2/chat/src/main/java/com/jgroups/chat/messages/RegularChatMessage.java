package com.jgroups.chat.messages;

/**
 * Created by novy on 25.03.15.
 */
public class RegularChatMessage implements Message {

    private final String channel;
    private final String nickname;
    private final String content;

    public RegularChatMessage(String channel, String nickname, String content) {
        this.channel = channel;
        this.nickname = nickname;
        this.content = content;
    }

    @Override
    public String channel() {
        return channel;
    }

    @Override
    public String nickname() {
        return nickname;
    }

    public String content() {
        return content;
    }

    @Override
    public String toString() {
        return "<" + nickname + ">: " + content;
    }
}
