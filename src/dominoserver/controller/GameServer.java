package dominoserver.controller;

import dominoserver.model.Exception.DuplicatedUsernameException;
import dominoserver.model.Exception.MaxCapacityExceededExeption;
import dominoserver.model.Exception.PlayerNotFoundExecption;
import dominoserver.model.GameStatus;
import dominoserver.model.Player;
import dominoserver.model.PlayerList;

public interface GameServer {
    
    public void start();
    public void stop();
    public void pause();
    public void resume();
    public GameStatus getStatus();
    
    public void acceptPlayer(Player player) throws MaxCapacityExceededExeption, DuplicatedUsernameException;
    public void removePlayer(Player player) throws PlayerNotFoundExecption;
    public PlayerList getPlayers();
        
    
}
