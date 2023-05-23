package josq.modelos;

import josq.controles.TiposAutorizados;
import josq.controles.Utilidades;
import josq.estructuras.ArbolBp;
import josq.estructuras.NodoHoja;

public class Tabla 
{
    private String nombre;

    // la llave es String y es el nombre de la columna
    // el valor es Columna
    private ArbolBp<String,Columna> columnas;
    private Columna llavePrimaria;

    // String es el nombre de una columna con relacion de esta tabla
    // la relacion es Columna y pertenece a otra Tabla 
    private ArbolBp<String,Columna> relaciones;

    // FINALIZADO
    protected Tabla(String nombre)
    {
        this.nombre = nombre;
        this.llavePrimaria = null;
        this.columnas = new ArbolBp<String,Columna>();
        this.relaciones = new ArbolBp<String,Columna>();
    }

    // FINALIZADO
    protected void addColumna(String columna,String tipo)
    {
        Columna col_replica = this.columnas.getValor(columna);
        if(col_replica != null){return;}

        TiposAutorizados tipo_propuesto = Utilidades.getTipo(tipo);
        if(tipo_propuesto == null){return;}

        Columna col_nueva = new Columna(columna,tipo_propuesto);
        this.columnas.setEntrada(columna, col_nueva);////
    }

    // FINALIZADO
    protected void setLlavePrimaria(String columna)
    {
        Columna col_propuesta = this.columnas.getValor(columna);
        if(col_propuesta == null){return;}

        this.llavePrimaria = col_propuesta;
        this.columnas.deleteEntrada(columna);
    }

    // FINALIZADO
    protected void setRelacion(String columna,Columna relacion)
    {
        Columna col_propuesta = this.columnas.getValor(columna);
        if(col_propuesta == null){return;}

        boolean mismoNombre = col_propuesta.getNombre().equals(relacion.getNombre());
        boolean mismoTipo = col_propuesta.getTipo() == relacion.getTipo();
    
        if (!(mismoNombre && mismoTipo)) {return;}
        this.relaciones.setEntrada(columna, relacion);
    }

    // FINALIZADO
    protected void setDato(String llave,String columna,String dato)
    {
        String llave_propuesta = this.llavePrimaria.getDato(llave);
        if(llave_propuesta == null){return;}

        Columna col_propuesta = this.columnas.getValor(columna);
        if (col_propuesta == null) { return; }

        if(!this.satisfaceRelacion(columna, dato)){return;}

        col_propuesta.setDato(llave, dato);
    }

    private boolean satisfaceRelacion(String columna,String dato)
    {
        Columna rel_LlavePrimaria = this.relaciones.getValor(columna);
        if (rel_LlavePrimaria != null)
        {
            String dato_propuesto = rel_LlavePrimaria.getDato(dato);
            if(dato_propuesto == null) {return false;}
        }

        return true;
    }

    // FINALIZADO
    protected void deleteDato(String llave,String columna)
    {
        String llave_propuesta = this.llavePrimaria.getDato(llave);
        if (llave_propuesta == null) {return;}

        Columna col_propuesta = this.columnas.getValor(columna);
        if (col_propuesta == null) { return; }

        col_propuesta.deleteDato(llave);
    }
    
    // FINALIZADO
    protected void addLlave(String llave)
    {
        this.llavePrimaria.setDato(llave, llave);
    }

    // FINALIZADO
    protected void deleteLlave(String llave)
    {
        String llave_propuesta = this.llavePrimaria.getDato(llave);
        if(llave_propuesta == null){return;}
        
        this.deleteTupla(llave);
        this.llavePrimaria.deleteDato(llave);
    }

    // FINALIZADO
    private void deleteTupla(String llave)
    {
        // check (existe llave == true)
        // para cada campo: eliminar la llave y su dato asociado

        NodoHoja<String,Columna> racimo_de_cols = this.columnas.getPrimeraHoja();
        while (racimo_de_cols != null) 
        {
            int cols_en_racimo = racimo_de_cols.getNumeroDeLlaves();

            for (int i = 0; i <= cols_en_racimo - 1; i++) 
            {
                Columna col = racimo_de_cols.getValor(i);
                col.deleteDato(llave);
            }

            racimo_de_cols = (NodoHoja<String,Columna>) racimo_de_cols.getAnyNodoDerecho();
        }
    }

    // FINALIZADO
    public Columna getLlavePrimaria() {
        return llavePrimaria;
    }

    // FINALIZADO
    public NodoHoja<String,Columna> getPrimeraHoja()
    {
        return this.columnas.getPrimeraHoja();
    }

    // FINALIZADO
    protected Columna getColumna(String columna) 
    {
        return this.columnas.getValor(columna);
    }

    // FINALIZADO
    public String getNombre() 
    {
        return nombre;
    }
    
    protected StringBuilder getCadenaGraphviz()
    {
        StringBuilder dot = new StringBuilder();
        dot.append("\""+this.nombre+"\"--\""+this.llavePrimaria.getNombre()+"\"\n");

        // COLUMNAS
        NodoHoja<String,Columna> racimo_de_cols = this.columnas.getPrimeraHoja();
        while (racimo_de_cols != null) 
        {
            int cols_en_racimo = racimo_de_cols.getNumeroDeLlaves();

            for (int i = 0; i <= cols_en_racimo - 1; i++) 
            {
                Columna col = racimo_de_cols.getValor(i);
                dot.append("\""+this.nombre+"\"--\""+col.getNombre()+"\"\n");
            }

            racimo_de_cols = (NodoHoja<String,Columna>) racimo_de_cols.getAnyNodoDerecho();
        }

        return dot;
    }
}
