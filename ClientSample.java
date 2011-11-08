import java.io.*;
import java.net.*;

public class ClientSample{

    public static void main(String args[]){
        LineSocket sock = new LineSocket("localhost", 8082);
        sock.addEventHandler(new LineSocketEventHandler(){
                public void onMessage(String line){
                    System.out.println(" > "+line);
                }
                public void onOpen(){
                    System.out.println("* socket connected");
                }
                public void onClose(){
                    System.out.println("* socket closed");
                    System.exit(0);
                }
            });
        

        sock.connect();

        int count = 5;
        while(true){
            try{
                Thread.sleep(1000);
                sock.send("count : " + count);
                if(count < 1){
                    sock.close();
                    break;
                }
            }
            catch(SocketException ex){
                sock.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            count--;
        }

        while(true){
        }
    }
}