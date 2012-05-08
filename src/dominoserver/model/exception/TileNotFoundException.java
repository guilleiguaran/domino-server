package dominoserver.model.exception;

public class TileNotFoundException extends Exception {
    
    public TileNotFoundException() {
        super("La ficha solicitada no existe");
    }
    
}
