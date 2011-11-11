import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.ConnectException;
import java.util.ArrayList;

public class LineSocketServer{
    private ServerSocket server;
    private LineSocketServerEventHandler handler;
    private ArrayList<LineSocket> clients;
    private boolean closer = false;

    public LineSocketServer(int port){
        if(server != null && !server.isClosed()) return;
        if(clients == null) clients = new ArrayList<LineSocket>();
        try{
            server = new ServerSocket(port);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void listen(){
        while(!closer){
            try{
                Thread.sleep(100);
                LineSocket sock = new LineSocket(server.accept());
                clients.add(sock);
                final int cid = clients.size()-1; // client id
                handler.onAccept(cid);
                if(handler != null){
                    sock.addEventHandler(new LineSocketEventHandler(){
                            public void onMessage(String line){
                                handler.onMessage(cid, line);
                            }
                            public void onOpen(){
                            }
                            public void onClose(){
                                handler.onClose(cid);
                            }
                        });
                }
                sock.run();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public LineSocket getClient(int id){
        return clients.get(id);
    }
    
    public void close(){
        closer = true;
        try{
            server.close();
            for(LineSocket sock : clients){
                sock.close();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void addEventHandler(LineSocketServerEventHandler handler){
        this.handler = handler;
    }

}