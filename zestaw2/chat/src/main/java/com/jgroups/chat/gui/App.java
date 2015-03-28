package com.jgroups.chat.gui;

import com.google.common.eventbus.EventBus;
import com.jgroups.chat.gui.channellist.ChannelListViewPresenter;
import com.jgroups.chat.gui.main.MainPresenter;
import com.jgroups.chat.gui.main.MainView;
import com.jgroups.chat.gui.tabbedchat.TabbedPaneChatPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by novy on 26.03.15.
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat");
        final EventBus eventBus = new EventBus();
        final TabbedPaneChatPresenter tabbedPaneChatPresenter = new TabbedPaneChatPresenter(eventBus);
        final ChannelListViewPresenter channelListViewPresenter = new ChannelListViewPresenter(eventBus);
        final MainPresenter mainPresenter = new MainPresenter(eventBus, tabbedPaneChatPresenter, channelListViewPresenter);
        final MainView view = new MainView(mainPresenter);
        primaryStage.setScene(new Scene(view));

        primaryStage.show();
    }
}

