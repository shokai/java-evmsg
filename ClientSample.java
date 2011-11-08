public class ClientSample{

    public static void main(String args[]){
        // initialize
        LineSocket sock = new LineSocket("localhost", 8082);
        final LineSocket that_sock = sock;

        // add handler - onMessage, onOpen, onClose
        sock.addEventHandler(new LineSocketEventHandler(){
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