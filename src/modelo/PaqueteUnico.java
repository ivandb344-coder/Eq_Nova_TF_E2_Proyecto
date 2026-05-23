package modelo;

/**
 * Paquete con un solo destino.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
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
