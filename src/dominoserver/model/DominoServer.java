package dominoserver.model;

import dominoserver.model.connection.SocketAcceptationThread;
import dominoserver.model.connection.SocketObserver;
import dominoserver.model.exception.DuplicatedUsernameException;
import dominoserver.model.exception.MaxCapacityExceededExeption;
import dominoserver.model.exception.PlayerNotFoundExecption;
import dominoserver.model.handlers.JoinRequestHandler;
import dominoserver.model.handlers.SocketDataHandler;
import dominoserver.model.logic.*;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class DominoServer implements SocketObserver {
    
    private GameStatus status;
    private PlayerList players;
    private Board board;
    private SocketAcceptationThread socket;
    private Map<Class, SocketDataHandler> handlers;
    
    private int num_players = 2;
    public final String WELCOME_MESSAGE = "Bienvenido al servidor.";
    public final String READY_MESSAGE = "El juego está listo para empezar.";
            
    public DominoServer(int port) {
        status = GameStatus.LOBBY;
        players = new PlayerList();
        socket = new SocketAcceptationThread(port);
        board = new Board(30, 20);
        Tile t1 = new Tile(4, 2);
        t1.setLocation(new Point(25, 10));
        t1.setOrientation(TileOrientation.WEST);
        Tile t2 = new Tile(6, 1);
        t2.setLocation(new Point(12, 8));
        t2.setOrientation(TileOrientation.NORTH);
        board.addTile(t1);
        board.addTile(t2);
        System.out.println(board);
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
            if (players.containsKey(player.getUsername())) {
                throw new DuplicatedUsernameException(player.getUsername());
            } else {
                players.put(player.getUsername(), player);
                System.out.println("Entra jugador " + player.getUsername());
            }
        } else {
            throw new MaxCapacityExceededExeption();
        }
    }

    public void removePlayer(Player player) throws PlayerNotFoundExecption {
        if (players.containsKey(player.getUsername())) {
            players.remove(player.getUsername());
        } else {
            throw new PlayerNotFoundExecption();
        }
    }

    public PlayerList getPlayers() {
        return players;
    }

    @Override
    public void notify(Object data, Object sender) {
        handlers.get(data.getClass()).handleData(data, sender);
    }

    public SocketAcceptationThread getSocket() {
        return socket;
    }
    
    public boolean hasEnoughPlayers() {
        return players.size() == num_players;
    }
    
    public void broadcast(Object data) {
        socket.broadcast(data);
    }

    @Override
    public void onConnectionSuccessful(Object sender) {
    }

    @Override
    public void onConnectionFailed(Object sender) {
    }

    @Override
    public void onConnectionLost(Object sender) {
    }
    
}