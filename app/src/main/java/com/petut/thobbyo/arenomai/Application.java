package com.petut.thobbyo.arenomai;

import com.petut.thobbyo.arenomai.net.Connection;

public final class Application {
    private Application() {}

    public static final String PREF_DEFAULT = "prefs";

    private static Connection serverConnection;

    public static Connection getServerConnection() {
        if (serverConnection == null) {
            serverConnection = new Connection();
        }
        return serverConnection;
    }
};
