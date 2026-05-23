package controlador;

import java.io.IOException;
import java.util.ArrayList;
import modelo.PaqueteMultiple;
import modelo.PaqueteTuristico;
import modelo.PaqueteUnico;
import utilitarios.Archivo;

/**
 * Controlador MVC del modulo de paquetes.
 * La vista llama aqui; esta clase usa el modelo y el CSV.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class PaqueteControlador {

    // Lista en memoria de todos los paquetes
    private static ArrayList<PaqueteTuristico> paquetes = new ArrayList<>();

    public static ArrayList<PaqueteTuristico> getPaquetes() {
        return paquetes;
    }

    // Lee paquetes.csv y llena la lista
    public static void cargarDesdeArchivo() throws IOException {
        paquetes.clear();
        ArrayList<String> lineas = Archivo.leerLineas(Archivo.RUTA_PAQUETES);
        for (String linea : lineas) {
            if (linea.trim().isEmpty()) {
                continue;
            }
            try {
                PaqueteTuristico paquete = PaqueteTuristico.desdeLineaCsv(linea);
                if (paquete != null) {
                    paquetes.add(paquete);
                }
            } catch (Exception e) {
                // linea con formato invalido
            }
        }
    }

    // Escribe todos los paquetes al CSV
    public static void guardarEnArchivo() throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        for (PaqueteTuristico paquete : paquetes) {
            lineas.add(paquete.aLineaCsv());
        }
        Archivo.escribirLineas(Archivo.RUTA_PAQUETES, lineas);
    }

    public static boolean existeCodigo(String codigo) {
        for (PaqueteTuristico paquete : paquetes) {
            if (paquete.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return true;
            }
        }
        return false;
    }

    // Registra un paquete nuevo (valida codigo unico)
    public static String registrar(PaqueteTuristico paquete) {
        String error = validarPaquete(paquete);
        if (error != null) {
            return error;
        }

        try {
            cargarDesdeArchivo();
            if (existeCodigo(paquete.getCodigo())) {
                return "El codigo del paquete ya existe.";
            }
            paquetes.add(paquete);
            guardarEnArchivo();
            return null;
        } catch (IOException e) {
            return "Error al guardar el archivo: " + e.getMessage();
        }
    }

    public static PaqueteTuristico buscarPorCodigo(String codigo) {
        for (PaqueteTuristico paquete : paquetes) {
            if (paquete.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return paquete;
            }
        }
        return null;
    }

    public static PaqueteTuristico buscarActivoPorCodigo(String codigo) throws IOException {
        cargarDesdeArchivo();
        PaqueteTuristico paquete = buscarPorCodigo(codigo);
        if (paquete != null && paquete.isActivo()) {
            return paquete;
        }
        return null;
    }

    public static PaqueteTuristico buscarPorCodigoDesdeArchivo(String codigo) throws IOException {
        cargarDesdeArchivo();
        return buscarPorCodigo(codigo);
    }

    // Devuelve solo paquetes con activo = true
    public static ArrayList<PaqueteTuristico> listarActivos() throws IOException {
        cargarDesdeArchivo();
        ArrayList<PaqueteTuristico> activos = new ArrayList<>();
        for (PaqueteTuristico paquete : paquetes) {
            if (paquete.isActivo()) {
                activos.add(paquete);
            }
        }
        return activos;
    }

    // Eliminacion logica: activo pasa a false
    public static String eliminarLogico(String codigo) {
        try {
            cargarDesdeArchivo();
            PaqueteTuristico paquete = buscarPorCodigo(codigo);
            if (paquete == null) {
                return "No se encontro el paquete seleccionado.";
            }
            if (!paquete.isActivo()) {
                return "El paquete ya esta inactivo.";
            }
            paquete.setActivo(false);
            guardarEnArchivo();
            return null;
        } catch (IOException e) {
            return "Error al eliminar el paquete: " + e.getMessage();
        }
    }

    public static String validarPaquete(PaqueteTuristico paquete) {
        if (paquete.getCodigo() == null || paquete.getCodigo().trim().isEmpty()) {
            return "El codigo del paquete es obligatorio.";
        }
        if (paquete.getNombre() == null || paquete.getNombre().trim().isEmpty()) {
            return "El nombre del paquete es obligatorio.";
        }
        if (paquete.getTipologia() == null || paquete.getTipologia().trim().isEmpty()) {
            return "La tipologia es obligatoria.";
        }
        if (paquete.getOrigen() == null || paquete.getOrigen().trim().isEmpty()) {
            return "El origen es obligatorio.";
        }
        if (paquete.getTarifaDia() < 0) {
            return "La tarifa por dia debe ser mayor o igual a 0.";
        }

        if (paquete instanceof PaqueteUnico) {
            PaqueteUnico unico = (PaqueteUnico) paquete;
            if (unico.getDestino() == null || unico.getDestino().trim().isEmpty()) {
                return "Debe seleccionar un destino.";
            }
            if (unico.getOrigen().equalsIgnoreCase(unico.getDestino())) {
                return "El origen y el destino no pueden ser iguales.";
            }
        }

        if (paquete instanceof PaqueteMultiple) {
            PaqueteMultiple multiple = (PaqueteMultiple) paquete;
            if (multiple.getDestinos().isEmpty()) {
                return "Debe agregar al menos un destino.";
            }
            for (String destino : multiple.getDestinos()) {
                if (paquete.getOrigen().equalsIgnoreCase(destino)) {
                    return "Ningun destino puede ser igual al origen.";
                }
            }
        }

        return null;
    }
}
