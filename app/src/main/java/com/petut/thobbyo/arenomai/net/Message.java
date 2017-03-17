package com.petut.thobbyo.arenomai.net;

import java.nio.charset.Charset;

abstract class Message {
    static class Header {
        static final int ByteSize = 4;

        int dataSize;
        MessageType type;
        byte subtype;

        Header(MessageType type, byte subtype) {
            this.type = type;
            this.subtype = subtype;
        }

        Header(byte[] bytes) {
            dataSize = (short) ((bytes[0] & 0xFF) | (bytes[1] << 8));
            type = MessageType.fromInteger(bytes[2]);
            subtype = bytes[3];
        }

        byte[] toBytes() {
            byte[] bytes = new byte[ByteSize];
            bytes[0] = (byte) (dataSize & 0xFF);
            bytes[1] = (byte) ((dataSize >> 8) & 0xFF);
            bytes[2] = (byte) type.getValue();
            bytes[3] = subtype;
            return bytes;
        }
    }

    static final Charset Utf8 = Charset.forName("UTF-8");
    static final Charset Utf32LE = Charset.forName("UTF-32LE");

    Header header;

    public MessageType getType() {
        return header.type;
    }

    public byte getSubtype() {
        return header.subtype;
    }
}
