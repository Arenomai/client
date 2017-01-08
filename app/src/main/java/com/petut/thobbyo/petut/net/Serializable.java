package com.petut.thobbyo.petut.net;

public interface Serializable {
    void writeToMsg(OutMessage msg);
    void readFromMsg(InMessage msg);
}
