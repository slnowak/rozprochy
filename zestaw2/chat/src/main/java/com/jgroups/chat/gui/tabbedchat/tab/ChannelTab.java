package com.jgroups.chat.gui.tabbedchat.tab;

import com.jgroups.chat.gui.common.ErrorDisplayingAwareView;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by novy on 26.03.15.
 */
public class ChannelTab extends VBox implements ErrorDisplayingAwareView {

    private final String channelName;
    private final ChannelTabPresenter presenter;

    private Label chatHistory;
    private TextField messageField;

    public ChannelTab(String channelName, ChannelTabPresenter presenter) {
        this.channelName = channelName;
        this.presenter = presenter;
        presenter.view(this);
        initializeView();
    }

    public void addMessage(String message) {
        final String history = chatHistory.getText();
        final String withMessageAppended = history + "\n" + message;
        Platform.runLater(() -> chatHistory.setText(withMessageAppended));
    }

    private void initializeView() {
        chatHistory = new Label("");
        messageField = new TextField("");
        messageField.setOnAction(
                action -> {
                    presenter.handleMessageTyped(messageField.getText());
                    messageField.setText("");
                }
        );
        getChildren().addAll(chatHistory, messageField);
    }

    public String channelName() {
        return channelName;
    }
}
