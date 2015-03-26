package com.jgroups.chat.gui.channellist;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.EventBus;
import com.jgroups.chat.messages.ChatViewChanged;
import org.junit.Test;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ChannelListViewPresenterTest {

    @Test
    public void shouldProperlyParseChannelStateAndReRenderView() throws Exception {

        final EventBus eventBusMock = mock(EventBus.class);
        final ChannelListView viewMock = mock(ChannelListView.class);
        final ChannelListViewPresenter objectUnderTest = new ChannelListViewPresenter(eventBusMock);
        objectUnderTest.view(viewMock);

        final ChatViewChanged chatViewChangedEvent = new ChatViewChanged(
                ImmutableMap.<String, Collection<String>>of(
                        "channel1", ImmutableList.of("user1", "user2"),
                        "channel2", ImmutableList.of("user1", "user2", "user3")
                )
        );
        final String parsedViewStateToRender =
                "channel1" + "\n" +
                        "\t-user1\n" + "\t-user2\n" + "\n" +
                "channel2" + "\n" +
                        "\t-user1\n" + "\t-user2\n" + "\t-user3";
        objectUnderTest.on(chatViewChangedEvent);

        verify(viewMock, times(1)).renderView(parsedViewStateToRender);

    }
}