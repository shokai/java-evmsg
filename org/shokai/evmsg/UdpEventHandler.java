package org.shokai.evmsg;

public interface UdpEventHandler{
    public void onMessage(String host, int port, String line);
}