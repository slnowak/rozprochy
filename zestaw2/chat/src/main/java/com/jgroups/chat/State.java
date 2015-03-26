package com.jgroups.chat;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jgroups.chat.operations.ChatOperationProtos.ChatAction;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by novy on 25.03.15.
 */
public class State {

    private Set<ChatAction> state = Sets.newConcurrentHashSet();

    State(Set<ChatAction> state) {
        this.state = Sets.newConcurrentHashSet(state);
    }

    public void retainOnly(final Collection<String> members) {
        state.removeIf(
                chatAction -> !members.contains(chatAction.getNickname())
        );
    }

    public Collection<String> usersOn(String channel) {
        final List<String> usersAsList = Lists.newArrayList(
                state
                        .stream()
                        .filter(chatAction -> chatAction.getChannel().equals(channel))
                        .map(ChatAction::getNickname)
                        .collect(Collectors.toList())
        );

        Collections.sort(usersAsList);

        return usersAsList;
    }

    public void registerUser(String channel, String username) {
        state.add(
                ChatAction
                        .newBuilder()
                        .setChannel(channel)
                        .setNickname(username)
                        .setAction(ChatAction.ActionType.JOIN)
                        .build()
        );
    }

    public void unregisterUser(String channel, String username) {
        state.removeIf(
                chatAction -> chatAction.getChannel().equals(channel) && chatAction.getNickname().equals(username)
        );
    }

    public Collection<ChatAction> allActions() {
        return Collections.unmodifiableCollection(state);
    }

    public void update(Collection<ChatAction> bulkUpdate) {
        state.addAll(bulkUpdate);
    }
}




