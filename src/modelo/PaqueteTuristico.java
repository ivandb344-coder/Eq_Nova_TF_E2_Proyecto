package modelo;

/**
 * Clase abstracta del modelo. Representa un paquete turistico.
 * Se usa herencia: PaqueteUnico y PaqueteMultiple.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public abstract class PaqueteTuristico {

    // Atributos comunes del paquete
    protected String codigo;
    protected String nombre;
    protected String tipologia;
    protected double tarifaDia;
    protected String origen;
    protected boolean vuelo;
    protected boolean hotel;
    protected boolean asistenciaMedica;
    protected boolean alimentacion;
    protected boolean soloDesayuno;
    protected String descripcion;
    protected boolean activo; // true = activo, false = eliminado logicamente

    public PaqueteTuristico() {
        this.activo = true;
    }

    // Metodos abstractos que implementan las subclases
    public abstract boolean esMultiple();

    public abstract String getDestinosCsv();

    // Recargo en venta: 0 unico, 1% multiple
    public double getPorcentajeRecargo() {
        return 0;
    }

    public String getCategoriaTexto() {
        return esMultiple() ? "Múltiple" : "Único";
    }

    public String getDestinosTexto() {
        return getDestinosCsv().replace("|", ", ");
    }

    public String getServicioTexto(boolean valor) {
        return valor ? "Si" : "No";
    }

    // Convierte el objeto a una linea del archivo paquetes.csv
    public String aLineaCsv() {
        return codigo + ";"
                + (esMultiple() ? "Multiple" : "Unico") + ";"
                + nombre + ";"
                + tipologia + ";"
                + tarifaDia + ";"
                + origen + ";"
                + getDestinosCsv() + ";"
                + boolCsv(vuelo) + ";"
                + boolCsv(hotel) + ";"
                + boolCsv(asistenciaMedica) + ";"
                + boolCsv(alimentacion) + ";"
                + boolCsv(soloDesayuno) + ";"
                + descripcion.replace(";", ",") + ";"
                + boolCsv(activo);
    }

    // Crea un paquete a partir de una linea del CSV
    public static PaqueteTuristico desdeLineaCsv(String linea) {
        String[] c = linea.split(";", -1);
        if (c.length < 14) {
            return null;
        }

        String categoria = c[1].trim();
        PaqueteTuristico paquete;

        if (categoria.equalsIgnoreCase("Multiple")) {
            paquete = new PaqueteMultiple();
            ((PaqueteMultiple) paquete).setDestinosDesdeCsv(c[6].trim());
        } else {
            paquete = new PaqueteUnico();
            ((PaqueteUnico) paquete).setDestino(c[6].trim());
        }

        paquete.codigo = c[0].trim();
        paquete.nombre = c[2].trim();
        paquete.tipologia = c[3].trim();
        paquete.tarifaDia = Double.parseDouble(c[4].trim());
        paquete.origen = c[5].trim();
        paquete.vuelo = csvBool(c[7]);
        paquete.hotel = csvBool(c[8]);
        paquete.asistenciaMedica = csvBool(c[9]);
        paquete.alimentacion = csvBool(c[10]);
        paquete.soloDesayuno = csvBool(c[11]);
        paquete.descripcion = c[12].trim();
        paquete.activo = csvBool(c[13]);

        return paquete;
    }

    private static String boolCsv(boolean valor) {
        return valor ? "S" : "N";
    }

    private static boolean csvBool(String valor) {
        return valor.trim().equalsIgnoreCase("S");
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public double getTarifaDia() {
        return tarifaDia;
    }

    public void setTarifaDia(double tarifaDia) {
        this.tarifaDia = tarifaDia;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public boolean isVuelo() {
        return vuelo;
    }

    public void setVuelo(boolean vuelo) {
        this.vuelo = vuelo;
    }

    public boolean isHotel() {
        return hotel;
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }

    public boolean isAsistenciaMedica() {
        return asistenciaMedica;
    }

    public void setAsistenciaMedica(boolean asistenciaMedica) {
        this.asistenciaMedica = asistenciaMedica;
    }

    public boolean isAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(boolean alimentacion) {
        this.alimentacion = alimentacion;
    }

    public boolean isSoloDesayuno() {
        return soloDesayuno;
    }

    public void setSoloDesayuno(boolean soloDesayuno) {
        this.soloDesayuno = soloDesayuno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? "" : descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
