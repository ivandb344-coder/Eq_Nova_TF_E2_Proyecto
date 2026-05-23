package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas del calculo de totales de la venta.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class VentaTest {

    @Test
    public void calcularTotalesSinRecargoNiDescuento() {
        double[] resultado = Venta.calcularTotales(100, 2, 3, 0, 0);
        assertEquals(0.0, resultado[0], 0.001);
        assertEquals(600.0, resultado[1], 0.001);
    }

    @Test
    public void calcularTotalesConRecargoMultiple() {
        // subtotal 600 + 1% recargo = 606
        double[] resultado = Venta.calcularTotales(100, 2, 3, 0, 1);
        assertEquals(0.0, resultado[0], 0.001);
        assertEquals(606.0, resultado[1], 0.001);
    }

    @Test
    public void calcularTotalesConDescuento() {
        // base 600, descuento 10% = 60, total 540
        double[] resultado = Venta.calcularTotales(100, 2, 3, 10, 0);
        assertEquals(60.0, resultado[0], 0.001);
        assertEquals(540.0, resultado[1], 0.001);
    }

    @Test
    public void calcularTotalesConRecargoYDescuento() {
        // base 606, descuento 10% = 60.6, total 545.4
        double[] resultado = Venta.calcularTotales(100, 2, 3, 10, 1);
        assertEquals(60.6, resultado[0], 0.001);
        assertEquals(545.4, resultado[1], 0.001);
    }

    @Test
    public void redondear2Decimales() {
        assertEquals(12.35, Venta.redondear2(12.345), 0.001);
        assertEquals(12.34, Venta.redondear2(12.344), 0.001);
    }

    @Test
    public void estadoActivoYCancelado() {
        Venta ventaActiva = new Venta();
        assertEquals("ACTIVO", ventaActiva.getEstadoTexto());

        ventaActiva.setActivo(false);
        assertEquals("CANCELADO", ventaActiva.getEstadoTexto());
    }
}
