package dominoserver.model.communication;

public class ServerEvent {
    
    private ServerEventType type;
    private String detail;

    public ServerEvent(ServerEventType type, String detail) {
        this.type = type;
        this.detail = detail;
    }
    
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ServerEventType getType() {
        return type;
    }

    public void setType(ServerEventType type) {
        this.type = type;
    }
    
}
