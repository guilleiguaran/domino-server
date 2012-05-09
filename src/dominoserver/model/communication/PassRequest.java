package dominoserver.model.communication;

public class PassRequest {

    String username;

    public PassRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
}
