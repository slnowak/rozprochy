package com.jgroups.chat.gui;

import com.jgroups.chat.gui.common.ErrorDisplayingAwareView;

/**
 * Created by novy on 26.03.15.
 */
public class AbstractPresenter<T extends ErrorDisplayingAwareView> {

    protected T view;

    public void view(T view) {
        this.view = view;
    }
}
