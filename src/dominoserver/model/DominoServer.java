package dominoserver.model;

import dominoserver.model.DataHandlers.JoinRequestHandler;
import dominoserver.model.DataHandlers.SocketDataHandler;
import dominoserver.model.connection.SocketAcceptationThread;
import dominoserver.model.connection.SocketObserver;
import dominoserver.model.exception.DuplicatedUsernameException;
import dominoserver.model.exception.MaxCapacityExceededExeption;
import dominoserver.model.exception.PlayerNotFoundExecption;
import dominoserver.model.logic.Board;
import dominoserver.model.logic.Player;
import dominoserver.model.logic.PlayerList;
import java.util.HashMap;
import java.util.Map;

public class DominoServer implements SocketObserver {
    
    private GameStatus status;
    private PlayerList players;
    private Board board;
    private SocketAcceptationThread socket;
    private Map<Class, SocketDataHandler> handlers;
            
    public DominoServer(int port) {
        status = GameStatus.STARTED;
        players = new PlayerList();
        socket = new SocketAcceptationThread(port);
        board = new Board(22, 22);
        initializeDataHandlers();
    }

    public void start() {
        socket.start();
        socket.addObserver(this);
    }
    
    private void initializeDataHandlers() {
        handlers = new HashMap<>();
        handlers.put(JoinRequest.class, new JoinRequestHandler(this));
    }

    public void stop() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public GameStatus getStatus() {
        return status;
    }

    public void acceptPlayer(Player player) throws MaxCapacityExceededExeption, DuplicatedUsernameException {
        if (players.size() < 4) {
            if (players.containsKey(player.getName())) {
                throw new DuplicatedUsernameException(player.getName());
            }
        } else {
            throw new MaxCapacityExceededExeption();
        }
    }

    public void removePlayer(Player player) throws PlayerNotFoundExecption {
        if (players.containsKey(player.getName())) {
            players.remove(player.getName());
        } else {
            throw new PlayerNotFoundExecption();
        }
    }

    public PlayerList getPlayers() {
        return players;
    }

    @Override
    public void notify(Object data, Object sender) {
        handlers.get(data.getClass()).handleData(data);
    }
    
}