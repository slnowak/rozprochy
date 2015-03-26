package com.jgroups.chat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.jgroups.chat.operations.ChatOperationProtos.ChatAction;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StateTest {

    @Test
    public void shouldRegisterUserToExistingChannel() throws Exception {

        final State objectUnderTest = new State(
                ImmutableSet.of(
                        actionFrom("ch1", "user1"), actionFrom("ch1", "user2")
                )
        );

        objectUnderTest.registerUser("ch1", "user3");

        assertThat(objectUnderTest.usersOn("ch1")).containsExactly("user1", "user2", "user3");
    }

    @Test
    public void shouldRegisterClientToNewChannel() throws Exception {

        final State objectUnderTest = new State(
                ImmutableSet.of(
                        actionFrom("ch1", "user1")
                )
        );

        objectUnderTest.registerUser("ch2", "user1");

        assertThat(objectUnderTest.usersOn("ch2")).containsExactly("user1");
    }

    @Test
    public void shouldUnregisterClientFromChannel() throws Exception {

        final State objectUnderTest = new State(
                ImmutableSet.of(
                        actionFrom("ch1", "user1"), actionFrom("ch1", "user2")
                )
        );

        objectUnderTest.unregisterUser("ch1", "user1");

        assertThat(objectUnderTest.usersOn("ch1")).containsExactly("user2");
    }

    @Test
    public void shouldDeleteUsersFromChannelsIfNecessary() throws Exception {

        final State objectUnderTest = new State(
                ImmutableSet.of(
                        actionFrom("ch1", "user1"), actionFrom("ch1", "user2"),
                        actionFrom("ch2", "user1"), actionFrom("ch2", "user3")
                )
        );

        objectUnderTest.retainOnly(ImmutableList.of("user2", "user3"));

        assertThat(objectUnderTest.usersOn("ch1")).containsExactly("user2");
        assertThat(objectUnderTest.usersOn("ch2")).containsExactly("user3");
    }

    @Test
    public void shouldBeAbleToUpdate() throws Exception {

        final State objectUnderTest = new State(
                ImmutableSet.of(
                        actionFrom("ch1", "user1"), actionFrom("ch1", "user2"),
                        actionFrom("ch2", "user1"), actionFrom("ch2", "user3")
                )
        );

        objectUnderTest.update(
                ImmutableList.of(
                        actionFrom("ch1", "user1"), actionFrom("ch1", "user4"),
                        actionFrom("ch2", "user1"), actionFrom("ch2", "user5")
                )
        );

        assertThat(objectUnderTest.usersOn("ch1")).containsExactly("user1", "user2", "user4");
        assertThat(objectUnderTest.usersOn("ch2")).containsExactly("user1", "user3", "user5");

    }

    @Test
    public void shouldReturnChannelWithUsers() throws Exception {
        final State objectUnderTest = new State(
                ImmutableSet.of(
                        actionFrom("ch1", "user2"), actionFrom("ch1", "user1"),
                        actionFrom("ch2", "user1"), actionFrom("ch2", "user3")
                )
        );


        final Map<String, Collection<String>> expectedResult = ImmutableMap.<String, Collection<String>>of(
                "ch1", ImmutableSet.of("user1", "user2"),
                "ch2", ImmutableSet.of("user1", "user3")
        );
        final Map<String, Collection<String>> actualResult = objectUnderTest.channelsWithUsers();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void shouldBeAbleToRetrieveActionsFromAllChannels() throws Exception {
        final State objectUnderTest = new State(
                ImmutableSet.of(
                        actionFrom("ch1", "user2"), actionFrom("ch1", "user1"),
                        actionFrom("ch2", "user1"), actionFrom("ch2", "user3")
                )
        );


        final Collection<ChatAction> expectedResult = ImmutableList.of(
                actionFrom("ch1", "user1"), actionFrom("ch1", "user2"),
                actionFrom("ch2", "user1"), actionFrom("ch2", "user3")
        );
        final Collection<ChatAction> actualResult = objectUnderTest.allActions();

        assertThat(actualResult).containsAll(expectedResult);
        assertThat(actualResult).hasSameSizeAs(expectedResult);

    }

    private ChatAction actionFrom(String channel, String nickname) {
        return ChatAction
                .newBuilder()
                .setChannel(channel)
                .setNickname(nickname)
                .setAction(ChatAction.ActionType.JOIN)
                .build();
    }
}