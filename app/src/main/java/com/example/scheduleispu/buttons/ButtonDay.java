package com.example.scheduleispu.buttons;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class ButtonDay{

    private Button button;
    private boolean active;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ButtonDay(Button button, boolean active) {
        this.button = button;
        this.active = active;

    }



}
