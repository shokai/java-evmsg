package org.shokai.evmsg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.ConnectException;

public class Udp{
    private InetSocketAddress addr;
    private DatagramSocket sock;
    private UdpEventHandler handler;
    private boolean closer;
    private int myPort = 0;

    public Udp(String host, int port){
        this.addr = new InetSocketAddress(host, port);
        run();
    }
    public Udp(String host, int port, int myPort){
        this.myPort = myPort;
        this.addr = new InetSocketAddress(host, port);
        run();
    }

    public void run(){
        new Thread(){
            public void run(){
                byte[] buf = new byte[1024];
                while(!closer){
                    if(sock != null){
                        try{
                            DatagramPacket pkt = new DatagramPacket(buf, buf.length);
                            sock.receive(pkt);
                            if(pkt.getLength() > 0){
                                String data = new String(pkt.getData(),0, pkt.getLength());
                                if(handler!=null){
                                    handler.onMessage(pkt.getAddress().getHostAddress(), pkt.getPort(), data);
                                }
                            }
                            Thread.sleep(100);
                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }
    
    public boolean send(String data){
        try{
            if(this.sock == null){
                if(myPort == 0) this.sock = new DatagramSocket();
                else this.sock = new DatagramSocket(myPort);
            }
            byte[] buf = data.getBytes();
            DatagramPacket pkt = new DatagramPacket(buf, buf.length, addr);
            sock.send(pkt);
        }
        catch(Exception ex){
            return false;
        }
        return true;
    }

    public void close(){
        this.closer = true;
        this.sock.close();
    }

    public void addEventHandler(UdpEventHandler handler){
        this.handler = handler;
    }

}