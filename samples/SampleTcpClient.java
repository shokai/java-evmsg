package samples;

import org.shokai.evmsg.TcpClient;
import org.shokai.evmsg.TcpClientEventHandler;

public class SampleTcpClient{

    public static void main(String args[]){
        // initialize
        TcpClient sock = new TcpClient("localhost", 5000);
        final TcpClient that_sock = sock;

        // add handler - onMessage, onOpen, onClose
        sock.addEventHandler(new TcpClientEventHandler(){
                public void onMessage(String line){
                    System.out.println(" > "+line);
                }
                public void onOpen(){
                    System.out.println("* socket connected");

                    // send
                    int count = 5;
                    while(true){
                        that_sock.send("count : " + count);
                        if(count < 1){
                            that_sock.close();
                            break;
                        }
                        count--;
                        try{
                            Thread.sleep(1000);
                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
                public void onClose(){
                    System.out.println("* socket closed");
                    try{
                        Thread.sleep(3000);
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                    System.out.println("reconnect..");
                    that_sock.connect();
                }
            });

        // connect
        sock.connect();

    }
}