package josq.controles;

import josq.modelos.MiBD;
import josq.modelos.Tabla;

// esta clase es nuestra API
public class Interacciones 
{
    static private MiBD dbms = new MiBD("Estructura de Datos, S1-2023");

    private static boolean esCadenaValida(String cadena) 
    { return Utilidades.esCadenaValida(cadena); }

    public static Tabla getTabla(String tabla) 
    { return dbms.getTabla(tabla); }

    // FINALIZADO
    static public void addTabla(String nombre)
    {
        if (!esCadenaValida(nombre)) {return;}
        dbms.addTabla(nombre);
    }

    // FINALIZADO
    static public void addColumna(String tabla,String columna,String tipo)
    {
        if(!(esCadenaValida(tabla) && esCadenaValida(columna) && esCadenaValida(tipo))) {return;}

        dbms.addColumna(tabla,columna,tipo);
    }

    // FINALIZADO
    static public void setLlavePrimaria(String tabla,String columna)
    {
        if(!(esCadenaValida(tabla) && esCadenaValida(columna))) {return;}
        dbms.setLlavePrimaria(tabla, columna);
    }

    // FINALIZADO
    static public void addLlave(String tabla,String llave)
    {
        if(!(esCadenaValida(tabla) && esCadenaValida(llave))) {return;}
        dbms.addLlave(tabla, llave);
    }

    // FINALIZADO
    static public void setDato(String tabla, String llave, String columna,String dato)
    {
        if(!(esCadenaValida(tabla) && esCadenaValida(columna) && esCadenaValida(llave))) {return;}
        dbms.setDato(tabla, llave, columna, dato);
    }

    // FINALIZADO
    static public void deleteDato(String tabla,String llave,String columna)
    {
        if(!(esCadenaValida(tabla) && esCadenaValida(columna) && esCadenaValida(llave))) {return;}
        dbms.deleteDato(tabla, llave, columna);
    }

    static public void setRelacion(String tabla,String columna,String relacion)
    {
        if(!(esCadenaValida(tabla) && esCadenaValida(columna) && esCadenaValida(relacion))) {return;}
        dbms.setRelacion(tabla, columna, relacion);
    }

    static public void deleteLlave(String tabla,String llave)
    {
        if(!(esCadenaValida(tabla) && esCadenaValida(llave))) {return;}
        dbms.deleteLlave(tabla,llave);
    }

    static public String getNombreLlavePrimaria(String tabla)
    {
        if(!(esCadenaValida(tabla))) {return null;}
        Tabla tabla_propuesta = dbms.getTabla(tabla);
        return tabla_propuesta.getLlavePrimaria().getNombre();
    }

    static public String getCadenaGraphviz()
    {
        return dbms.getCadenaGraphviz().toString();
    }
}
