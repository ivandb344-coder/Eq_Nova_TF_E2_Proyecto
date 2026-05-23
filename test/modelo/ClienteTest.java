package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas de conversion CSV del cliente.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class ClienteTest {

    @Test
    public void conversionCsvCliente() {
        String linea = "C;123456;Juan Perez;juan@mail.com;3001234567;Maria Lopez;N";
        Cliente cliente = Cliente.desdeLineaCsv(linea);

        assertNotNull(cliente);
        assertEquals("C", cliente.getTipoId());
        assertEquals("123456", cliente.getNumeroId());
        assertEquals("Juan Perez", cliente.getNombre());
        assertFalse(cliente.isEmpresa());
        assertEquals("NO", cliente.getEmpresaTexto());
    }

    @Test
    public void lineaCsvDesdeObjeto() {
        Cliente cliente = new Cliente();
        cliente.setTipoId("N");
        cliente.setNumeroId("900111222");
        cliente.setNombre("Empresa Viajes");
        cliente.setEmail("info@viajes.com");
        cliente.setTelefono("6015551234");
        cliente.setNombreContacto("Pedro Gomez");
        cliente.setEmpresa(true);

        String linea = cliente.aLineaCsv();
        Cliente cargado = Cliente.desdeLineaCsv(linea);

        assertEquals(cliente.getNumeroId(), cargado.getNumeroId());
        assertTrue(cargado.isEmpresa());
        assertEquals("SI", cargado.getEmpresaTexto());
    }

    @Test
    public void validarNumeroIdCedulaYNit() {
        assertEquals("La cedula debe tener 6 digitos.",
                Cliente.validarNumeroId("C", "12345"));
        assertNull(Cliente.validarNumeroId("C", "123456"));
        assertEquals("El NIT debe tener 9 digitos.",
                Cliente.validarNumeroId("N", "12345678"));
        assertNull(Cliente.validarNumeroId("N", "123456789"));
    }

    @Test
    public void validarFormatoCliente() {
        Cliente cliente = new Cliente();
        cliente.setTipoId("C");
        cliente.setNumeroId("123456");
        cliente.setNombre("Juan Perez");
        cliente.setNombreContacto("Maria Lopez");
        cliente.setEmail("juan@mail.com");
        cliente.setTelefono("3001234567");

        assertNull(Cliente.validarFormato(cliente));

        cliente.setNombre("Juan123");
        assertNotNull(Cliente.validarFormato(cliente));

        cliente.setNombre("Juan Perez");
        cliente.setEmail("correo-invalido");
        assertNotNull(Cliente.validarFormato(cliente));

        cliente.setEmail("juan@mail.com");
        cliente.setTelefono("123");
        assertNotNull(Cliente.validarFormato(cliente));
    }
}
