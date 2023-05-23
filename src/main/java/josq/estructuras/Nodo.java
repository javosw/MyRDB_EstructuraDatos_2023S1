package josq.estructuras;

enum TipoNodo { NodoRama, NodoHoja }

abstract class Nodo<_Llave_ extends Comparable<_Llave_>> 
{
	protected Object[] llaves;
	// numeroDeLlaves es el numero de llaves que actualmente tiene este nodo
	// numeroDeLlaves aumenta cuando se inserta una nueva llave al nodo 
	// numeroDeLlaves disminuye cuando se elimina una llave al nodo
	protected int numeroDeLlaves; 
	protected Nodo<_Llave_> nodoAncestro;
	protected Nodo<_Llave_> nodoIzquierdo;
	protected Nodo<_Llave_> nodoDerecho;

	protected Nodo() 
	{
		this.numeroDeLlaves = 0;
		this.nodoAncestro = null;
		this.nodoIzquierdo = null;
		this.nodoDerecho = null;
	}

	public Nodo<_Llave_> dealExceso() 
	{
		int mitad = this.getNumeroDeLlaves() / 2;
		_Llave_ arriba = this.getLlave(mitad);

		Nodo<_Llave_> newDerecho = this.separar();

		if (this.getAncestro() == null) { this.setAncestro(new NodoRama<_Llave_>()); }

		newDerecho.setAncestro(this.getAncestro());

		newDerecho.setNodoIzquierdo(this);
		newDerecho.setNodoDerecho(this.nodoDerecho);
		if (this.getNodoDerecho() != null)
			this.getNodoDerecho().setNodoIzquierdo(newDerecho);
		this.setNodoDerecho(newDerecho);

		return this.getAncestro().pushLlaveHaciaArriba(arriba, this, newDerecho);
	}

	protected abstract Nodo<_Llave_> separar();

	protected abstract Nodo<_Llave_> pushLlaveHaciaArriba(_Llave_ llave, Nodo<_Llave_> hijoIzq, Nodo<_Llave_> hijoDer);

	public boolean hasCarencia() {
		return this.getNumeroDeLlaves() < (this.llaves.length / 2);
	}

	public boolean canEmitirLlave() {
		return this.getNumeroDeLlaves() > (this.llaves.length / 2);
	}

	public Nodo<_Llave_> getNodoIzquierdo() {
		if (this.nodoIzquierdo != null && this.nodoIzquierdo.getAncestro() == this.getAncestro())
			return this.nodoIzquierdo;
		return null;
	}

	public void setNodoIzquierdo(Nodo<_Llave_> vecino) {
		this.nodoIzquierdo = vecino;
	}

	// aclaracion: solo se devuelve nodos del mismo nodo ancestro
	public Nodo<_Llave_> getNodoDerecho() {
		if (this.nodoDerecho != null && this.nodoDerecho.getAncestro() == this.getAncestro())
			return this.nodoDerecho;
		return null;
	}

	public Nodo<_Llave_> getAnyNodoDerecho() {
		if (this.nodoDerecho != null) return this.nodoDerecho;
		return null;
	}

	public void setNodoDerecho(Nodo<_Llave_> vecino) {
		this.nodoDerecho = vecino;
	}

	public Nodo<_Llave_> dealCarencia() 
	{
		if (this.getAncestro() == null) return null;

		Nodo<_Llave_> izq = this.getNodoIzquierdo();
		if (izq != null && izq.canEmitirLlave()) 
		{
			this.getAncestro().doEmisionDeHijos(this, izq, izq.getNumeroDeLlaves() - 1);
			return null;
		}

		Nodo<_Llave_> der = this.getNodoDerecho();
		if (der != null && der.canEmitirLlave()) 
		{
			this.getAncestro().doEmisionDeHijos(this, der, 0);
			return null;
		}

		if (izq != null) { return this.getAncestro().doUnificarHijos(izq, this); } 
		else { return this.getAncestro().doUnificarHijos(this, der); }
	}

	protected abstract void doEmisionDeHijos(Nodo<_Llave_> receptor, Nodo<_Llave_> emisor, int recibirEn);

	protected abstract Nodo<_Llave_> doUnificarHijos(Nodo<_Llave_> hijoIzq, Nodo<_Llave_> hijoDer);

	protected abstract void doUnificarConVecino(_Llave_ enterrar, Nodo<_Llave_> derecho);

	protected abstract _Llave_ emitirDesdeVecino(_Llave_ enterrar, Nodo<_Llave_> vecino, int recibirEn);

	public int getNumeroDeLlaves() {
		return this.numeroDeLlaves;
	}

	@SuppressWarnings("unchecked")
	public _Llave_ getLlave(int index) {
		return (_Llave_) this.llaves[index];
	}

	public void setLlave(int index, _Llave_ llave) {
		this.llaves[index] = llave;
	}

	public Nodo<_Llave_> getAncestro() {
		return this.nodoAncestro;
	}

	public void setAncestro(Nodo<_Llave_> ancestro) {
		this.nodoAncestro = ancestro;
	}

	public abstract TipoNodo getTipoDeNodo();

	// en un nodo hoja devuelve -1 si no se encuentra la llave en dicho nodo
	// en un nodo rama devuelve el indice que podria contener la llave en dicho nodo
	public abstract int getPosicionDeLlave(_Llave_ llave);

	public boolean hasExceso() 
	{
		return this.getNumeroDeLlaves() == this.llaves.length;
	}

}