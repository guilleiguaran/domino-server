package dominoserver.model;

import dominoserver.controller.GameServer;
import dominoserver.model.Exception.DuplicatedUsernameException;
import dominoserver.model.Exception.MaxCapacityExceededExeption;
import dominoserver.model.Exception.PlayerNotFoundExecption;

public class DominoServer implements GameServer {
    
    private GameStatus status;
    private PlayerList players;
    private SocketAcceptationThread socket;
        
    public DominoServer(int port) {
        status = GameStatus.STARTED;
        players = new PlayerList();
        socket = new SocketAcceptationThread(port);
    }

    @Override
    public void start() {
        socket.start();
    }

    @Override
    public void stop() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public GameStatus getStatus() {
        return status;
    }

    @Override
    public void acceptPlayer(Player player) throws MaxCapacityExceededExeption, DuplicatedUsernameException {
        if (players.size() < 4) {
            if (players.containsKey(player.getName())) {
                throw new DuplicatedUsernameException(player.getName());
            }
        } else {
            throw new MaxCapacityExceededExeption();
        }
    }

    @Override
    public void removePlayer(Player player) throws PlayerNotFoundExecption {
        if (players.containsKey(player.getName())) {
            players.remove(player.getName());
        } else {
            throw new PlayerNotFoundExecption();
        }
    }

    @Override
    public PlayerList getPlayers() {
        return players;
    }
    
}