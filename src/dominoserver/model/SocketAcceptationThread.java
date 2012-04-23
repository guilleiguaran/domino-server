package dominoserver.model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketAcceptationThread extends Thread implements SocketObserver, ObservableSocket {

    private boolean is_accepting = true;
    private ServerSocket server_socket;
    private ArrayList<SocketClientThread> clients = new ArrayList<>();
    private ArrayList<SocketObserver> observers;
    private int port;

    public SocketAcceptationThread(int port) {
        this.port = port;
        observers = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            server_socket = new ServerSocket(port);
            while (is_accepting) {
                System.out.println("Server is accepting clients");
                Socket incomming_socket = server_socket.accept();
                SocketClientThread current_client = getNotOcupatedClientThread();
                if (current_client != null) {
                    current_client.assignSocket(incomming_socket);
                    clients.add(current_client);
                    current_client.addObserver(this);
                    System.out.println("Accept new client");
                }
            }
        } catch (Exception ex) {
        }
    }

    public SocketClientThread getNotOcupatedClientThread() {
        SocketClientThread free_client_thread = null;
        try {
            free_client_thread = new SocketClientThread();
            free_client_thread.start();
        } catch (Exception ex) {
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
        } catch (Exception ex) {
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
    public void notifyObservers(String message, Object sender) {
        for (SocketObserver o : observers) {
            o.notify(message, sender);
        }
    }

}
