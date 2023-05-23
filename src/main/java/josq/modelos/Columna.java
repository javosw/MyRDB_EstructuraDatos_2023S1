package josq.modelos;

import josq.controles.TiposAutorizados;
import josq.controles.Utilidades;
import josq.estructuras.ArbolBp;
import josq.estructuras.NodoHoja;

public class Columna
{
    // el String 1 es la llave (convertida a String) de la tabla
    // el String 2 es el dato (convertido a String) de este campo para dicha llave
    private ArbolBp<String,String> datos = new ArbolBp<>();

    private TiposAutorizados tipo;
    private String nombre;

    // FINALIZADO
    protected Columna(String nombre,TiposAutorizados tipo)
    {
        this.tipo = tipo;
        this.nombre = nombre;
    }

    // FINALIZADO
    public String getDato(String llave)
    {
        return this.datos.getValor(llave);
    }

    // FINALIZADO
    // requisitos: la llave debe existir en this.llaves
    // aclaracion: si this.esLlavePrimaria() == true entonces setDato(llave,llave)
    protected void setDato(String llave,String dato)
    {
        boolean mismoTipo = Utilidades.tieneTipoCorrecto(this.tipo, dato);
        if (!mismoTipo) {return;}

        this.datos.setEntrada(llave, dato);
    }

    // FINALIZADO
    protected void deleteDato(String llave)
    {
        this.datos.deleteEntrada(llave);
    }

    protected TiposAutorizados getTipo() {
        return this.tipo;
    }

    // FINALIZADO
    public NodoHoja<String,String> getPrimeraHoja()
    {
        return this.datos.getPrimeraHoja();
    }

    public String getNombre() { return this.nombre; }
}
