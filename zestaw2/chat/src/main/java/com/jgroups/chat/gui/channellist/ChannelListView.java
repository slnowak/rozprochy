package com.jgroups.chat.gui.channellist;

import com.jgroups.chat.gui.common.ErrorDisplayingAwareView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Created by novy on 26.03.15.
 */
public class ChannelListView extends VBox implements ErrorDisplayingAwareView {

    private ChannelListViewPresenter presenter;
    private Label channelListWithUsers;
    private Button connectToChannelButton;

    public ChannelListView(ChannelListViewPresenter presenter) {
        this.presenter = presenter;
        presenter.view(this);
        initializeView();
    }

    private void initializeView() {
        channelListWithUsers = new Label();

        connectToChannelButton = new Button("Connect to channel");
        connectToChannelButton.setOnMouseClicked(mouseEvent -> presenter.handleConnectToChannelButtonClicked());

        getChildren().addAll(channelListWithUsers, connectToChannelButton);
    }

    void askForChannelToConnect() {
        final TextInputDialog dialog = new TextInputDialog("230.0.0.1");
        dialog.setTitle("Type chanel");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter channel you want to connect to:");

        final Optional<String> result = dialog.showAndWait();

        result.ifPresent(presenter::handleChannelTyped);
    }

    void renderView(String channelList) {
        Platform.runLater(
                () -> channelListWithUsers.setText(channelList)
        );

    }

}
