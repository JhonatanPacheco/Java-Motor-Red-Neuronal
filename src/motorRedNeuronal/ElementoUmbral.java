package motorRedNeuronal;

/**Elemento para simular la entrada umbral con valor -1. Todos los m�todos de esta clase est�n vac�os.*/
public class ElementoUmbral implements INeurona
{

	/**Este m�todo no hace nada en esta neurona. Seg�n el dise�o, no hay motivo para invocarlo.*/
	public void calcular(float in, INeurona origen)
	{}

	/**Este m�todo no hace nada en esta clase, y devuelve el control a el objeto que lo invoca.*/
	public void retropropagar(float deltaAnterior, INeurona origen)
	{}

	/**Este m�todo no hace nada en esta neurona. Seg�n el dise�o, no hay motivo para invocarlo.*/
	public void addElementoEntrante(INeurona entrante)
	{}

	/**Este m�todo no hace nada en esta neurona. Seg�n el dise�o, no hay motivo para invocarlo.*/
	public void addElementoSaliente(INeurona saliente)
	{}

}
