package josq.controles;

import java.io.File;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class CargarXML 
{
    public static void delDatos(String path)
    {
        try 
        {
            File entrada = new File(path);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document doc = saxBuilder.build(entrada);

            Element raiz = doc.getRootElement();
            System.out.println(raiz.getName() + ":");

            List<Element> tablas = raiz.getChildren();

            for (int i = 0; i < tablas.size(); i++) 
            {
                System.out.println("eliminacion_" + i);

                // TABLA
                Element tabla = tablas.get(i);
                String sTabla = tabla.getName();
                System.out.println("  tabla=" + sTabla);

                // LLAVE PRIMARIA
                String sLlavePrimaria = Interacciones.getNombreLlavePrimaria(sTabla);
                Element llavePrimaria = tabla.getChild(sLlavePrimaria);
                if(llavePrimaria == null) {continue;}

                // TUPLA-ID
                String sTuplaID = llavePrimaria.getTextTrim();
                System.out.println("    id-tupla=" + sTuplaID);

                // BUSCAR COLUMNAS
                List<Element> columnas = tabla.getChildren();

                // ELIMINAR TUPLA
                if(columnas.size() == 1)
                {
                    Interacciones.deleteLlave(sTabla, sTuplaID);
                    continue;
                }

                // ELIMINAR DATOS EN TUPLA
                for (int j = 0; j < columnas.size(); j++) 
                {
                    // COLUMNA
                    Element columna = columnas.get(j);
                    String sColumna = columna.getName();
                    if(sColumna.equals(sLlavePrimaria)) {continue;}

                    Interacciones.deleteDato(sTabla, sTuplaID, sColumna);
                    System.out.println("    " + sColumna);
                }
            }

        } 
        catch (Exception e) {}
    }

    public static void addDatos(String archivo) 
    {
        try 
        {
            File inputFile = new File(archivo);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document doc = saxBuilder.build(inputFile);

            Element raiz = doc.getRootElement();
            System.out.println(raiz.getName() + ":");

            List<Element> tablas = raiz.getChildren();

            for (int i = 0; i < tablas.size(); i++) 
            {
                System.out.println("ingreso_" + i + ": ");
                Element tabla = tablas.get(i);
                String sTabla = tabla.getName();

                System.out.println("  tabla=" + sTabla);

                // LA CULUMNA-ID EXISTE EN LA BD?
                String sLlave = Interacciones.getNombreLlavePrimaria(sTabla);

                // LA COLUMNA-ID EXISTE EN EL XML?
                Element llave = tabla.getChild(sLlave);
                if(llave == null){continue;}

                // TUPLA-ID
                String sTuplaID = llave.getTextTrim();
                Interacciones.addLlave(sTabla, sTuplaID);
                System.out.println("  id-tupla=" + sTuplaID);

                // BUSCAR COLUMNAS
                System.out.println("  columnas:");
                List<Element> hijos = tabla.getChildren();
                for (int j = 0; j < hijos.size(); j++) 
                {
                    Element hijo = hijos.get(j);

                    // COLUMNA
                    String sHijo = hijo.getName();
                    if(sHijo.equals(sLlave)){continue;}

                    // DATO
                    String dato = hijo.getTextTrim();
                    System.out.println("    " + sHijo + "=" + dato);
                    Interacciones.setDato(sTabla, sTuplaID, sHijo, dato);
                }
            }
        } 
        catch(Exception e) {}
    }

    public static void addTablas(String path)
    {
        try 
        {
            File inputFile = new File(path);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document doc = saxBuilder.build(inputFile);

            Element raiz = doc.getRootElement();
            System.out.println(raiz.getName() + ":");

            // ESTRUCTURAS
            List<Element> estructuras = raiz.getChildren();
            for (int i = 0; i < estructuras.size(); i++) 
            {
                // ESTRUCTURA
                Element estructura = estructuras.get(i);
                System.out.println("estructura_" + i + ": ");

                // TABLA
                Element tabla = estructura.getChild("tabla");
                if(tabla == null) {continue;} 
                String sTabla = tabla.getTextTrim();System.out.println("  tabla=" + sTabla);
                Interacciones.addTabla(sTabla);

                // BUSCAR COLUMNAS
                System.out.println("  columnas:");
                List<Element> hijos = estructura.getChildren();
                for (int j = 0; j < hijos.size(); j++)
                {
                    Element hijo = hijos.get(j);
                    String sHijo = hijo.getName();
                    if(sHijo.equals("tabla") || sHijo.equals("clave")){continue;}

                    if(hijo.getTextTrim().length() == 0)
                    {
                        if(j - 1 < 0){continue;}
                        Element ultimaColumna = hijos.get(j - 1);
                        String sUltimaColumna = ultimaColumna.getName();

                        System.out.println("    " + sUltimaColumna + ": relacion=" + sHijo);
                        Interacciones.setRelacion(sTabla, sUltimaColumna, sHijo);
                        continue;
                    }

                    // COLUMNA
                    String sTipo = hijo.getTextTrim();
                    System.out.println("    " + sHijo + ": tipo=" + sTipo);
                    Interacciones.addColumna(sTabla, sHijo, sTipo);
                }

                // LLAVE PRIMARIA
                Element llavePrimaria = estructura.getChild("clave");
                if(llavePrimaria == null){continue;}
                String sLlavePrimaria = llavePrimaria.getTextTrim(); System.out.println("  llave-primaria=" + sLlavePrimaria);
                Interacciones.setLlavePrimaria(sTabla, sLlavePrimaria);
            }
        }
        catch(Exception e) {}
    }
}
