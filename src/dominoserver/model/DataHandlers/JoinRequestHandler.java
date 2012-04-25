package dominoserver.model.DataHandlers;

import dominoserver.model.DominoServer;
import dominoserver.model.JoinRequest;

public class JoinRequestHandler implements SocketDataHandler {
    
    private DominoServer parent;

    public JoinRequestHandler(DominoServer parent) {
        this.parent = parent;
    }

    @Override
    public void handleData(Object o) {
        JoinRequest request = (JoinRequest) o;
        System.out.println(request.getUsername());
    }
    
}
