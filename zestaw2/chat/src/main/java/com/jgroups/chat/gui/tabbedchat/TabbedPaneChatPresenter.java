package com.jgroups.chat.gui.tabbedchat;

import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jgroups.chat.*;
import com.jgroups.chat.gui.AbstractPresenter;
import com.jgroups.chat.gui.common.events.ConnectToChannelRequestedEvent;
import com.jgroups.chat.gui.common.events.NickChosenEvent;
import com.jgroups.chat.gui.tabbedchat.tab.ChannelTab;
import com.jgroups.chat.gui.tabbedchat.tab.ChannelTabPresenter;
import org.jgroups.JChannel;
import org.jgroups.Receiver;

import java.util.Map;

/**
 * Created by novy on 26.03.15.
 */
public class TabbedPaneChatPresenter extends AbstractPresenter<TabbedPaneChatView> {

    private static final int GETTING_STATE_TIMEOUT = 0;
    private static final String CHAT_MANAGEMENT_CHANNEL_NAME = "ChatManagement768264";
    private String nickname = "undefined";

    private final EventBus eventBus;
    private ChatManager manager;
    private final Map<String, ChannelTabPresenter> channelTabPresenters = Maps.newHashMap();

    public TabbedPaneChatPresenter(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    @Subscribe
    public void on(NickChosenEvent event) throws Exception {
        nickname = event.nickname();
        manager = chatManagerFor(nickname);
    }

    @Subscribe
    public void on(ConnectToChannelRequestedEvent event) {
        final String channelName = event.channel();

        final EventBus channelEventBus = new EventBus();
        final Receiver channelMessageReceiver = channelReceiverFor(channelName, channelEventBus);

        try {
            final JChannel channel = buildChannel(nickname, channelName, channelMessageReceiver);
            channel.connect(channelName);
            final ChannelTabPresenter channelTabPresenter = tabPresenterFor(eventBus, channelEventBus, channel);

            channelTabPresenters.put(channelName, channelTabPresenter);

            final ChannelTab channelTab = new ChannelTab(event.channel(), channelTabPresenter);
            view.addTab(channelTab);
            manager.sendJoinMessage(nickname, channelName);
        } catch (Exception e) {
            e.printStackTrace();
            view.showErrorDialog(
                    "Error",
                    "Couldn't connect!"
            );
        }
    }

    private Receiver channelReceiverFor(String channelName, EventBus eventBus) {
        return new ChannelReceiver(channelName, eventBus);
    }

    private ChannelTabPresenter tabPresenterFor(EventBus managementEventBus, EventBus channelEventBus, JChannel channel) {
        return new ChannelTabPresenter(managementEventBus, channelEventBus, channel);
    }

    private ChatManager chatManagerFor(String nickname) throws Exception {
        final State state = new State();
        final ManagementChannelReceiver managementChannelReceiver = new ManagementChannelReceiver(state, eventBus);
        final JChannel managerChannel = buildChannel(nickname, managementChannelReceiver);
        managerChannel.connect(CHAT_MANAGEMENT_CHANNEL_NAME);
        managerChannel.getState(null, GETTING_STATE_TIMEOUT);
        return new ChatManager(managerChannel);
    }

    private JChannel buildChannel(String channelName, Receiver receiver) throws Exception {
        return JChannelFactory.channelFor(channelName, receiver);
    }

    private JChannel buildChannel(String channelName, String hostName, Receiver receiver) throws Exception {
        return JChannelFactory.channelFor(channelName, hostName, receiver);
    }

    public void tabClosed(String channelName) {
        channelTabPresenters.remove(channelName);
        try {
            manager.sendLeaveMessage(nickname, channelName);
        } catch (Exception e) {
            view.showErrorDialog(
                    "Error",
                    "There was an error leaving!"
            );
            e.printStackTrace();
        }
    }
}
