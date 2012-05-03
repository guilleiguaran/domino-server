package dominoserver.model.exception;

public class TileNotFoundException extends Exception {
    
    public TileNotFoundException() {
        super("La posición solicitada no existe");
    }
    
}
