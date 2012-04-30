package dominoserver.model.connection;

public interface ObservableSocket {

    public void addObserver(SocketObserver o);
    public void notifyObservers(Object data, Object sender);
    public void onConnectionSuccessful(Object sender);
    public void onConnectionFailed(Object sender);
    public void onConnectionLost(Object sender);
    
}