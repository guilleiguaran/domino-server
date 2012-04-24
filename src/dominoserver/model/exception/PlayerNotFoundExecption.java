package dominoserver.model.exception;

public class PlayerNotFoundExecption extends Exception {

    public PlayerNotFoundExecption() {
        super("Player not found on server, cannot be removed.");
    }
    
}
