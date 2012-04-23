package dominoserver.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SocketClientThread extends Thread implements ObservableSocket {

    private final Object mutex = new Object();
    private final Object call_me_when_ready_the_run = new Object();
    private Socket current_socket;
    private PrintWriter output_stream;
    private BufferedReader input_stream;
    private boolean is_enabled = true;
    private boolean is_reading = true;
    private boolean waiting = false;
    private ArrayList<SocketObserver> observers;

    public SocketClientThread() {
        observers = new ArrayList<>();
    }

    public void assignSocket(Socket current_socket) {
        try {
            this.current_socket = current_socket;
            extractStreamsFromSocket();
            this.start();
        } catch (Exception ex) {
        }
    }

    public void extractStreamsFromSocket() {
        try {
            this.output_stream = new PrintWriter(new OutputStreamWriter(current_socket.getOutputStream()), true);
            this.input_stream = new BufferedReader(new InputStreamReader(current_socket.getInputStream()));
        } catch (Exception ex) {
        }
    }

    @Override
    public void run() {
        while (this.is_enabled) {
            this.startReadingProcess();
        }
    }

    public void makeThisThreadWait() {
        try {
            synchronized (mutex) {
                waiting = true;
                this.mutex.wait();
                waiting = false;
            }
        } catch (Exception ex) {
        }
    }

    public boolean isWaiting() {
        return waiting;
    }
        
    public void makeThisThreadWakeUp() {
        try {
            synchronized (mutex) {
                this.mutex.notify();
            }
        } catch (Exception ex) {
        }
    }

    public void makeTheAcceptionThreadWakeUp() {
        try {
            synchronized (this.call_me_when_ready_the_run) {
                this.call_me_when_ready_the_run.notify();
            }
        } catch (Exception ex) {
        }
    }

    public void startReadingProcess() {
        this.is_reading = true;
        String message;
        try {
            while ((((message = this.input_stream.readLine()) != null)) && (is_reading)) {
                processIncommingMessage(message);
            }
        } catch (Exception ex) {
        }
    }

    private void processIncommingMessage(String message) {
        try {
            notifyObservers(message, this);
        } catch (Exception ex) {
        }
    }

    public boolean sendMessage(String message) {
        boolean success = true;
        try {
            this.output_stream.println(message);
        } catch (Exception ex) {
            success = false;
        }
        return success;
    }

    @Override
    public void addObserver(SocketObserver o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(String message, Object sender) {
        for (SocketObserver o : observers) {
            o.notify(message, this);
        }
    }

}
