package dominoserver.model.communication;

public class JoinRequest {

    String username;

    public JoinRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
}
