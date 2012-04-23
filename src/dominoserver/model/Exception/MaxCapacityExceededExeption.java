package dominoserver.model.Exception;

public class MaxCapacityExceededExeption extends Exception {

    public MaxCapacityExceededExeption() {
        super("The game cannot contain more than 4 players.");
    }
    
}
