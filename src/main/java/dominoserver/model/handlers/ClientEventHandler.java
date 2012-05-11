package dominoserver.model.handlers;

import dominoserver.model.DominoServer;
import dominoserver.model.communication.ClientEvent;

public class ClientEventHandler implements SocketDataHandler {

    private DominoServer server;

    public ClientEventHandler(DominoServer server) {
        this.server = server;
    }
    
    @Override
    public void handleData(Object o, Object sender) {
        System.out.println(o);
        ClientEvent evt = (ClientEvent) o;
        switch (evt.getType()) {
            case PLAYER_READY:
                server.registerReadyPlayer();
                break;
            default:
                break;
        }
    }
    
}
