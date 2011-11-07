import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class LineSocket{
    private String host;
    private int port;
    private Socket sock;
    private BufferedWriter bWriter;
    private BufferedReader bReader;
    private InputStreamReader iReader;
    private LineSocketEventHandler handler;
    private boolean closer = false;

    public LineSocket(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void connect() throws Exception{
        try{
            this.sock = new Socket(host, port);
            this.bWriter = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
            this.iReader = new InputStreamReader(this.sock.getInputStream());
            this.bReader = new BufferedReader(this.iReader);
            final LineSocket that = this;
            if(handler != null) handler.onOpen();
            new Thread(){
                public void run(){
                    while(!closer){
                        try{
                            String line = bReader.readLine();
                            if(line != null){
                                if(handler != null) handler.onMessage(line);
                            }
                            Thread.sleep(10);
                        }
                        catch(SocketException ex){
                            that.close();
                        }
                        catch(IOException ex){
                            that.close();
                        }
                        catch(Exception ex){
                            that.close();
                        }
                    }
                }
            }.start();
        }
        catch(Exception ex){
            throw ex;
        }        
    }

    public void close(){
        try{
            closer = true;
            bReader.close();
            bWriter.close();
            iReader.close();
            sock.close();
            if(handler != null) handler.onClose();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void send(String line) throws Exception{
        try{
            bWriter.write(line);
            bWriter.flush();
        }
        catch(Exception ex){
            throw ex;
        }
    }

    public void addEventHandler(LineSocketEventHandler handler){
        this.handler = handler;
    }

}