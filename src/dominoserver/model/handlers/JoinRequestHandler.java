package dominoserver.model.handlers;

import dominoserver.model.DominoServer;
import dominoserver.model.JoinRequest;
import dominoserver.model.connection.ServerEvent;
import dominoserver.model.connection.ServerEventType;
import dominoserver.model.connection.SocketClientThread;
import dominoserver.model.exception.DuplicatedUsernameException;
import dominoserver.model.exception.MaxCapacityExceededExeption;
import dominoserver.model.logic.Player;

public class JoinRequestHandler implements SocketDataHandler {
    
    private DominoServer server;

    public JoinRequestHandler(DominoServer server) {
        this.server = server;
    }

    @Override
    public void handleData(Object o, Object sender) {
        JoinRequest request = (JoinRequest) o;
        Player player = new Player(request.getUsername());
        player.setSocket((SocketClientThread) sender);
        try {
            server.acceptPlayer(player);
            player.getSocket().sendMessage(new ServerEvent(ServerEventType.PLAYER_ACCEPTED, server.WELCOME_MESSAGE));
            if (server.hasEnoughPlayers()) {
                    server.broadcast(new ServerEvent(ServerEventType.GAME_READY, server.READY_MESSAGE));
                }
        } catch (MaxCapacityExceededExeption | DuplicatedUsernameException ex) {
            player.getSocket().sendMessage(new ServerEvent(ServerEventType.PLAYER_REJECTED, ex.getMessage()));
            server.getSocket().removeClient(player.getSocket());
        }
    }
    
}
