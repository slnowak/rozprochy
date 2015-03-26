package com.jgroups.chat.gui.channellist;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jgroups.chat.gui.AbstractPresenter;
import com.jgroups.chat.gui.common.events.ConnectToChannelRequestedEvent;
import com.jgroups.chat.messages.ChatViewChanged;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by novy on 26.03.15.
 */
public class ChannelListViewPresenter extends AbstractPresenter<ChannelListView> {

    private final EventBus eventBus;

    public ChannelListViewPresenter(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    public void handleConnectToChannelButtonClicked() {
        view.askForChannelToConnect();
    }

    public void handleChannelTyped(String channel) {
        // todo: to some kind of checking
        eventBus.post(new ConnectToChannelRequestedEvent(channel));
    }

    @Subscribe
    public void on(ChatViewChanged chatViewChanged) {
        // todo: fix later!
        final Map<String, Collection<String>> channelsWithUsers = chatViewChanged.newChatView();
        String result = "";
        for (Map.Entry<String, Collection<String>> channel: channelsWithUsers.entrySet()) {
            result += channel.getKey() + "\n";
            result += channel.getValue()
                    .stream()
                    .map(user -> "\t-" + user + "\n")
                    .collect(Collectors.joining());
            result += "\n";
        }

        view.renderView(StringUtils.stripEnd(result, "\n\n"));
    }
}
