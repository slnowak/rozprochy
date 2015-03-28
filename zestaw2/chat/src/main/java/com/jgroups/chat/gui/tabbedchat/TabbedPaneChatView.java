package com.jgroups.chat.gui.tabbedchat;

import com.jgroups.chat.gui.common.ErrorDisplayingAwareView;
import com.jgroups.chat.gui.tabbedchat.tab.ChannelTab;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Created by novy on 26.03.15.
 */
public class TabbedPaneChatView extends TabPane implements ErrorDisplayingAwareView {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int TAB_WIDTH = 800/4;

    private final TabbedPaneChatPresenter presenter;

    public TabbedPaneChatView(TabbedPaneChatPresenter presenter) {
        this.presenter = presenter;
        presenter.view(this);
        initializeView();
    }

    public void addTab(ChannelTab channelTab) {
        final Tab tab = new Tab();
        tab.setOnClosed(action -> presenter.tabClosed(channelTab.channelName()));
        tab.setText(channelTab.channelName());
        tab.setContent(channelTab);
        getTabs().add(tab);
    }

    private void initializeView() {
        setMinSize(WIDTH, HEIGHT);
        setTabMinWidth(TAB_WIDTH);
    }
}
