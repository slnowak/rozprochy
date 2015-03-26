package com.jgroups.chat.gui.main;

import com.jgroups.chat.gui.channellist.ChannelListView;
import com.jgroups.chat.gui.tabbedchat.TabbedPaneChatView;
import javafx.scene.layout.HBox;

/**
 * Created by novy on 26.03.15.
 */
public class MainView extends HBox {

    private final MainPresenter presenter;

    private TabbedPaneChatView tabbedPaneChatView;
    private ChannelListView channelListView;

    public MainView(MainPresenter presenter) {
        this.presenter = presenter;
        presenter.view(this);
        initializeView();
    }

    private void initializeView() {
        tabbedPaneChatView = new TabbedPaneChatView(presenter.tabbedPaneChatViewPresenter());
        channelListView = new ChannelListView(presenter.channelListViewPresenter());

        getChildren().addAll(tabbedPaneChatView, channelListView);
    }


}
