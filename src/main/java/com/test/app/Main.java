package com.test.app;

import com.test.app.configuaration.FlywayConfig;
import com.test.app.view.user.LoginFrm;

public class Main {
    public static void main(String[] args) {
        FlywayConfig.migrate();

        LoginFrm myFrame = new LoginFrm();
        myFrame.setVisible(true);
    }
}