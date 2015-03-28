package com.jgroups.chat.gui.channellist;

import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jgroups.chat.gui.AbstractPresenter;
import com.jgroups.chat.gui.common.events.ConnectToChannelRequestedEvent;
import com.jgroups.chat.gui.common.events.NickChosenEvent;
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

    private final Collection<String> channels = Sets.newHashSet();
    private String nickname;

    public ChannelListViewPresenter(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    public void handleConnectToChannelButtonClicked() {
        view.askForChannelToConnect();
    }

    public void handleChannelTyped(String channel) {
        if (alreadyConnectedTo(channel)) {
            view.showErrorDialog(
                    "Error",
                    "Already connected to channel " + channel + "."
            );
        } else {
            eventBus.post(new ConnectToChannelRequestedEvent(channel));
            channels.add(channel);
        }
    }

    private boolean alreadyConnectedTo(String aChannel) {
        return channels.contains(aChannel);
    }

    @Subscribe
    public void on(NickChosenEvent event) throws Exception {
        nickname = event.nickname();
    }

    @Subscribe
    public void on(ChatViewChanged chatViewChanged) {
        replaceConnectedChannelsWith(chatViewChanged.newChatView());
        // todo: fix later!
        final Map<String, Collection<String>> channelsWithUsers = chatViewChanged.newChatView();
        String result = "";
        for (Map.Entry<String, Collection<String>> channel : channelsWithUsers.entrySet()) {
            result += channel.getKey() + "\n";
            result += channel.getValue()
                    .stream()
                    .map(user -> "\t-" + user + "\n")
                    .collect(Collectors.joining());
            result += "\n";
        }

        view.renderView(StringUtils.stripEnd(result, "\n\n"));
    }

    private void replaceConnectedChannelsWith(Map<String, Collection<String>> channelsWithUsers) {
        channels.clear();
        channelsWithUsers.forEach(
                (channel, nicknames) -> {
                    if (nicknames.contains(nickname)) {
                        channels.add(channel);
                    }
                });

    }
}
