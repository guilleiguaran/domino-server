package dominoserver.controller;

public interface SocketObserver {

    public void notify(String message, Object sender);

}
