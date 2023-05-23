package josq.estructuras;

public class ArbolBp<_Llave_ extends Comparable<_Llave_>, _Valor_> 
{
	private Nodo<_Llave_> raiz;

	public ArbolBp() 
	{
		this.raiz = new NodoHoja<_Llave_, _Valor_>();
	}

	public boolean hasLlave(_Llave_ llave)
	{
		if(llave == null) {return false;}

		NodoHoja<_Llave_, _Valor_> hoja = this.getHoja(llave);

		int index = hoja.getPosicionDeLlave(llave);
		if (index == -1) {return false;}
		return true;
	}

	// si la llave ya existe entonces se actualiza el valor sino se crea una nueva entrada
	public void setEntrada(_Llave_ llave,_Valor_ valor)
	{
		if(llave == null) {return;}

		if(this.hasLlave(llave)) { this.updateValor(llave, valor); }
		else { this.addEntrada(llave, valor); } 
	}

	// aclaracion: la llave de esta entrada podria replicarse
	private void addEntrada(_Llave_ llave, _Valor_ valor) 
	{
		if(llave == null) {return;}

		NodoHoja<_Llave_, _Valor_> hoja = this.getHoja(llave);
		hoja.insertarLlave(llave, valor);

		if (hoja.hasExceso()) 
		{
			Nodo<_Llave_> n = hoja.dealExceso();
			if (n != null) this.raiz = n;
		}
	}

	// aclaracion: la llave de esta entrada no debe replicarse
	public void addEntradaSinReplicas(_Llave_ llave, _Valor_ valor)
	{
		if(llave == null) {return;}

		if(!this.hasLlave(llave)) { this.addEntrada(llave, valor); }
	}

	// aclaracion: para actualizar el valor su llave tiene que estar ya ingresada
	public void updateValor(_Llave_ llave, _Valor_ valor)
	{
		if(llave == null) {return;}

		NodoHoja<_Llave_, _Valor_> hoja = this.getHoja(llave);

		int index = hoja.getPosicionDeLlave(llave);
		if(index == -1) { return; }

		hoja.setValor(index,valor);
	}

	public _Valor_ getValor(_Llave_ llave) 
	{
		if(llave == null) {return null;}

		NodoHoja<_Llave_, _Valor_> hoja = this.getHoja(llave);

		int index = hoja.getPosicionDeLlave(llave);
		return (index == -1) ? null : hoja.getValor(index);
	}

	public void deleteEntrada(_Llave_ llave) 
	{
		if(llave == null) {return;}

		NodoHoja<_Llave_, _Valor_> hoja = this.getHoja(llave);

		if (hoja.delete(llave) && hoja.hasCarencia()) 
		{
			Nodo<_Llave_> n = hoja.dealCarencia();
			if (n != null)
				this.raiz = n;
		}
	}

	@SuppressWarnings("unchecked")
	private NodoHoja<_Llave_, _Valor_> getHoja(_Llave_ llave)
	{
		if(llave == null) {return null;}

		Nodo<_Llave_> node = this.raiz;
		while (node.getTipoDeNodo() == TipoNodo.NodoRama) {
			node = ((NodoRama<_Llave_>) node).getHijo(node.getPosicionDeLlave(llave));
		}

		return (NodoHoja<_Llave_, _Valor_>) node;
	}

	// FINALIZADO
	@SuppressWarnings("unchecked")
	public NodoHoja<_Llave_, _Valor_> getPrimeraHoja()
	{
		Nodo<_Llave_> node = this.raiz;
		while (node.getTipoDeNodo() == TipoNodo.NodoRama) {
			node = ((NodoRama<_Llave_>) node).getHijo(0);
		}

		return (NodoHoja<_Llave_, _Valor_>) node;
	}
}
