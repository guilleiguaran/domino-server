package dominoserver.model.connection;

public interface SocketObserver {

    public void notify(String message, Object sender);

}
