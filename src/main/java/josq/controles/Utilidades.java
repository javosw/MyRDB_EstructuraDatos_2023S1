package josq.controles;

import java.io.File;
import java.io.FileWriter;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import josq.estructuras.NodoHoja;
import josq.modelos.Columna;
import josq.modelos.Tabla;

public class Utilidades 
{
    public static TableView<ObservableList<String>> generarTabla(Tabla tabla_propuesta)
    {
        if(tabla_propuesta == null) {return null;}

        System.out.println(">>>>>>>>>>>>>> TITULOS");

        Columna llaves = tabla_propuesta.getLlavePrimaria();

        TableView<ObservableList<String>> vista = new TableView<>();
        if(llaves == null) {return vista;}

        TableColumn<ObservableList<String>,String> col_llave = new TableColumn<>(llaves.getNombre());
        vista.getColumns().add(col_llave);
        col_llave.setCellValueFactory( 
            celda -> 
            {
                int index = celda.getTableView().getVisibleLeafIndex(col_llave);                
                ObservableList<String> tupla_actual = celda.getValue();
                String dato_mostrado = tupla_actual.get(index);
                return new SimpleStringProperty(dato_mostrado);
            });
        System.out.println("  titulo="+llaves.getNombre());

        NodoHoja<String, Columna> racimo_de_cols = tabla_propuesta.getPrimeraHoja();
        while (racimo_de_cols != null) 
        {
            int cols_en_este_racimo = racimo_de_cols.getNumeroDeLlaves();
            for (int j = 0; j <= cols_en_este_racimo - 1; j++) 
            {
                Columna col = racimo_de_cols.getValor(j);

                String nombre = col.getNombre();
                TableColumn<ObservableList<String>,String> titulo = new TableColumn<>(nombre);
                vista.getColumns().add(titulo);
                titulo.setCellValueFactory( celda -> 
                {
                    int index = celda.getTableView().getVisibleLeafIndex(titulo);
                    ObservableList<String> tupla_actual = celda.getValue();
                    String dato_mostrado = tupla_actual.get(index);
                    return new SimpleStringProperty(dato_mostrado);
                });
                
                System.out.println("  titulo="+nombre);
            }

            racimo_de_cols = (NodoHoja<String,Columna>) racimo_de_cols.getAnyNodoDerecho();
        }
        System.out.println("#titulos="+vista.getColumns().size());
        System.out.println("<<<<<<<<<<<<<< TITULOS");

        System.out.println(">>>>>>>>>>>>>> TUPLAS");
        ObservableList<ObservableList<String>> tuplas = getTuplas(tabla_propuesta);
        System.out.println("<<<<<<<<<<<<<< TUPLAS");
        vista.setItems(tuplas);

        return vista;
    }

    private static ObservableList<ObservableList<String>> getTuplas(Tabla tabla_propuesta)
    {
        ObservableList<ObservableList<String>> tuplas = FXCollections.observableArrayList();

        NodoHoja<String, Columna> racimo_de_cols = tabla_propuesta.getPrimeraHoja();
        Columna llaves = tabla_propuesta.getLlavePrimaria();
        NodoHoja<String, String> racimo_de_llaves = llaves.getPrimeraHoja();
        while(racimo_de_llaves != null)
        {
            int llaves_en_este_racimo = racimo_de_llaves.getNumeroDeLlaves();
            for (int i = 0; i <= llaves_en_este_racimo - 1; i++) 
            {
                String llave = racimo_de_llaves.getValor(i);

                ObservableList<String> tupla = getTupla(llave, racimo_de_cols);
                tuplas.add(tupla);
                System.out.println("  llave-"+ llave + "@" + tuplas.size() + ".size=" + tupla.size());
            }

            racimo_de_llaves = (NodoHoja<String,String>) racimo_de_llaves.getAnyNodoDerecho();
        }
        System.out.println("#tuplas=" + tuplas.size());
        return tuplas;
    }

    private static ObservableList<String> getTupla(String llave,NodoHoja<String, Columna> racimo_de_cols)
    {
        ObservableList<String> tupla = FXCollections.observableArrayList();
        tupla.add(llave);

        while (racimo_de_cols != null) 
        {
            int cols_en_este_racimo = racimo_de_cols.getNumeroDeLlaves();

            for (int j = 0; j <= cols_en_este_racimo - 1; j++) 
            {
                Columna col = racimo_de_cols.getValor(j);

                String dato = col.getDato(llave);
                tupla.add(dato);
            }

            racimo_de_cols = (NodoHoja<String,Columna>) racimo_de_cols.getAnyNodoDerecho();
        }

        return tupla;
    }

    public static boolean esCadenaValida(String dato)
    {
        if(dato == null || dato.length() == 0){return false;}
        return true;
    }

    public static String getUbicacionDeGrafico() throws Exception
    {
        // ARCHIVO GRAPHVIZ
        String dot = Interacciones.getCadenaGraphviz();
        FileWriter fwDot = new FileWriter("josq-db.txt");
        fwDot.write(dot);
        fwDot.close();

        // neato -Tpng -o josq-db.png josq-db.txt
        ProcessBuilder pbNeato = new ProcessBuilder();
        pbNeato.command("neato","-Tpng","-o","josq-db.png","josq-db.txt");

        Process pNeato = pbNeato.start();
        pNeato.waitFor();

        File grafica = new File("josq-db.png");
        return grafica.getCanonicalPath();
    }

    // FINALIZADO
    public static TiposAutorizados getTipo(String tipo_propuesto)
    {
        if(tipo_propuesto.equals("boolean")) {return TiposAutorizados.BOOLEAN;}
        if(tipo_propuesto.equals("int")) {return TiposAutorizados.INT;}
        if(tipo_propuesto.equals("float")) {return TiposAutorizados.FLOAT;}
        if(tipo_propuesto.equals("double")) {return TiposAutorizados.DOUBLE;}
        if(tipo_propuesto.equals("long")) {return TiposAutorizados.LONG;}
        if(tipo_propuesto.equals("char")) {return TiposAutorizados.CHAR;}
        if(tipo_propuesto.equals("String")) {return TiposAutorizados.STRING;}
        return null;
    }

    // FINALIZADO: utilizado para comprobar que un dato sea valido en una columna
    public static boolean tieneTipoCorrecto(TiposAutorizados tipo,String dato)
    {
        if(dato == null) {return false;}
        
        if(tipo == TiposAutorizados.BOOLEAN)
        {
            try { Boolean.parseBoolean(dato); return true; } 
            catch (Exception e) { }
        }
        else if(tipo == TiposAutorizados.INT)
        {
            try { Integer.parseInt(dato); return true; } 
            catch (Exception e) { }
        }
        else if(tipo == TiposAutorizados.FLOAT)
        {
            try { Float.parseFloat(dato); return true; } 
            catch (Exception e) { }
        }
        else if(tipo == TiposAutorizados.LONG)
        {
            try { Long.parseLong(dato); return true; } 
            catch (Exception e) { }
        }
        else if(tipo == TiposAutorizados.DOUBLE)
        {
            try { Double.parseDouble(dato); return true; } 
            catch (Exception e) { }
        }
        else if(tipo == TiposAutorizados.CHAR)
        {
            int l = dato.length();
            if(l == 1){ return true; }
        }
        else if(tipo == TiposAutorizados.STRING)
        {
            return true;
        }
        return false;
    }

}
