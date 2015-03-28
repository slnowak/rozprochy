package com.jgroups.chat.gui.common;

import javafx.scene.control.Alert;

/**
 * Created by novy on 28.03.15.
 */
public interface ErrorDisplayingAwareView {

    default void showErrorDialog(String title, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }
}
