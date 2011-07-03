package motorRedNeuronal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**Los elementos de entrada son neuronas ficticias que recogen los resultados de la red y la alimentan para la retroprogramaci�n.*/
public class ElementoSalida implements INeurona
{
	/**Almacena la �ltima salida para su consulta*/
	float ultimaSalida;
	
	/**Neuronas conectadas a la entrada de este elemento de salida*/
	private INeurona entrante;

	/**En un elemento de salida, la funci�n calcular guarda el dato a calcular como salida
	 * y devuelve para que siga fluyendo la retropropagaci�n por la red neuronal.*/
	@Override
	public void calcular(float in, INeurona origen)
	{
		ultimaSalida=in;
	}

	/**Le pasa el dato a la neurona entrante.
	 * Al llamar a este m�todo desde fuera de la red, habr� que pasarle null como origen.*/
	@Override
	public void retropropagar(float deltaAnterior,INeurona origen)
	{
			entrante.retropropagar(deltaAnterior, this);
	}

	/**A�ade una neurona entrante a este elemento*/
	@Override
	public void addElementoEntrante(INeurona entrante)
	{
		this.entrante=entrante;
	}

	/**En un elemento saliente, este m�todo no hace nada.*/
	@Override
	public void addElementoSaliente(INeurona saliente)
	{ }

}
