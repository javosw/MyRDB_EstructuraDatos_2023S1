package josq.estructuras;

public class NodoHoja<_Llave_ extends Comparable<_Llave_>, _Valor_> extends Nodo<_Llave_> 
{
	protected final static int ORDEN = 4;
	private Object[] valores;

	public NodoHoja() 
	{
		this.llaves = new Object[ORDEN + 1];
		this.valores = new Object[ORDEN + 1];
	}

	@Override
	public int getPosicionDeLlave(_Llave_ llave) 
	{
		for (int i = 0; i < this.getNumeroDeLlaves(); ++i) 
		{
			int cmp = this.getLlave(i).compareTo(llave);
			if (cmp == 0) { return i; } 
			else if (cmp > 0) { return -1; } 
		}

		return -1;
	}

	public void insertarLlave(_Llave_ llave, _Valor_ valor) 
	{
		int index = 0;
		while (index < this.getNumeroDeLlaves() && this.getLlave(index).compareTo(llave) < 0)
			++index;
		this.insertarEnPosicion(index, llave, valor);
	}

	private void insertarEnPosicion(int index, _Llave_ llave, _Valor_ valor) 
	{
		for (int i = this.getNumeroDeLlaves() - 1; i >= index; --i) 
		{
			this.setLlave(i + 1, this.getLlave(i));
			this.setValor(i + 1, this.getValor(i));
		}

		this.setLlave(index, llave);
		this.setValor(index, valor);
		++this.numeroDeLlaves;
	}

	@Override
	protected Nodo<_Llave_> separar() 
	{
		int mitad = this.getNumeroDeLlaves() / 2;

		NodoHoja<_Llave_, _Valor_> newDer = new NodoHoja<_Llave_, _Valor_>();
		for (int i = mitad; i < this.getNumeroDeLlaves(); ++i) 
		{
			newDer.setLlave(i - mitad, this.getLlave(i));
			newDer.setValor(i - mitad, this.getValor(i));
			this.setLlave(i, null);
			this.setValor(i, null);
		}
		newDer.numeroDeLlaves = this.getNumeroDeLlaves() - mitad;
		this.numeroDeLlaves = mitad;

		return newDer;
	}

	public boolean delete(_Llave_ llave) 
	{
		int index = this.getPosicionDeLlave(llave);
		if (index == -1) return false;

		this.deleteEnPosicion(index);
		return true;
	}

	private void deleteEnPosicion(int index) 
	{
		int i = index;
		for (i = index; i < this.getNumeroDeLlaves() - 1; ++i) 
		{
			this.setLlave(i, this.getLlave(i + 1));
			this.setValor(i, this.getValor(i + 1));
		}
		this.setLlave(i, null);
		this.setValor(i, null);
		--this.numeroDeLlaves;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doUnificarConVecino(_Llave_ enterrar, Nodo<_Llave_> vecinoDer) 
	{
		NodoHoja<_Llave_, _Valor_> vecino = (NodoHoja<_Llave_, _Valor_>) vecinoDer;

		int j = this.getNumeroDeLlaves();
		for (int i = 0; i < vecino.getNumeroDeLlaves(); ++i) 
		{
			this.setLlave(j + i, vecino.getLlave(i));
			this.setValor(j + i, vecino.getValor(i));
		}
		this.numeroDeLlaves += vecino.getNumeroDeLlaves();

		this.setNodoDerecho(vecino.nodoDerecho);
		if (vecino.nodoDerecho != null) vecino.nodoDerecho.setNodoIzquierdo(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected _Llave_ emitirDesdeVecino(_Llave_ enterrar, Nodo<_Llave_> vecino, int prestamoEn) 
	{
		NodoHoja<_Llave_, _Valor_> nodo_vecino = (NodoHoja<_Llave_, _Valor_>) vecino;

		this.insertarLlave(nodo_vecino.getLlave(prestamoEn), nodo_vecino.getValor(prestamoEn));
		nodo_vecino.deleteEnPosicion(prestamoEn);

		return prestamoEn == 0 ? vecino.getLlave(0) : this.getLlave(0);
	}

	// el limite es el numero de llaves en este nodo: index = [0, this.getKeyCount() - 1]
	// el limite es el orden de este nodo: index = [0, ORDEN - 1]
	@SuppressWarnings("unchecked")
	public _Valor_ getValor(int index) 
	{
		return (_Valor_) this.valores[index];
	}

	public void setValor(int index, _Valor_ valor) {
		this.valores[index] = valor;
	}

	@Override
	public TipoNodo getTipoDeNodo() {
		return TipoNodo.NodoHoja;
	}

	@Override
	protected Nodo<_Llave_> pushLlaveHaciaArriba(_Llave_ llave, Nodo<_Llave_> hijoIzq, Nodo<_Llave_> vecinoDer) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doEmisionDeHijos(Nodo<_Llave_> receptor, Nodo<_Llave_> emisor, int recibirEn) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected Nodo<_Llave_> doUnificarHijos(Nodo<_Llave_> hijoIzq, Nodo<_Llave_> hijoDer) 
	{
		throw new UnsupportedOperationException();
	}
}
