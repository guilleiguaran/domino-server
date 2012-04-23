package dominoserver.model;

import dominoserver.controller.Observable;
import dominoserver.controller.Observer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SocketClientThread extends Thread implements Observable {

    boolean is_ocupated_ = true;
    final Object mutex_ = new Object();
    final Object call_me_when_ready_the_run_ = new Object();
    Socket current_socket_;
    PrintWriter output_stream_;
    BufferedReader input_stream_;
    boolean is_enabled_ = true;
    boolean is_reading_ = true;
    boolean waiting_ = false;
    boolean is_first_wake_up_ = true;
    private ArrayList<Observer> observers;
    private double longitude = 0;
    private double latitude = 0;

    public SocketClientThread() {
        observers = new ArrayList<>();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void AssignSocket(Socket current_socket) {
        try {
            this.current_socket_ = current_socket;
            ExtractStreamsFromSocket();
            this.start();
        } catch (Exception err) {
        }
    }

    public void ExtractStreamsFromSocket() {
        try {
            this.output_stream_ = new PrintWriter(new OutputStreamWriter(current_socket_.getOutputStream()), true);
            this.input_stream_ = new BufferedReader(new InputStreamReader(current_socket_.getInputStream()));

        } catch (Exception err) {
        }
    }

    @Override
    public void run() {
        while (this.is_enabled_) {
            this.StartReadingProcess();
        }
    }

    public void MakeThisThreadWait() {
        try {
            synchronized (mutex_) {
                waiting_ = true;
                this.mutex_.wait();
                waiting_ = false;
            }
        } catch (Exception err) {
        }
    }

    public void MakeThisThreadWakeUp() {
        try {
            synchronized (mutex_) {
                this.mutex_.notify();
            }
        } catch (Exception err) {
        }
    }

    public void MakeTheAcceptionThreadWakeUp() {
        try {
            synchronized (this.call_me_when_ready_the_run_) {
                this.call_me_when_ready_the_run_.notify();
            }
        } catch (Exception err) {
        }
    }

    public void StartReadingProcess() {
        this.is_reading_ = true;
        String message_;
        try {
            while ((((message_ = this.input_stream_.readLine()) != null)) && (is_reading_)) {
                ProcessIncommingMessage(message_);
            }
        } catch (Exception err) {
        }
    }

    private void ProcessIncommingMessage(String message_) {
        try {
            String header = message_.split("#")[0];
            if (header.equals("location")) {
                latitude = Double.parseDouble(message_.split("#")[1].split("@")[0]);
                longitude = Double.parseDouble(message_.split("#")[1].split("@")[1]);
                System.out.println("location: lat. " + latitude + ", long. " + longitude);
            }
            if (header.equals("message")) {
                notifyObservers(this.current_socket_.getInetAddress().getHostAddress() + ": " + message_.split("#")[1]);
            }
        } catch (Exception err) {
        }
    }

    public boolean SendMessage(String message) {
        boolean success = true;
        try {
            this.output_stream_.println(message);
        } catch (Exception e) {
            success = false;
        }
        return success;
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
