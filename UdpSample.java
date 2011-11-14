public class UdpSample{

    public static void main(String args[]){
        UdpLineSocket sock = new UdpLineSocket("localhost", 5001);

        sock.addEventHandler(new UdpLineSocketEventHandler(){
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