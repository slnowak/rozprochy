package com.jgroups.chat.gui.tabbedchat;

import com.jgroups.chat.gui.tabbedchat.tab.ChannelTab;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by novy on 26.03.15.
 */
public class TabbedPaneChatView extends TabPane {

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
        // todo: get rid of magic numbers
        Tab tab = new Tab();
        tab.setText(null);
        tab.setContent(new Rectangle(500, 500, Color.LIGHTSTEELBLUE));
        getTabs().add(tab);
    }
}
