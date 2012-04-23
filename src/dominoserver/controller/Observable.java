package dominoserver.controller;

public interface Observable {

    public void addObserver(Observer o);
    public void notifyObservers(String message);
    
}







