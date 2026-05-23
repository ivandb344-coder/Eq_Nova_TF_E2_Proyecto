package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas del paquete turistico Multiple.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class PaqueteMultipleTest {

    @Test
    public void esPaqueteMultipleConRecargo() {
        PaqueteMultiple paquete = new PaqueteMultiple();

        assertTrue(paquete.esMultiple());
        assertEquals(1.0, paquete.getPorcentajeRecargo(), 0.001);
        assertEquals("Múltiple", paquete.getCategoriaTexto());
    }

    @Test
    public void agregarDestinoValidaDuplicados() {
        PaqueteMultiple paquete = new PaqueteMultiple();

        assertTrue(paquete.agregarDestino("CALI"));
        assertTrue(paquete.agregarDestino("MEDELLIN"));
        assertFalse(paquete.agregarDestino("CALI"));
        assertFalse(paquete.agregarDestino(""));
        assertEquals("CALI|MEDELLIN", paquete.getDestinosCsv());
    }

    @Test
    public void destinosDesdeCsv() {
        PaqueteMultiple paquete = new PaqueteMultiple();
        paquete.setDestinosDesdeCsv("BOGOTA|CARTAGENA|CALI");

        assertEquals(3, paquete.getDestinos().size());
        assertEquals("BOGOTA, CARTAGENA, CALI", paquete.getDestinosTexto().replace("\n", ", "));
    }

    @Test
    public void conversionCsvMultiple() {
        String linea = "P02;Multiple;Ruta Andina;Aventura;200000;BOGOTA;CALI|MEDELLIN;S;N;S;N;N;Varias ciudades;S";
        PaqueteTuristico paquete = PaqueteTuristico.desdeLineaCsv(linea);

        assertNotNull(paquete);
        assertTrue(paquete instanceof PaqueteMultiple);
        PaqueteMultiple multiple = (PaqueteMultiple) paquete;
        assertEquals(2, multiple.getDestinos().size());
        assertEquals("CALI", multiple.getDestinos().get(0));
    }
}
