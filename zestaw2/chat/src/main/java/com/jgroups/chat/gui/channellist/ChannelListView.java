package com.jgroups.chat.gui.channellist;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

import static javafx.scene.control.Alert.AlertType;

/**
 * Created by novy on 26.03.15.
 */
public class ChannelListView extends VBox {

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
        // todo: start
        renderView("ch1\n\tuser1\n\tuser2\nch2\n\tuser1");

        connectToChannelButton = new Button("Connect to channel");
        connectToChannelButton.setOnMouseClicked(mouseEvent -> presenter.handleConnectToChannelButtonClicked());

        getChildren().addAll(channelListWithUsers, connectToChannelButton);
    }

    void showErrorDialog(String title, String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        alert.showAndWait();
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
        channelListWithUsers.setText(channelList);
    }

}
