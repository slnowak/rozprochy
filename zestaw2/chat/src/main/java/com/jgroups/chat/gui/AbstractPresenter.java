package com.jgroups.chat.gui;

import javafx.scene.Parent;

/**
 * Created by novy on 26.03.15.
 */
public class AbstractPresenter<T extends Parent> {

    protected T view;

    public void view(T view) {
        this.view = view;
    }
}
