package dominoserver.model.communication;

public class ClientEvent {
    
    private ClientEventType type;
    private String detail;

    public ClientEvent(ClientEventType type, String detail) {
        this.type = type;
        this.detail = detail;
    }
    
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ClientEventType getType() {
        return type;
    }

    public void setType(ClientEventType type) {
        this.type = type;
    }
    
}
