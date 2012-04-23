package dominoserver.model;

import dominoserver.model.SocketObserver;

public interface ObservableSocket {

    public void addObserver(SocketObserver o);
    public void notifyObservers(String message, Object sender);
    
}