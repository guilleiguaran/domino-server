package dominoserver.model.connection;

public interface SocketObserver {

    public void notify(Object data, Object sender);
    public void onConnectionSuccessful(Object sender);
    public void onConnectionFailed(Object sender);
    public void onConnectionLost(Object sender);
    
}
