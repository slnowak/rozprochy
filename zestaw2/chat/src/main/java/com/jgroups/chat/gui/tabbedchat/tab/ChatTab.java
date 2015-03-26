package com.jgroups.chat.gui.tabbedchat.tab;

import javafx.scene.layout.VBox;

/**
 * Created by novy on 26.03.15.
 */
public class ChatTab extends VBox {

    private final TabPresenter presenter;

    public ChatTab(TabPresenter presenter) {
        this.presenter = presenter;
        presenter.view(this);
        initializeView();
    }

    private void initializeView() {

    }
}
