package motorRedNeuronal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**Los elementos de entrada son neuronas ficticias que alimentan de entradas a la red*/
public class ElementoEntrada implements INeurona
{
	/**Lista de neuronas conectadas a la salida de este elemento de entrada*/
	List <INeurona> salientes = new ArrayList<INeurona>();

	/**Recorre la lista de neuronas salientes y le pasa el dato.
	 * Al llamar a este m�todo desde fuera de la red, habr� que pasarle null como origen.*/
	@Override
	public void calcular(float in, INeurona origen)
	{
		Iterator <INeurona> it = salientes.iterator();
		while(it.hasNext())
			it.next().calcular(in, this);
	}

	/**En un elemento de entrada, la funci�n retropropagar no hace nada,
	 * para que siga fluyendo la retropropagaci�n por la red neuronal.*/
	@Override
	public void retropropagar(float resultado, INeurona origen)
	{ }

	/**En un elemento entrante, este m�todo no hace nada.*/
	@Override
	public void addElementoEntrante(INeurona entrante)
	{ }

	/**A�ade una neurona saliente a este elemento*/
	@Override
	public void addElementoSaliente(INeurona saliente)
	{
		salientes.add(saliente);
	}

}
