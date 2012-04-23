package dominoserver;

import dominoserver.model.DominoServer;

public class Main {
    
    public Main() {
        new DominoServer().start();
    }
    
    public static void main(String[] args) {
        Main main = new Main();
    }
        
}
