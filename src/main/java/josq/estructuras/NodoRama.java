package josq.estructuras;

class NodoRama<_Llave_ extends Comparable<_Llave_>> extends Nodo<_Llave_> 
{
	protected final static int ORDEN = 4;
	protected Object[] hijos;

	public NodoRama() 
	{
		this.llaves = new Object[ORDEN + 1];
		this.hijos = new Object[ORDEN + 2];
	}

	@Override
	public int getPosicionDeLlave(_Llave_ llave) 
	{
		int index = 0;
		for (index = 0; index < this.getNumeroDeLlaves(); ++index) {
			int cmp = this.getLlave(index).compareTo(llave);
			if (cmp == 0) {
				return index + 1;
			} else if (cmp > 0) {
				return index;
			}
		}

		return index;
	}

	private void insertEn(int index, _Llave_ llave, Nodo<_Llave_> hijoIzq, Nodo<_Llave_> hijoDer) 
	{
		for (int i = this.getNumeroDeLlaves() + 1; i > index; --i) 
		{
			this.setHijo(i, this.getHijo(i - 1));
		}
		for (int i = this.getNumeroDeLlaves(); i > index; --i) 
		{
			this.setLlave(i, this.getLlave(i - 1));
		}

		this.setLlave(index, llave);
		this.setHijo(index, hijoIzq);
		this.setHijo(index + 1, hijoDer);
		this.numeroDeLlaves += 1;
	}

	@Override
	protected Nodo<_Llave_> separar() {
		int mitad = this.getNumeroDeLlaves() / 2;

		NodoRama<_Llave_> newDer = new NodoRama<_Llave_>();
		for (int i = mitad + 1; i < this.getNumeroDeLlaves(); ++i) 
		{
			newDer.setLlave(i - mitad - 1, this.getLlave(i));
			this.setLlave(i, null);
		}
		for (int i = mitad + 1; i <= this.getNumeroDeLlaves(); ++i) 
		{
			newDer.setHijo(i - mitad - 1, this.getHijo(i));
			newDer.getHijo(i - mitad - 1).setAncestro(newDer);
			this.setHijo(i, null);
		}
		this.setLlave(mitad, null);
		newDer.numeroDeLlaves = this.getNumeroDeLlaves() - mitad - 1;
		this.numeroDeLlaves = mitad;

		return newDer;
	}

	@Override
	protected Nodo<_Llave_> pushLlaveHaciaArriba(_Llave_ llave, Nodo<_Llave_> hijoIzq, Nodo<_Llave_> hijoDer) 
	{
		int index = this.getPosicionDeLlave(llave);

		this.insertEn(index, llave, hijoIzq, hijoDer);

		if (this.hasExceso()) { return this.dealExceso(); } 
		else { return this.getAncestro() == null ? this : null; }
	}

	private void deleteEn(int index) 
	{
		int i = 0;
		for (i = index; i < this.getNumeroDeLlaves() - 1; ++i) 
		{
			this.setLlave(i, this.getLlave(i + 1));
			this.setHijo(i + 1, this.getHijo(i + 2));
		}
		this.setLlave(i, null);
		this.setHijo(i + 1, null);
		--this.numeroDeLlaves;
	}

	@Override
	protected void doEmisionDeHijos(Nodo<_Llave_> receptor, Nodo<_Llave_> emisor, int recibirEn) {
		int hijoReceptorEn = 0;
		while (hijoReceptorEn < this.getNumeroDeLlaves() + 1 && this.getHijo(hijoReceptorEn) != receptor)
			++hijoReceptorEn;

		if (recibirEn == 0) 
		{
			_Llave_ upLlave = receptor.emitirDesdeVecino(this.getLlave(hijoReceptorEn), emisor, recibirEn);
			this.setLlave(hijoReceptorEn, upLlave);
		} 
		else 
		{
			_Llave_ upLlave = receptor.emitirDesdeVecino(this.getLlave(hijoReceptorEn - 1), emisor, recibirEn);
			this.setLlave(hijoReceptorEn - 1, upLlave);
		}
	}

	@Override
	protected Nodo<_Llave_> doUnificarHijos(Nodo<_Llave_> hijoIzq, Nodo<_Llave_> hijoDer) 
	{
		int index = 0;
		while (index < this.getNumeroDeLlaves() && this.getHijo(index) != hijoIzq)
			++index;
		_Llave_ enterrar = this.getLlave(index);

		hijoIzq.doUnificarConVecino(enterrar, hijoDer);

		this.deleteEn(index);

		if (this.hasCarencia()) 
		{
			if (this.getAncestro() == null) 
			{
				if (this.getNumeroDeLlaves() == 0) 
				{
					hijoIzq.setAncestro(null);
					return hijoIzq;
				} 
				else { return null; }
			}

			return this.dealCarencia();
		}

		return null;
	}

	@Override
	protected void doUnificarConVecino(_Llave_ enterrar, Nodo<_Llave_> vecinoDer) 
	{
		NodoRama<_Llave_> ramaVecinaDer = (NodoRama<_Llave_>) vecinoDer;

		int j = this.getNumeroDeLlaves();
		this.setLlave(j++, enterrar);

		for (int i = 0; i < ramaVecinaDer.getNumeroDeLlaves(); ++i) 
		{
			this.setLlave(j + i, ramaVecinaDer.getLlave(i));
		}
		for (int i = 0; i < ramaVecinaDer.getNumeroDeLlaves() + 1; ++i) 
		{
			this.setHijo(j + i, ramaVecinaDer.getHijo(i));
		}
		this.numeroDeLlaves += 1 + ramaVecinaDer.getNumeroDeLlaves();

		this.setNodoDerecho(ramaVecinaDer.nodoDerecho);
		if (ramaVecinaDer.nodoDerecho != null)
			ramaVecinaDer.nodoDerecho.setNodoIzquierdo(this);
	}

	@Override
	protected _Llave_ emitirDesdeVecino(_Llave_ enterrar, Nodo<_Llave_> vecino, int recibirEn) 
	{
		NodoRama<_Llave_> ramaVecina = (NodoRama<_Llave_>) vecino;

		_Llave_ llave = null;
		if (recibirEn == 0) 
		{
			int index = this.getNumeroDeLlaves();
			this.setLlave(index, enterrar);
			this.setHijo(index + 1, ramaVecina.getHijo(recibirEn));
			this.numeroDeLlaves += 1;

			llave = ramaVecina.getLlave(0);
			ramaVecina.deleteEn(recibirEn);
		} 
		else 
		{
			this.insertEn(0, enterrar, ramaVecina.getHijo(recibirEn + 1), this.getHijo(0));
			llave = ramaVecina.getLlave(recibirEn);
			ramaVecina.deleteEn(recibirEn);
		}

		return llave;
	}

	@SuppressWarnings("unchecked")
	public Nodo<_Llave_> getHijo(int index) {
		return (Nodo<_Llave_>) this.hijos[index];
	}

	public void setHijo(int index, Nodo<_Llave_> hijo) {
		this.hijos[index] = hijo;
		if (hijo != null) hijo.setAncestro(this);
	}

	@Override
	public TipoNodo getTipoDeNodo() {
		return TipoNodo.NodoRama;
	}

}
