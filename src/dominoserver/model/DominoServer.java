package dominoserver.model;

import dominoserver.model.connection.SocketAcceptationThread;
import dominoserver.model.exception.DuplicatedUsernameException;
import dominoserver.model.exception.MaxCapacityExceededExeption;
import dominoserver.model.exception.PlayerNotFoundExecption;
import dominoserver.model.logic.Board;
import dominoserver.model.logic.Player;
import dominoserver.model.logic.PlayerList;
import dominoserver.model.logic.Tile;
import dominoserver.model.logic.TileOrientation;
import java.awt.Point;

public class DominoServer {
    
    private GameStatus status;
    private PlayerList players;
    private Board board;
    private SocketAcceptationThread socket;
            
    public DominoServer(int port) {
        status = GameStatus.STARTED;
        players = new PlayerList();
        socket = new SocketAcceptationThread(port);
        board = new Board(30, 20);
        Tile t1 = new Tile(4, 2);
        t1.setLocation(new Point(25, 10));
        t1.setOrientation(TileOrientation.WEST);
        Tile t2 = new Tile(6, 1);
        t2.setLocation(new Point(12, 8));
        t2.setOrientation(TileOrientation.EAST);
        board.addTile(t1);
        board.addTile(t2);
        System.out.println(board);
    }

    public void start() {
        socket.start();
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
    
}