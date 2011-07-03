package motorRedNeuronal;

/**Esta interfaz la implementan todas las clases que pueden formar parte de la red neuronal,
 * y define m�todos comunes para toda la red.*/
public interface INeurona
{
	/**Este m�todo calcula, si tiene datos suficientes, una salida a partir de la entrada.
	 * Antes de acabar, invoca este mismo m�todo en todas las INeurona conectadas a este objeto.
	 * No todos los elementos de la red proporcionan una implementaci�n de este m�todo,
	 * que en algunos casos est� vac�o.*/
	public void calcular (float in, INeurona origen);
	
	/**Este m�todo efectua el aprendizaje por retropropagaci�n, si el objeto tiene datos suficientes.
	 * Antes de acabar, invoca este mismo m�todo en todas las INeurona conectadas a este objeto.
	 * No todos los elementos de la red proporcionan una implementaci�n de este m�todo,
	 * que en algunos casos est� vac�o.*/
	public void retropropagar(float deltaAnterior, INeurona origen);
	
	/**A�ade una INeurona como elemento entrante.
	 * No todos los elementos de la red proporcionan una implementaci�n de este m�todo,
	 * que en algunos casos est� vac�o.
	 */	
	public void addElementoEntrante(INeurona entrante);
	
	/**A�ade una INeurona como elemento saliente.
	 * No todos los elementos de la red proporcionan una implementaci�n de este m�todo,
	 * que en algunos casos est� vac�o.
	 */	
	public void addElementoSaliente(INeurona saliente);

}
