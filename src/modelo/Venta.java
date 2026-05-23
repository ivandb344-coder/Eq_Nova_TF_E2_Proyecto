package modelo;

/**
 * Modelo de la venta. Guarda datos del cliente, paquete y totales.
 * Persistencia en datos/ventas.csv
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class Venta {

    private String codigoVenta;
    private String tipoId;
    private String numeroId;
    private String nombreCliente;
    private String codigoPaquete;
    private String nombrePaquete;
    private String origen;
    private String destino;
    private double tarifaDia;
    private int diasPermanencia;
    private int unidades;
    private double porcentajeDescuento;
    private double montoDescuento;
    private double total;
    private boolean activo;

    public Venta() {
        this.activo = true;
    }

    /**
     * Calcula descuento y total de la venta.
     * [0] = monto descuento, [1] = total a pagar (redondeados a 2 decimales)
     */
    public static double[] calcularTotales(double tarifaDia, int dias, int unidades,
            double porcentajeDescuento, double porcentajeRecargo) {
        double subtotal = tarifaDia * dias * unidades;
        double recargo = subtotal * (porcentajeRecargo / 100.0);
        double base = subtotal + recargo;
        double descuento = base * (porcentajeDescuento / 100.0);
        double total = base - descuento;

        descuento = redondear2(descuento);
        total = redondear2(total);
        return new double[]{descuento, total};
    }

    public static double redondear2(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public String aLineaCsv() {
        return codigoVenta + ";"
                + tipoId + ";"
                + numeroId + ";"
                + nombreCliente + ";"
                + codigoPaquete + ";"
                + nombrePaquete + ";"
                + origen + ";"
                + destino.replace(";", ",") + ";"
                + tarifaDia + ";"
                + unidades + ";"
                + diasPermanencia + ";"
                + porcentajeDescuento + ";"
                + montoDescuento + ";"
                + total + ";"
                + (activo ? "S" : "N");
    }

    public static Venta desdeLineaCsv(String linea) {
        String[] c = linea.split(";", -1);
        if (c.length < 15) {
            return null;
        }
        Venta venta = new Venta();
        venta.codigoVenta = c[0].trim();
        venta.tipoId = c[1].trim();
        venta.numeroId = c[2].trim();
        venta.nombreCliente = c[3].trim();
        venta.codigoPaquete = c[4].trim();
        venta.nombrePaquete = c[5].trim();
        venta.origen = c[6].trim();
        venta.destino = c[7].trim();
        venta.tarifaDia = Double.parseDouble(c[8].trim());
        venta.unidades = Integer.parseInt(c[9].trim());
        venta.diasPermanencia = Integer.parseInt(c[10].trim());
        venta.porcentajeDescuento = Double.parseDouble(c[11].trim());
        venta.montoDescuento = Double.parseDouble(c[12].trim());
        venta.total = Double.parseDouble(c[13].trim());
        venta.activo = c[14].trim().equalsIgnoreCase("S");
        return venta;
    }

    public String getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(String codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getNumeroId() {
        return numeroId;
    }

    public void setNumeroId(String numeroId) {
        this.numeroId = numeroId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCodigoPaquete() {
        return codigoPaquete;
    }

    public void setCodigoPaquete(String codigoPaquete) {
        this.codigoPaquete = codigoPaquete;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public void setNombrePaquete(String nombrePaquete) {
        this.nombrePaquete = nombrePaquete;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getTarifaDia() {
        return tarifaDia;
    }

    public void setTarifaDia(double tarifaDia) {
        this.tarifaDia = tarifaDia;
    }

    public int getDiasPermanencia() {
        return diasPermanencia;
    }

    public void setDiasPermanencia(int diasPermanencia) {
        this.diasPermanencia = diasPermanencia;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public double getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(double montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getEstadoTexto() {
        return activo ? "ACTIVO" : "CANCELADO";
    }
}
