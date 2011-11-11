public interface LineSocketServerEventHandler{
    public void onMessage(int client_id, String line);
    public void onAccept(int client_id);
    public void onClose(int client_id);
}