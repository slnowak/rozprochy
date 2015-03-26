package com.jgroups.chat.gui.channellist;

import com.google.common.eventbus.EventBus;
import com.jgroups.chat.gui.AbstractPresenter;

/**
 * Created by novy on 26.03.15.
 */
public class ChannelListViewPresenter extends AbstractPresenter<ChannelListView> {

    private ChannelListView view;
    private final EventBus eventBus;

    public ChannelListViewPresenter(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    public void handleConnectToChannelButtonClicked() {
        view.askForChannelToConnect();
    }

    public void view(ChannelListView view) {
        this.view = view;
    }

    public void handleChannelTyped(String channel) {
        // todo: to some kind of checking
//        eventBus.post(new );
    }
}
