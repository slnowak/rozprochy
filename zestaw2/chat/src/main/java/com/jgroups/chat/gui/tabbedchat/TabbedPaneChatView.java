package com.jgroups.chat.gui.tabbedchat;

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

    private void initializeView() {
        Tab tab = new Tab();
        tab.setText("new tab");
        tab.setContent(new Rectangle(200, 200, Color.LIGHTSTEELBLUE));
        getTabs().add(tab);
    }
}