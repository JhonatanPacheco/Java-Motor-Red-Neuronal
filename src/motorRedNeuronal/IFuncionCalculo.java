package motorRedNeuronal;

/**Esta interfaz la implementar�n las clases que codifiquen una funci�n que calcule la salida de un perceptron.*/
 
public interface IFuncionCalculo
{
	/**Funci�n de c�lculo de la clase.
	 * Recive un float (las entradas ya sumadas) y devuelve otro float con el resultado.*/
	public float calcula(float in);

}
