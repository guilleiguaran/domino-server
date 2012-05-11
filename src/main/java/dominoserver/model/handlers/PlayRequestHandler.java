package dominoserver.model.handlers;

import dominoserver.model.DominoServer;
import dominoserver.model.communication.PlayRequest;
import dominoserver.model.communication.ServerEvent;
import dominoserver.model.communication.ServerEventType;
import dominoserver.model.connection.SocketClientThread;
import dominoserver.model.exception.TileNotFoundException;
import dominoserver.model.logic.Player;

public class PlayRequestHandler implements SocketDataHandler {

    private DominoServer server;

    public PlayRequestHandler(DominoServer server) {
        this.server = server;
    }
    
    @Override
    public void handleData(Object o, Object sender) {
        try {
            PlayRequest request = (PlayRequest) o;
            Player source = server.getClients().get((SocketClientThread) sender);
            if (!source.isWaiting() && source.isPlaying()) {
                source.setWaiting(true);
                if (!source.getTiles().containsKey(request.getTile_id())) {
                    source.setWaiting(false);
                    throw new TileNotFoundException();
                } else {
                    server.placeTile(source, request);
                }
            }
        } catch (TileNotFoundException ex) {
            ((SocketClientThread) sender).sendMessage(new ServerEvent(ServerEventType.TILE_REJECTED, ex.getMessage()));   
        }
    }
    
}
