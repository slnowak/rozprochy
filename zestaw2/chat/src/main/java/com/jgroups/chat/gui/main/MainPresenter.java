package com.jgroups.chat.gui.main;

import com.google.common.eventbus.EventBus;
import com.jgroups.chat.gui.AbstractPresenter;
import com.jgroups.chat.gui.channellist.ChannelListViewPresenter;
import com.jgroups.chat.gui.common.events.NickChosenEvent;
import com.jgroups.chat.gui.tabbedchat.TabbedPaneChatPresenter;
import javafx.application.Platform;

/**
 * Created by novy on 26.03.15.
 */
public class MainPresenter extends AbstractPresenter<MainView> {

    private final EventBus eventBus;
    private final TabbedPaneChatPresenter tabbedPaneChatPresenter;
    private final ChannelListViewPresenter channelListViewPresenter;

    public MainPresenter(EventBus eventBus, TabbedPaneChatPresenter tabbedPaneChatPresenter, ChannelListViewPresenter channelListViewPresenter) {
        this.eventBus = eventBus;
        this.tabbedPaneChatPresenter = tabbedPaneChatPresenter;
        this.channelListViewPresenter = channelListViewPresenter;
    }

    public TabbedPaneChatPresenter tabbedPaneChatViewPresenter() {
        return tabbedPaneChatPresenter;
    }

    public ChannelListViewPresenter channelListViewPresenter() {
        return channelListViewPresenter;
    }

    public void handleApplicationStarted() {
        view.askForNickname();
    }

    public void handleNicknameTyped(String nickname) {
        eventBus.post(new NickChosenEvent(nickname));
    }

    public void handleNicknameNotTyped() {
        // ugh...
        Platform.exit();
    }
}
