package controlador;

import java.io.IOException;
import java.util.ArrayList;
import modelo.Cliente;
import utilitarios.Archivo;

/**
 * Controlador MVC de clientes (buscar y guardar en CSV).
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class ClienteControlador {

    private static ArrayList<Cliente> clientes = new ArrayList<>();

    // Carga clientes desde datos/clientes.csv
    public static void cargarDesdeArchivo() throws IOException {
        clientes.clear();
        ArrayList<String> lineas = Archivo.leerLineas(Archivo.RUTA_CLIENTES);
        for (String linea : lineas) {
            if (linea.trim().isEmpty()) {
                continue;
            }
            try {
                Cliente cliente = Cliente.desdeLineaCsv(linea);
                if (cliente != null) {
                    clientes.add(cliente);
                }
            } catch (Exception e) {
                // linea invalida
            }
        }
    }

    public static void guardarEnArchivo() throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        for (Cliente cliente : clientes) {
            lineas.add(cliente.aLineaCsv());
        }
        Archivo.escribirLineas(Archivo.RUTA_CLIENTES, lineas);
    }

    // Busca por tipo y numero de identificacion
    public static Cliente buscar(String tipoId, String numeroId) throws IOException {
        cargarDesdeArchivo();
        for (Cliente cliente : clientes) {
            if (cliente.getTipoId().equalsIgnoreCase(tipoId.trim())
                    && cliente.getNumeroId().equalsIgnoreCase(numeroId.trim())) {
                return cliente;
            }
        }
        return null;
    }

    // Guarda o actualiza un cliente en el CSV
    public static String guardar(Cliente cliente) {
        String error = validarCliente(cliente);
        if (error != null) {
            return error;
        }
        try {
            cargarDesdeArchivo();
            Cliente existente = buscarSinRecargar(cliente.getTipoId(), cliente.getNumeroId());
            if (existente != null) {
                clientes.remove(existente);
            }
            clientes.add(cliente);
            guardarEnArchivo();
            return null;
        } catch (IOException e) {
            return "Error al guardar el cliente: " + e.getMessage();
        }
    }

    private static Cliente buscarSinRecargar(String tipoId, String numeroId) {
        for (Cliente cliente : clientes) {
            if (cliente.getTipoId().equalsIgnoreCase(tipoId.trim())
                    && cliente.getNumeroId().equalsIgnoreCase(numeroId.trim())) {
                return cliente;
            }
        }
        return null;
    }

    public static String validarCliente(Cliente cliente) {
        if (cliente.getTipoId() == null || cliente.getTipoId().trim().isEmpty()) {
            return "El tipo de identificacion es obligatorio.";
        }
        if (cliente.getNumeroId() == null || cliente.getNumeroId().trim().isEmpty()) {
            return "El numero de identificacion es obligatorio.";
        }
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            return "El nombre del cliente es obligatorio.";
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            return "El email es obligatorio.";
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            return "El telefono es obligatorio.";
        }
        if (cliente.getNombreContacto() == null || cliente.getNombreContacto().trim().isEmpty()) {
            return "El nombre de contacto es obligatorio.";
        }
        return null;
    }
}
