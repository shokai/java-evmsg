public interface UdpLineSocketEventHandler{
    public void onMessage(String host, int port, String line);
}