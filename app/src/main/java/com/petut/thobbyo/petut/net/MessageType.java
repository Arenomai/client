package com.petut.thobbyo.petut.net;

public enum MessageType {
    Null(0),
    Auth(1),
    Inventory(2),
    Event(3),
    UserAccount(4);

    private static final MessageType[] mEnumValues = MessageType.values();
    public static MessageType fromInteger(int x) {
        return mEnumValues[x];
    }

    private final int value;
    MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
