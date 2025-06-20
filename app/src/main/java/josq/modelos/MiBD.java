package josq.modelos;

import josq.estructuras.ArbolBp;
import josq.estructuras.NodoHoja;

public class MiBD 
{
    private ArbolBp<String,Tabla> tablas;
    private String nombre;

    // FINALIZADO
    public MiBD(String nombre) 
    {
        this.nombre = nombre;
        this.tablas = new ArbolBp<>();
    }

    // FINALIZADO
    public void addTabla(String tabla)
    {
        Tabla tabla_nueva = new Tabla(tabla);
        this.tablas.addEntradaSinReplicas(tabla, tabla_nueva);
    }

    // 
    public Tabla getTabla(String tabla)
    { return this.tablas.getValor(tabla); }
    
    // FINALIZADO
    public void setLlavePrimaria(String tabla,String columna)
    {
        Tabla tabla_propuesta = this.tablas.getValor(tabla);
        if(tabla_propuesta == null) {return;}

        tabla_propuesta.setLlavePrimaria(columna);
    }

    // FINALIZADO
    public void setRelacion(String tabla,String columna,String relacion)
    {
        Tabla relacion_propuesta = this.tablas.getValor(relacion);
        if(relacion_propuesta == null) {return;}

        Columna llave_primaria = relacion_propuesta.getLlavePrimaria();
        if(llave_primaria == null){return;}

        Tabla tabla_propuesta = this.tablas.getValor(tabla);
        if(tabla_propuesta == null) {return;}
        
        // la relacion debe ser la llave_primaria de la relacion_propuesta
        // https://gestionbasesdatos.readthedocs.io/es/latest/Tema2/Teoria.html#relaciones-binarias-de-grado-2

        tabla_propuesta.setRelacion(columna, llave_primaria);
    }

    // FINALIZADO
    public void addColumna(String tabla,String columna,String tipo)
    {
        Tabla tabla_propuesta = this.tablas.getValor(tabla);
        if(tabla_propuesta == null) {return;}

        tabla_propuesta.addColumna(columna, tipo);
        //System.out.println("MiBD.addColumna()");
    }

    // FINALIZADO
    public void addLlave(String tabla,String llave)
    {
        Tabla tabla_propuesta = tablas.getValor(tabla);
        if(tabla_propuesta == null) {return;}

        tabla_propuesta.addLlave(llave);
    }

    // FINALIZADO
    public void setDato(String tabla,String llave,String columna,String dato)
    {
        Tabla tabla_propuesta = tablas.getValor(tabla);
        if(tabla_propuesta == null) {return;}

        tabla_propuesta.setDato(llave,columna,dato);
    }

    // FINALIZADO
    public void deleteDato(String tabla,String llave,String columna)
    {
        Tabla tabla_propuesta = tablas.getValor(tabla);
        if(tabla_propuesta == null) {return;}

        tabla_propuesta.deleteDato(llave,columna);
    }

    // FINALIZADO
    public void deleteLlave(String tabla,String llave)
    {
        Tabla tabla_propuesta = tablas.getValor(tabla);
        if(tabla_propuesta == null) {return;}

        tabla_propuesta.deleteLlave(llave);
    }

    public StringBuilder getCadenaGraphviz()
    {

        StringBuilder dot1 = new StringBuilder();
        dot1.append("graph\n{\nlayout=neato\ngraph [pad=\"0.1\"];\n\"josq-db\" [shape=cylinder,style=filled,fillcolor=\"cyan\"]\n");
        StringBuilder dot2 = new StringBuilder();

        StringBuilder dot3 = new StringBuilder();


        // TABLAS
        NodoHoja<String,Tabla> racimo_de_tablas = this.tablas.getPrimeraHoja();
        while (racimo_de_tablas != null) 
        {
            int tablas_en_racimo = racimo_de_tablas.getNumeroDeLlaves();

            for (int i = 0; i <= tablas_en_racimo - 1; i++) 
            {
                Tabla t = racimo_de_tablas.getValor(i);
                dot1.append("\"" + t.getNombre() + "\" [shape=box,style=filled]\n");
                dot2.append("\"josq-db\"--\""+t.getNombre()+"\"\n");
                dot3.append(t.getCadenaGraphviz());
            }

            racimo_de_tablas = (NodoHoja<String,Tabla>) racimo_de_tablas.getAnyNodoDerecho();
        }

        dot1.append(dot2);
        dot1.append(dot3+"}");System.out.println(dot1);
        return dot1;
    }
}
