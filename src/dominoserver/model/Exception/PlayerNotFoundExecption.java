package dominoserver.model.Exception;

public class PlayerNotFoundExecption extends Exception {

    public PlayerNotFoundExecption() {
        super("Player not found on server, cannot be removed.");
    }
    
}
