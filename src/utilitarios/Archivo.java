package utilitarios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Utilidad para lectura y escritura de archivos CSV.
 *
 * @author Carlos Duran, Ivan David Bejarano Diaz, Zuri Saday Messu, Michael Steven Reyes
 */
public class Archivo {

    public static final String RUTA_PAQUETES = "datos" + File.separator + "paquetes.csv";
    public static final String RUTA_CLIENTES = "datos" + File.separator + "clientes.csv";
    public static final String RUTA_VENTAS = "datos" + File.separator + "ventas.csv";

    public static ArrayList<String> leerLineas(String ruta) throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return lineas;
        }

        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = lector.readLine()) != null) {
            lineas.add(linea);
        }
        lector.close();
        return lineas;
    }

    public static void escribirLineas(String ruta, ArrayList<String> lineas) throws IOException {
        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo, false));
        for (String linea : lineas) {
            escritor.write(linea);
            escritor.newLine();
        }
        escritor.close();
    }
}
