package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas del paquete turistico Unico.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class PaqueteUnicoTest {

    @Test
    public void esPaqueteUnico() {
        PaqueteUnico paquete = new PaqueteUnico();
        paquete.setDestino("CALI");

        assertFalse(paquete.esMultiple());
        assertEquals(0.0, paquete.getPorcentajeRecargo(), 0.001);
        assertEquals("CALI", paquete.getDestinosCsv());
        assertEquals("Único", paquete.getCategoriaTexto());
    }

    @Test
    public void conversionCsvUnico() {
        String linea = "P01;Unico;Tour Cafe;Cultural;120000;BOGOTA;CALI;S;S;N;N;N;Ruta cafetera;S";
        PaqueteTuristico paquete = PaqueteTuristico.desdeLineaCsv(linea);

        assertNotNull(paquete);
        assertTrue(paquete instanceof PaqueteUnico);
        assertEquals("P01", paquete.getCodigo());
        assertEquals("CALI", ((PaqueteUnico) paquete).getDestino());
        assertTrue(paquete.isActivo());
    }
}
