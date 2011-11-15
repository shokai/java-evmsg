package org.shokai.evmsg;

public interface TcpClientEventHandler{
    public void onMessage(String line);
    public void onOpen();
    public void onClose();
}
