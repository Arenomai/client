package com.petut.thobbyo.petut.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class InMessage extends Message {
    byte[] data;
    private ByteArrayInputStream bais;
    private DataInputStream dis;

    InMessage(Header hdr, byte[] data) {
        this.header = hdr;
        this.data = data;
        this.bais = new ByteArrayInputStream(this.data);
        this.dis = new DataInputStream(this.bais);
    }

    public String readString() {
        int stringSize = readU16();
        byte[] buf = new byte[stringSize];
        try {
            dis.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(buf, Utf8);
    }

    public String readString32() {
        int stringSize = readU16() * 4;
        byte[] buf = new byte[stringSize];
        try {
            dis.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(buf, Utf32LE);
    }

    public boolean readBool() {
        try {
            return dis.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public long readI64() {
        try {
            return dis.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public long readU64() {
        try {
            return dis.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public int readI32() {
        try {
            return dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long readU32() {
        try {
            return dis.readInt() & 0xFFFFFFFFL;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public short readI16() {
        try {
            return dis.readShort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int readU16() {
        try {
            return dis.readShort() & 0xFFFF;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public byte readI8() {
        try {
            return dis.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public short readU8() {
        try {
            return (short) (dis.readByte() & 0xFF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public float readFloat() {
        try {
            return dis.readFloat();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.f;
    }

    public double readDouble() {
        try {
            return dis.readDouble();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.d;
    }

    public void readData(byte[] buf) {
        try {
            dis.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] readData(int len) {
        byte[] buf = new byte[len];
        try {
            dis.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }
}
