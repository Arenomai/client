package com.petut.thobbyo.arenomai.net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Connection {
    private Socket chaussette;
    private InputStream sockIn;
    private OutputStream rawSockOut;
    private BufferedOutputStream sockOut;

    private byte[] headerBuffer = new byte[Message.Header.ByteSize];
    private int headerBytesReceived = 0;
    private byte[] messageBuffer = null;
    private int messageBytesReceived = 0;


    public void connect(final String host, final int port) throws IOException {
        chaussette = new Socket();
        InetSocketAddress addr = new InetSocketAddress(host, port);
        chaussette.connect(addr);
        chaussette.setTcpNoDelay(true);
        sockIn = chaussette.getInputStream();
        rawSockOut = chaussette.getOutputStream();
        sockOut = new BufferedOutputStream(rawSockOut);
    }


    private InMessage readBlock() {
        try {
            sockIn.read(headerBuffer);
            Message.Header hdr = new Message.Header(headerBuffer);
            byte[] buf = new byte[hdr.dataSize];
            sockIn.read(buf);
            return new InMessage(hdr, buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InMessage readNonblock() {
        try {
            if (headerBytesReceived < Message.Header.ByteSize) {
                final int toRead = Math.min(sockIn.available(),
                        Message.Header.ByteSize - headerBytesReceived);
                headerBytesReceived += sockIn.read(headerBuffer, headerBytesReceived, toRead);
                if (headerBytesReceived == Message.Header.ByteSize) {
                    Message.Header hdr = new Message.Header(headerBuffer);
                    if (hdr.dataSize == 0) {
                        headerBytesReceived = 0;
                        return new InMessage(hdr, null);
                    } else {
                        messageBuffer = new byte[hdr.dataSize];
                        messageBytesReceived = 0;
                    }
                }
            }
            if (headerBytesReceived < Message.Header.ByteSize) {
                return null;
            }
            if (messageBuffer != null) {
                final int toRead = Math.min(sockIn.available(),
                        messageBuffer.length - messageBytesReceived);
                messageBytesReceived += sockIn.read(headerBuffer, messageBytesReceived, toRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InMessage read(boolean blocking) {
        if (blocking) {
            return readBlock();
        } else {
            return readNonblock();
        }
    }

    public InMessage read() {
        return read(true);
    }

    public void write(final OutMessage msg) {
        try {
            msg.header.dataSize = msg.baos.size();
            sockOut.write(msg.header.toBytes());
            sockOut.write(msg.baos.toByteArray());
            sockOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        sockOut.flush();
        sockIn.close();
        sockOut.close();
        chaussette.close();
    }
}
