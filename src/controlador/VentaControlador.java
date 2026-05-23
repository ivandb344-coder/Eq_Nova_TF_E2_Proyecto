package controlador;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import modelo.Cliente;
import modelo.PaqueteTuristico;
import modelo.Venta;
import utilitarios.Archivo;

/**
 * Controlador MVC de ventas: registrar, listar, cancelar y boleta.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class VentaControlador {

    private static ArrayList<Venta> ventas = new ArrayList<>();

    // Lee ventas desde datos/ventas.csv
    public static void cargarDesdeArchivo() throws IOException {
        ventas.clear();
        ArrayList<String> lineas = Archivo.leerLineas(Archivo.RUTA_VENTAS);
        for (String linea : lineas) {
            if (linea.trim().isEmpty()) {
                continue;
            }
            try {
                Venta venta = Venta.desdeLineaCsv(linea);
                if (venta != null) {
                    ventas.add(venta);
                }
            } catch (Exception e) {
                // linea invalida
            }
        }
    }

    public static void guardarEnArchivo() throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        for (Venta venta : ventas) {
            lineas.add(venta.aLineaCsv());
        }
        Archivo.escribirLineas(Archivo.RUTA_VENTAS, lineas);
    }

    // Guarda cliente y venta; genera codigo V1, V2...
    public static String registrar(Venta venta, Cliente cliente) {
        String errorCliente = ClienteControlador.guardar(cliente);
        if (errorCliente != null) {
            return errorCliente;
        }

        String errorVenta = validarVenta(venta);
        if (errorVenta != null) {
            return errorVenta;
        }

        try {
            PaqueteTuristico paquete = PaqueteControlador.buscarActivoPorCodigo(venta.getCodigoPaquete());
            if (paquete == null) {
                return "El paquete no existe o no esta activo.";
            }

            cargarDesdeArchivo();
            venta.setCodigoVenta(generarCodigoVenta());
            venta.setActivo(true);
            ventas.add(venta);
            guardarEnArchivo();
            return null;
        } catch (IOException e) {
            return "Error al guardar la venta: " + e.getMessage();
        }
    }

    private static String generarCodigoVenta() {
        int max = 0;
        for (Venta venta : ventas) {
            String codigo = venta.getCodigoVenta();
            if (codigo.toUpperCase().startsWith("V")) {
                try {
                    int num = Integer.parseInt(codigo.substring(1));
                    if (num > max) {
                        max = num;
                    }
                } catch (NumberFormatException e) {
                    // otro formato de codigo
                }
            }
        }
        return "V" + (max + 1);
    }

    public static Venta buscarPorCodigo(String codigoVenta) throws IOException {
        cargarDesdeArchivo();
        for (Venta venta : ventas) {
            if (venta.getCodigoVenta().equalsIgnoreCase(codigoVenta.trim())) {
                return venta;
            }
        }
        return null;
    }

    // Filtra por estado ACTIVO/CANCELADO y opcionalmente por codigo
    public static ArrayList<Venta> buscarPorFiltro(boolean activo, String codigoVenta) throws IOException {
        cargarDesdeArchivo();
        ArrayList<Venta> resultado = new ArrayList<>();
        String codigo = codigoVenta == null ? "" : codigoVenta.trim();

        for (Venta venta : ventas) {
            if (venta.isActivo() != activo) {
                continue;
            }
            if (!codigo.isEmpty() && !venta.getCodigoVenta().equalsIgnoreCase(codigo)) {
                continue;
            }
            resultado.add(venta);
        }
        return resultado;
    }

    // Cancelacion logica de la venta
    public static String cancelarVenta(String codigoVenta) {
        try {
            cargarDesdeArchivo();
            Venta venta = buscarPorCodigoSinRecargar(codigoVenta);
            if (venta == null) {
                return "No se encontro la venta seleccionada.";
            }
            if (!venta.isActivo()) {
                return "La venta ya esta cancelada.";
            }
            venta.setActivo(false);
            guardarEnArchivo();
            return null;
        } catch (IOException e) {
            return "Error al cancelar la venta: " + e.getMessage();
        }
    }

    private static Venta buscarPorCodigoSinRecargar(String codigoVenta) {
        for (Venta venta : ventas) {
            if (venta.getCodigoVenta().equalsIgnoreCase(codigoVenta.trim())) {
                return venta;
            }
        }
        return null;
    }

    // Arma el texto tipo boleta para el detalle de venta
    public static String obtenerTextoBoleta(Venta venta) throws IOException {
        Cliente cliente = ClienteControlador.buscar(venta.getTipoId(), venta.getNumeroId());
        PaqueteTuristico paquete = PaqueteControlador.buscarPorCodigoDesdeArchivo(venta.getCodigoPaquete());

        double porcentajeRecargo = 0;
        if (paquete != null) {
            porcentajeRecargo = paquete.getPorcentajeRecargo();
        }

        double subtotal = venta.getTarifaDia() * venta.getDiasPermanencia() * venta.getUnidades();
        double montoRecargo = Venta.redondear2(subtotal * (porcentajeRecargo / 100.0));
        double base = Venta.redondear2(subtotal + montoRecargo);

        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        String linea = "==============================================";

        StringBuilder boleta = new StringBuilder();
        boleta.append(linea).append("\n");
        boleta.append("     VENTA DE PAQUETES TURISTICOS - NOVA\n");
        boleta.append(linea).append("\n");
        boleta.append("              BOLETA DE VENTA\n");
        boleta.append("Codigo venta: ").append(venta.getCodigoVenta());
        boleta.append("          Estado: ").append(venta.getEstadoTexto()).append("\n");
        boleta.append("Fecha: ").append(fecha).append("\n");
        boleta.append(linea).append("\n\n");

        boleta.append("--- DATOS DEL CLIENTE ---\n");
        boleta.append("Tipo identificacion: ").append(nombreTipoId(venta.getTipoId())).append("\n");
        boleta.append("Numero identificacion: ").append(venta.getNumeroId()).append("\n");
        boleta.append("Nombre: ").append(venta.getNombreCliente()).append("\n");
        if (cliente != null) {
            boleta.append("Email: ").append(cliente.getEmail()).append("\n");
            boleta.append("Telefono: ").append(cliente.getTelefono()).append("\n");
            boleta.append("Nombre contacto: ").append(cliente.getNombreContacto()).append("\n");
            boleta.append("Empresa: ").append(cliente.getEmpresaTexto()).append("\n");
        } else {
            boleta.append("Email: No registrado\n");
            boleta.append("Telefono: No registrado\n");
            boleta.append("Nombre contacto: No registrado\n");
            boleta.append("Empresa: No registrado\n");
        }
        boleta.append("\n");

        boleta.append("--- DATOS DEL PAQUETE ---\n");
        boleta.append("Codigo paquete: ").append(venta.getCodigoPaquete()).append("\n");
        boleta.append("Nombre: ").append(venta.getNombrePaquete()).append("\n");
        if (paquete != null) {
            boleta.append("Categoria: ").append(paquete.getCategoriaTexto()).append("\n");
            boleta.append("Tipologia: ").append(paquete.getTipologia()).append("\n");
            boleta.append("Estado paquete: ").append(paquete.isActivo() ? "Activo" : "Inactivo").append("\n");
            boleta.append("Servicios incluidos:\n");
            boleta.append("  - Vuelo: ").append(paquete.getServicioTexto(paquete.isVuelo())).append("\n");
            boleta.append("  - Hotel: ").append(paquete.getServicioTexto(paquete.isHotel())).append("\n");
            boleta.append("  - Asistencia medica: ").append(paquete.getServicioTexto(paquete.isAsistenciaMedica())).append("\n");
            boleta.append("  - Alimentacion: ").append(paquete.getServicioTexto(paquete.isAlimentacion())).append("\n");
            boleta.append("  - Solo desayuno: ").append(paquete.getServicioTexto(paquete.isSoloDesayuno())).append("\n");
            if (paquete.getDescripcion() != null && !paquete.getDescripcion().trim().isEmpty()) {
                boleta.append("Descripcion: ").append(paquete.getDescripcion()).append("\n");
            }
        } else {
            boleta.append("Categoria: ").append("No disponible").append("\n");
            boleta.append("Tipologia: ").append("No disponible").append("\n");
        }
        boleta.append("Origen: ").append(venta.getOrigen()).append("\n");
        boleta.append("Destino(s): ").append(venta.getDestino()).append("\n");
        boleta.append("Tarifa por dia: ").append(String.format("%.2f", venta.getTarifaDia())).append("\n");
        boleta.append("\n");

        boleta.append("--- DATOS DE LA VENTA ---\n");
        boleta.append("Dias de permanencia: ").append(venta.getDiasPermanencia()).append("\n");
        boleta.append("Unidades (cantidad): ").append(venta.getUnidades()).append("\n");
        boleta.append("Subtotal: ").append(String.format("%.2f", subtotal)).append("\n");
        if (porcentajeRecargo > 0) {
            boleta.append("Recargo paquete multiple (").append(String.format("%.0f", porcentajeRecargo))
                    .append("%): ").append(String.format("%.2f", montoRecargo)).append("\n");
        }
        boleta.append("Base antes de descuento: ").append(String.format("%.2f", base)).append("\n");
        boleta.append("Descuento aplicado (").append(String.format("%.2f", venta.getPorcentajeDescuento()))
                .append("%): ").append(String.format("%.2f", venta.getMontoDescuento())).append("\n");
        boleta.append(linea).append("\n");
        boleta.append("TOTAL A PAGAR: ").append(String.format("%.2f", venta.getTotal())).append("\n");
        boleta.append(linea).append("\n");

        return boleta.toString();
    }

    private static String nombreTipoId(String tipoId) {
        if (tipoId.equalsIgnoreCase("C")) {
            return "Cedula";
        }
        if (tipoId.equalsIgnoreCase("N")) {
            return "NIT";
        }
        return tipoId;
    }

    public static String validarVenta(Venta venta) {
        if (venta.getCodigoPaquete() == null || venta.getCodigoPaquete().trim().isEmpty()) {
            return "Debe buscar un paquete turistico.";
        }
        if (venta.getDiasPermanencia() <= 0) {
            return "Los dias de permanencia deben ser mayores a 0.";
        }
        if (venta.getUnidades() <= 0) {
            return "Las unidades deben ser mayores a 0.";
        }
        if (venta.getPorcentajeDescuento() < 0 || venta.getPorcentajeDescuento() > 100) {
            return "El descuento debe estar entre 0 y 100.";
        }
        return null;
    }
}
