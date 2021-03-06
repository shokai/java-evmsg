package samples;

import org.shokai.evmsg.TcpServer;
import org.shokai.evmsg.TcpServerEventHandler;
import org.shokai.evmsg.TcpClient;

public class SampleTcpServer{

    public static void main(String args[]){
        // initialize
        int port = 5000;
        TcpServer server = new TcpServer(port);
        System.out.println("TCP server start - port:"+port);
        
        // add event listener
        final TcpServer that_server = server;
        server.addEventHandler(new TcpServerEventHandler(){
                public void onMessage(int client_id, String line){
                    System.out.println("* <"+client_id+"> "+ line);
                    that_server.getClient(client_id).send("echo : <"+client_id+"> "+line);
                }
                public void onAccept(int client_id){
                    System.out.println("* <"+client_id+"> connection accepted");
                    that_server.setReadInterval(100 + that_server.getClients().size()*10);
                }
                public void onClose(int client_id){
                    System.out.println("* <"+client_id+"> closed");
                }
            });
        
        // start TCP socket server
        server.listen();
        
        // broadcast
        new Thread(){
            public void run(){
                while(true){
                    try{
                        Thread.sleep(10000);
                        String msg = new java.util.Date().toString();
                        System.out.println("<broadcast> "+msg);
                        for(TcpClient sock : that_server.getClients()){
                            sock.send(msg);
                        }
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }.start();
    }
}