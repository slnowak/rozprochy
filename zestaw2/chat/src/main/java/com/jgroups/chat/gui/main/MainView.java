package com.jgroups.chat.gui.main;

import com.jgroups.chat.gui.channellist.ChannelListView;
import com.jgroups.chat.gui.tabbedchat.TabbedPaneChatView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;

import java.util.Optional;

/**
 * Created by novy on 26.03.15.
 */
public class MainView extends HBox {

    private final MainPresenter presenter;

    private TabbedPaneChatView tabbedPaneChatView;
    private ChannelListView channelListView;

    private String nickname;

    public MainView(MainPresenter presenter) {
        this.presenter = presenter;
        presenter.view(this);
        initializeView();
    }

    private void initializeView() {
        tabbedPaneChatView = new TabbedPaneChatView(presenter.tabbedPaneChatViewPresenter());
        channelListView = new ChannelListView(presenter.channelListViewPresenter());
        getChildren().addAll(tabbedPaneChatView, channelListView);

        presenter.handleApplicationStarted();
    }

    public void askForNickanme() {
        final TextInputDialog dialog = new TextInputDialog("nickname");
        dialog.setTitle("Type chanel");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter nickname:");

        final Optional<String> result = dialog.showAndWait();

        result.ifPresent(presenter::handleNicknameTyped);
    }


    public void nickname(String nickname) {
        this.nickname = nickname;
    }

    public String nickname() {
        return nickname;
    }
}
