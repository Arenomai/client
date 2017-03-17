package com.petut.thobbyo.arenomai.net;

public interface Serializable {
    void writeToMsg(OutMessage msg);
    void readFromMsg(InMessage msg);
}
