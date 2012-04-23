package dominoserver.controller;

import dominoserver.model.SocketClientThread;

public interface ObservableSocket {

    public void addObserver(SocketObserver o);
    public void notifyObservers(String message, SocketClientThread sender);
    
}