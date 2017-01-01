package com.petut.thobbyo.petut.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class OutMessage extends Message {
    ByteArrayOutputStream baos;
    private DataOutputStream dos;

    public OutMessage(MessageType type, byte subtype) {
        this.header = new Header(type, subtype);
        this.baos = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.baos);
    }

    public OutMessage(MessageType type) {
        this(type, (byte) 255);
    }

    public void writeString(final String str) {
        try {
            dos.write(Utf8.encode(str).array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeString32(final String str) {
        try {
            dos.write(Utf32LE.encode(str).array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeBool(boolean v) {
        try {
            dos.writeBoolean(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeI64(long i) {
        try {
            dos.writeLong(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeU64(long i) {
        try {
            dos.writeLong(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeI32(int i) {
        try {
            dos.writeInt(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeU32(long i) {
        try {
            dos.writeInt((int) (i & 0xFFFFFFFFL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeI16(short i) {
        try {
            dos.writeShort(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeU16(int i) {
        try {
            dos.writeShort((short) (i & 0xFFFF));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeI8(byte i) {
        try {
            dos.writeByte(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeU8(short i) {
        try {
            dos.writeByte((byte) (i & 0xFF));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFloat(float f) {
        try {
            dos.writeFloat(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDouble(double d) {
        try {
            dos.writeDouble(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData(byte[] data) {
        try {
            dos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
