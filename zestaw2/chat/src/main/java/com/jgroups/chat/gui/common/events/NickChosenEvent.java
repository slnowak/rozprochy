package com.jgroups.chat.gui.common.events;

/**
 * Created by novy on 27.03.15.
 */
public class NickChosenEvent {

    private final String nickname;

    public NickChosenEvent(String nickname) {
        this.nickname = nickname;
    }

    public String nickname() {
        return nickname;
    }
}
