package modelo;

/**
 * Paquete turistico con un solo destino (hereda de PaqueteTuristico).
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class PaqueteUnico extends PaqueteTuristico {

    private String destino; // unico destino del viaje

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

    @Override
    public String toString() {
        return "PaqueteUnico{" 
                + "destino=" + destino 
                + '}';
    }
    
}
