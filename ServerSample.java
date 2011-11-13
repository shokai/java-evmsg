public class ServerSample{

    public static void main(String args[]){
        // initialize
        int port = 5000;
        LineSocketServer server = new LineSocketServer(port);
        System.out.println("linesocket server start - port:"+port);
        
        // add event listener
        final LineSocketServer that_server = server;
        server.addEventHandler(new LineSocketServerEventHandler(){
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
                        for(LineSocket sock : that_server.getClients()){
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