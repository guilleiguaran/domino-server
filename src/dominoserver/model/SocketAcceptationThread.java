package dominoserver.model;

import dominoserver.controller.Observable;
import dominoserver.controller.Observer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketAcceptationThread extends Thread implements Observer, Observable {

    public boolean is_accepting_ = true;
    ServerSocket serverSocket_;
    ArrayList<SocketClientThread> replication_clients = new ArrayList<>();
    private ArrayList<Observer> observers;
    int port_;

    public SocketAcceptationThread(int Port) {
        port_ = Port;
        observers = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            serverSocket_ = new ServerSocket(port_);
            while (is_accepting_) {
                Socket incomming_socket = serverSocket_.accept();
                SocketClientThread current_client = GetNotOcupatedClientThread();
                if (current_client != null) {
                    current_client.AssignSocket(incomming_socket);
                    replication_clients.add(current_client);
                    current_client.addObserver(this);
                    System.out.println("Accept new client");
                }
            }
        } catch (Exception err) {
        }
    }

    public SocketClientThread GetNotOcupatedClientThread() {
        SocketClientThread free_client_thread = null;
        try {
            free_client_thread = new SocketClientThread();
            free_client_thread.start();
        } catch (Exception err) {
        }
        MakeThisProcessWaitForAWhile(2000);
        return free_client_thread;
    }

    public void MakeThisProcessWaitForAWhile(int delay) {
        final Object object = new Object();
        try {
            synchronized (object) {
                object.wait(delay);
            }
        } catch (Exception err) {
        }
    }

    @Override
    public void notify(String message, Object sender) {
        for (SocketClientThread c : replication_clients) {
            if (!c.equals(sender)) {
                SocketClientThread source = (SocketClientThread) sender;
                //TODO: remove replication
                c.SendMessage(message);
            }
        }
        notifyObservers(message);
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.notify(message, this);
        }
    }

}
