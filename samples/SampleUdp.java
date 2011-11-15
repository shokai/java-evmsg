package samples;

import org.shokai.evmsg.Udp;
import org.shokai.evmsg.UdpEventHandler;

public class SampleUdp{

    public static void main(String args[]){
        Udp sock = new Udp("localhost", 5001);

        sock.addEventHandler(new UdpEventHandler(){
                public void onMessage(String host, int port, String line){
                    System.out.println("<"+host+":"+port+"> "+line);
                }
            });
        
        while(true){
            sock.send("test");
            try{
                Thread.sleep(1000);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

}