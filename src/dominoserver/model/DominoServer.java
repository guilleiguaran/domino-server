package dominoserver.model;

import dominoserver.model.Exception.DuplicatedUsernameException;
import dominoserver.model.Exception.MaxCapacityExceededExeption;
import dominoserver.model.Exception.PlayerNotFoundExecption;

public class DominoServer {
    
    private GameStatus status;
    private PlayerList players;
    private SocketAcceptationThread socket;
        
    public DominoServer(int port) {
        status = GameStatus.STARTED;
        players = new PlayerList();
        socket = new SocketAcceptationThread(port);
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