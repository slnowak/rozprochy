package com.jgroups.chat.gui.tabbedchat;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jgroups.chat.gui.AbstractPresenter;
import com.jgroups.chat.gui.common.events.ConnectToChannelRequestedEvent;
import com.jgroups.chat.gui.tabbedchat.tab.ChannelTab;
import com.jgroups.chat.gui.tabbedchat.tab.ChannelTabPresenter;

/**
 * Created by novy on 26.03.15.
 */
public class TabbedPaneChatPresenter extends AbstractPresenter<TabbedPaneChatView> {

    private final EventBus eventBus;

    public TabbedPaneChatPresenter(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    @Subscribe
    public void on(ConnectToChannelRequestedEvent event) {
        // todo: temporary
        final ChannelTabPresenter channelTabPresenter = new ChannelTabPresenter(eventBus, new EventBus());
        final ChannelTab channelTab = new ChannelTab(event.channel(), channelTabPresenter);
        view.addTab(channelTab);
    }
}
