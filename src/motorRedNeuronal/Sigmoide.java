package motorRedNeuronal;

/**Clase que implementa la funci�n sigmoide como funci�n de c�lculo para el perceptron.*/
public class Sigmoide implements IFuncionCalculo {

	/**Funci�n sigmoide.*/
	@Override
	public float calcula(float in)
	{
		return (float) (1/(1+Math.exp(-in)));
	}

}
