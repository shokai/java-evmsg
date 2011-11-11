public class ServerSample{

    public static void main(String args[]){
        // initialize
        LineSocketServer server = new LineSocketServer(8082);
        
        final LineSocketServer that_server = server;
        server.addEventHandler(new LineSocketServerEventHandler(){
                public void onMessage(int client_id, String line){
                    System.out.println("* <"+client_id+"> "+ line);
                    that_server.getClient(client_id).send("echo : <"+client_id+"> "+line);
                    
                }
                public void onAccept(int client_id){
                    System.out.println("* <"+client_id+"> connection accepted");
                }
                public void onClose(int client_id){
                    System.out.println("* <"+client_id+"> closed");
                }
            });
        
        server.listen();
    }
}