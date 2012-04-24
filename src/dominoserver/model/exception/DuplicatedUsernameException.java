package dominoserver.model.exception;

public class DuplicatedUsernameException extends Exception {
    
    public DuplicatedUsernameException(String name) {
        super("The username " + name + " already exist.");
    }
    
}
