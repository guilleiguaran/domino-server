package dominoserver.model;

import com.google.gson.Gson;
import dominoserver.model.communication.*;
import dominoserver.model.connection.SocketAcceptationThread;
import dominoserver.model.connection.SocketClientThread;
import dominoserver.model.connection.SocketObserver;
import dominoserver.model.exception.DuplicatedUsernameException;
import dominoserver.model.exception.MaxCapacityExceededExeption;
import dominoserver.model.exception.PlayerNotFoundExecption;
import dominoserver.model.handlers.ClientEventHandler;
import dominoserver.model.handlers.JoinRequestHandler;
import dominoserver.model.handlers.PlayRequestHandler;
import dominoserver.model.handlers.SocketDataHandler;
import dominoserver.model.logic.Board;
import dominoserver.model.logic.Player;
import dominoserver.model.logic.PlayerList;
import dominoserver.model.logic.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DominoServer implements SocketObserver {

    private GameStatus status;
    private PlayerList players;
    private Board board;
    private ArrayList<Tile> tiles;
    private SocketAcceptationThread socket;
    private Map<Class, SocketDataHandler> handlers;
    private int num_players = 1;
    public final String WELCOME_MESSAGE = "Bienvenido al servidor";
    private int num_ready_players;
    private int current_player = -1;
    private Map<SocketClientThread, Player> clients;

    public DominoServer(int port) {
        status = GameStatus.LOBBY;
        players = new PlayerList();
        socket = new SocketAcceptationThread(port);
        board = new Board(64, 40);
        tiles = new ArrayList<>();
        generateTiles();
        initializeDataHandlers();
        num_ready_players = 0;
        clients = new HashMap<>();
    }

    public Map<SocketClientThread, Player> getClients() {
        return clients;
    }
    
    public void start() {
        socket.start();
        socket.addObserver(this);
    }

    private void initializeDataHandlers() {
        handlers = new HashMap<>();
        handlers.put(JoinRequest.class, new JoinRequestHandler(this));
        handlers.put(ClientEvent.class, new ClientEventHandler(this));
        handlers.put(PlayRequest.class, new PlayRequestHandler(this));
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

    public final void acceptPlayer(Player player) throws MaxCapacityExceededExeption, DuplicatedUsernameException {
        if (players.size() < num_players) {
            if (players.containsKey(player.getUsername())) {
                throw new DuplicatedUsernameException(player.getUsername());
            } else {
                players.put(player.getUsername(), player);
                clients.put(player.getSocket(), player);
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

    public void onGameReady() {
        status = GameStatus.READY;
        String s[] = new String[num_players];
        for (int i = 0; i < num_players; i++) {
            s[i] = ((Player) players.values().toArray()[i]).getUsername();
        }
        broadcast(new ServerEvent(ServerEventType.GAME_READY, new Gson().toJson(s)));
        assignTiles();
    }

    private void generateTiles() {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                if (i >= j) {
                    tiles.add(new Tile(i, j));
                }
            }
        }
    }

    public void assignTiles() {
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        int[] t = {11, 11, 9, 7};
        int p = 0;
        int[][][] a = new int[num_players][t[num_players - 1]][2];
        int dt[] = new int[num_players];
        while (tiles.size() > 28 - t[num_players - 1] * num_players) {
            int i = r.nextInt(tiles.size());
            ((Player) players.values().toArray()[p]).addTile(tiles.get(i));
            a[p][dt[p]][0] = tiles.get(i).getHalf(0).getValue();
            a[p][dt[p]][1] = tiles.get(i).getHalf(1).getValue();
            dt[p] += 1;
            tiles.remove(i);
            p = (p + 1) % players.size();
        }
        for (int i = 0; i < num_players; i++) {
            TileAssignment ta = new TileAssignment(a[i]);
            System.out.println(i);
            ((Player) players.values().toArray()[i]).getSocket().sendMessage(ta);
        }
    }

    public void registerReadyPlayer() {
        num_ready_players += 1;
        if (num_ready_players == num_players) {
            status = GameStatus.ONGOING;
            broadcast(new ServerEvent(ServerEventType.GAME_STARTED, "Empieza el juego"));
            setNextCurrentPlayer();
        }
    }

    public void setNextCurrentPlayer() {
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        if (current_player == -1) {
            //TODO: the one with tile of form n:n with max(n) starts
            current_player = r.nextInt(num_players);
        } else {
            current_player = (current_player + 1) % players.size();
        }
        broadcast(new ServerEvent(ServerEventType.CANT_PLAY, ((Player) players.values().toArray()[current_player]).getUsername()));
        for (Player p : players.values()) {
            p.setPlaying(false);
        }
        ((Player) players.values().toArray()[current_player]).getSocket().sendMessage(new ServerEvent(ServerEventType.CAN_PLAY, ""));
        ((Player) players.values().toArray()[current_player]).setPlaying(true);
    }
    
    public void placeTile(Player source, PlayRequest request) {
        Tile tile = source.getTiles().get(request.getTile_id());
        
    }

    public Board getBoard() {
        return board;
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