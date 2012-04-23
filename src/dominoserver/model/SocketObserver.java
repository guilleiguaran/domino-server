package dominoserver.model;

public interface SocketObserver {

    public void notify(String message, Object sender);

}
