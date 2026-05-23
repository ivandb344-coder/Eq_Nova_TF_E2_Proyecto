package modelo;

/**
 * Modelo del cliente que compra paquetes turisticos.
 * Se guarda en datos/clientes.csv
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class Cliente {

    private String tipoId;
    private String numeroId;
    private String nombre;
    private String email;
    private String telefono;
    private String nombreContacto;
    private boolean empresa;

    public String aLineaCsv() {
        return tipoId + ";"
                + numeroId + ";"
                + nombre + ";"
                + email + ";"
                + telefono + ";"
                + nombreContacto + ";"
                + (empresa ? "S" : "N");
    }

    public static Cliente desdeLineaCsv(String linea) {
        String[] c = linea.split(";", -1);
        if (c.length < 7) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.tipoId = c[0].trim();
        cliente.numeroId = c[1].trim();
        cliente.nombre = c[2].trim();
        cliente.email = c[3].trim();
        cliente.telefono = c[4].trim();
        cliente.nombreContacto = c[5].trim();
        cliente.empresa = c[6].trim().equalsIgnoreCase("S");
        return cliente;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public boolean isEmpresa() {
        return empresa;
    }

    public void setEmpresa(boolean empresa) {
        this.empresa = empresa;
    }

    public String getEmpresaTexto() {
        return empresa ? "SI" : "NO";
    }
}
