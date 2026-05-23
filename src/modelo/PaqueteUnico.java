package modelo;

/**
 * Paquete con un solo destino.
 */
public class PaqueteUnico extends PaqueteTuristico {

    private String destino;

    public PaqueteUnico() {
        super();
    }

    @Override
    public boolean esMultiple() {
        return false;
    }

    @Override
    public String getDestinosCsv() {
        return destino;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
