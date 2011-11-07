public interface LineSocketEventHandler{
    public void onMessage(String line);
    public void onOpen();
    public void onClose();
}