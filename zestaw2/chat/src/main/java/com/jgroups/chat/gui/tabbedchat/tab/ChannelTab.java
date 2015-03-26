package com.jgroups.chat.gui.tabbedchat.tab;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by novy on 26.03.15.
 */
public class ChannelTab extends VBox {

    private final String channelName;
    private final ChannelTabPresenter presenter;

    private Label chatHistory;

    public ChannelTab(String channelName, ChannelTabPresenter presenter) {
        this.channelName = channelName;
        this.presenter = presenter;
        presenter.view(this);
        initializeView();
    }

    public void addMessage(String message) {
        final String history = chatHistory.getText();
        final String withMessageAppended = history + "\n" + message;
        chatHistory.setText(withMessageAppended);
    }

    private void initializeView() {
        chatHistory = new Label("dsdsa");
        getChildren().add(chatHistory);
    }

    public String channelName() {
        return channelName;
    }
}
