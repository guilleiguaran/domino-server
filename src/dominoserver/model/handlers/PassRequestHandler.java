package dominoserver.model.handlers;

import dominoserver.model.DominoServer;
import dominoserver.model.communication.PassRequest;
import dominoserver.model.communication.ServerEvent;
import dominoserver.model.communication.ServerEventType;
import dominoserver.model.connection.SocketClientThread;
import dominoserver.model.logic.Player;
 
public class PassRequestHandler implements SocketDataHandler {

    private DominoServer server;

    public PassRequestHandler(DominoServer server) {
        this.server = server;
    }
    @Override
    public void handleData(Object o, Object sender) {
        PassRequest request = (PassRequest) o;
        Player source = server.getClients().get((SocketClientThread) sender);
        if (!source.isWaiting() && source.isPlaying()) {
            server.broadcast(new ServerEvent(ServerEventType.PLAYER_PASSES, request.getUsername()));
            server.setNextCurrentPlayer();
        }
    }
    
}
