package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas de conversion CSV del paquete turistico.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class PaqueteTuristicoTest {

    @Test
    public void lineaCsvYObjetoConsistentes() {
        PaqueteUnico original = new PaqueteUnico();
        original.setCodigo("PX1");
        original.setNombre("Paquete prueba");
        original.setTipologia("Naturaleza");
        original.setTarifaDia(85000.5);
        original.setOrigen("ARMENIA");
        original.setDestino("CARTAGENA");
        original.setVuelo(true);
        original.setHotel(true);
        original.setAsistenciaMedica(false);
        original.setAlimentacion(true);
        original.setSoloDesayuno(false);
        original.setDescripcion("Prueba unitaria");
        original.setActivo(true);

        String linea = original.aLineaCsv();
        PaqueteTuristico cargado = PaqueteTuristico.desdeLineaCsv(linea);

        assertEquals(original.getCodigo(), cargado.getCodigo());
        assertEquals(original.getNombre(), cargado.getNombre());
        assertEquals(original.getTarifaDia(), cargado.getTarifaDia(), 0.001);
        assertEquals(original.getDestinosCsv(), cargado.getDestinosCsv());
    }

    @Test
    public void servicioTexto() {
        PaqueteUnico paquete = new PaqueteUnico();
        assertEquals("Si", paquete.getServicioTexto(true));
        assertEquals("No", paquete.getServicioTexto(false));
    }

    @Test
    public void lineaCsvInvalidaRetornaNull() {
        assertNull(PaqueteTuristico.desdeLineaCsv("dato;incompleto"));
    }
}
