package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Paquete con varios destinos. Aplica 1% de recargo al vender.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class PaqueteMultiple extends PaqueteTuristico {

    private List<String> destinos; // lista de ciudades destino

    public PaqueteMultiple() {
        super();
        this.destinos = new ArrayList<>();
    }

    @Override
    public boolean esMultiple() {
        return true;
    }

    // Recargo del 1% sobre el total de la venta
    @Override
    public double getPorcentajeRecargo() {
        return 1.0;
    }

    @Override
    public String getDestinosCsv() {
        if (destinos.isEmpty()) {
            return "";
        }
        String texto = destinos.get(0);
        for (int i = 1; i < destinos.size(); i++) {
            texto = texto + "|" + destinos.get(i);
        }
        return texto;
    }

    public void setDestinosDesdeCsv(String valor) {
        destinos = new ArrayList<>();
        if (valor == null || valor.trim().isEmpty()) {
            return;
        }
        String[] partes = valor.split("\\|");
        for (String parte : partes) {
            if (!parte.trim().isEmpty()) {
                destinos.add(parte.trim());
            }
        }
    }

    public List<String> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<String> destinos) {
        this.destinos = new ArrayList<>(destinos);
    }

    public boolean agregarDestino(String destino) {
        if (destino == null || destino.trim().isEmpty()) {
            return false;
        }
        String d = destino.trim();
        if (destinos.contains(d)) {
            return false;
        }
        destinos.add(d);
        return true;
    }

    public String getDestinosTexto() {
        if (destinos.isEmpty()) {
            return "";
        }
        String texto = destinos.get(0);
        for (int i = 1; i < destinos.size(); i++) {
            texto = texto + "\n" + destinos.get(i);
        }
        return texto;
    }

    @Override
    public String toString() {
        return "PaqueteMultiple{"
                + "destinos=" + destinos 
                + '}';
    }
    
}
