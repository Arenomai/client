package com.petut.thobbyo.petut;

import com.petut.thobbyo.petut.net.Connection;

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
