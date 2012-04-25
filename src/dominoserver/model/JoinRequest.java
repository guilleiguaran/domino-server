package dominoserver.model;

public class JoinRequest {

    String username;

    public JoinRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
}
