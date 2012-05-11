package dominoserver.model.exception;

public class MaxCapacityExceededExeption extends Exception {

    public MaxCapacityExceededExeption() {
        super("Juego lleno");
    }
    
}
