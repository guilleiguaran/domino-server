package dominoserver.model;

import dominoserver.controller.ObservableSocket;
import dominoserver.controller.SocketObserver;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketAcceptationThread extends Thread implements SocketObserver, ObservableSocket {

    public boolean is_accepting_ = true;
    ServerSocket serverSocket_;
    ArrayList<SocketClientThread> replication_clients = new ArrayList<>();
    private ArrayList<SocketObserver> observers;
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
                System.out.println("Server is accepting clients");
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

    //This socket observes
    @Override
    public void notify(String message, Object sender) {
        notifyObservers(message, (SocketClientThread) sender);
    }

    @Override
    public void addObserver(SocketObserver o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(String message, SocketClientThread sender) {
        for (SocketObserver o : observers) {
            o.notify(message, sender);
        }
    }

}
