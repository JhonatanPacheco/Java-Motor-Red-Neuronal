package motorRedNeuronal;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**Esta clase codifica el funcionamiento de un perceptron, para funcionar solo o en compa��a de otros formando una red neuronal.
 * El perceptr�n tiene elementos entrantes y elementos salientes conectados. Si el perceptr�n esta en la primera o �ltimca capa,
 * estos elementos ser�n del tipo {@link ElementoEntrada} o {@link ElementoSalida} respectivamente.
 * El c�lculo de salida se realiza mediante un objeto {@link IFuncionCalculo}, al que se le pasa la suma de las entradas poderada con los pesos.
 * El c�lculo y la retroprogramaci�n se realizan cuando todos los elementos entrantes y salientes le han pasado un valor. Si el perceptr�n
 * no tiene todos los valores, devuelve el control al m�todo llamante, para que prosiga el c�lculo por la red neuronal.*/
public class Perceptron implements INeurona
{

	/////////////////////////////////////////
	//	ATRIBUTOS
	/////////////////////////////////////////
	
	/**Diccionario con pares K={@link INeurona}, V={@link Float} con las neuronas entrantes y los valores dados por estas.*/
	private HashMap <INeurona,Float> entrantes = new LinkedHashMap<INeurona, Float>();
	
	/**Diccionario con pares K={@link INeurona}, V={@link Float} con las neuronas salientes y los valores dados por esta para la retroprogramaci�n.*/
	private Map <INeurona,Float> salientes = new HashMap<INeurona, Float>();
	
	/**Diccionario auxiliar con todos los valores nulos para clonarlo y reiniciar el diccionario entrante*/
	private HashMap <INeurona,Float> entrantesSinDatos = new LinkedHashMap<INeurona, Float>();
	/**Diccionario auxiliar con todos los valores nulos para clonarlo y reiniciar el diccionario entrante*/
	private HashMap <INeurona,Float> salientesSinDatos = new HashMap<INeurona, Float>();
	
	/**Entero para contar cu�ntas neuronas entrantes nos han pasado ya su dato. Es uno porque el dato de la neurona umbral siempre est� activo*/
	private int entrantesListas=1;
	
	/**Entero para contar cu�ntas neuronas salientes nos han pasado ya su dato en la retroprogramaci�n.*/
	private int salientesListas=0;
	
	/**Flotante para calcular el peso m�nimo de una entrada.*/
	private float pesoMinimo;
	
	/**Flotante para calcular el peso m�ximo de una entrada.*/
	private float pesoMaximo;
	
	/**Flotante que representa el factor de correcci�n.*/
	private float fCorreccion;
	
	/**Flotante que representa el momento. Si no se desea momento, p�ngase a cero.*/
	private float momento;
	
	/**Funci�n de c�lculo para calcular la salida del perceptr�n.*/
	private IFuncionCalculo funcion;
	
	/**Pesos de las entradas*/
	private Map <INeurona,Float> pesos = new LinkedHashMap<INeurona, Float>();
	
	/**Almacena el �ltimo valor calculado para usarlo en la retroprogramaci�n*/
	private float ultimaSalida = 0;
	
	/**Almacena la �ltima entrada para la cual se calcul� el atributo ultimoValor*/
	private Map <INeurona,Float> ultimaEntrada = new HashMap<INeurona, Float>();
	
	/**Almacena el diferencial de la retroprogramaci�n anterior para aplicar con los momentos*/
	private Map <INeurona,Float> diferencialesAnteriores = new HashMap<INeurona, Float>();
	
	/////////////////////////////////////////
	//	CONSTRUCTOR
	/////////////////////////////////////////
	
	/**Constructor de la clase perceptron.
	 * Construye un perceptr�n con los par�metros dados y sin ninguna conexi�n entrante o saliente.
	 * Lanza una IllegalArgumentException si pesoMin > pesoMax.*/
	public Perceptron(float fCorreccion, float momento, float pesoMin, float pesoMax, IFuncionCalculo funcion)
	{
		if (pesoMin>pesoMax)
			throw new IllegalArgumentException("El peso m�nimo no puede ser mayor que el peso m�ximo");
		
		this.pesoMinimo=pesoMin;
		this.pesoMaximo=pesoMax;
		this.fCorreccion=fCorreccion;
		this.momento= momento;
		this.funcion=funcion;
		
		//Inicio de la neurona umbral		
		//Crear y a�adir la neurona umbral
		INeurona umbral = new ElementoUmbral();
		
		this.addElementoEntrante(umbral);
		
		//Modificar la entrada desde la neurona umbral para que sea -1
		entrantes.put(umbral, new Float(-1));
		
		//A�adir la entrada desde la neurona umbral al diccionario entrantesSinDatos, para que se copie en cada iteraci�n
		//y no se quede esta clase esperando su entrada.
		entrantesSinDatos.put(umbral, new Float(-1));
		
		//Modificar el peso de la neurona umbral para que sea 1.
		pesos.put(umbral, new Float(1));
		
	
	}
	
	/////////////////////////////////////////
	//	M�TODOS PRIVADOS
	/////////////////////////////////////////
	private float nuevoPeso()
	{
		Random r = new Random();
		
		return (r.nextFloat() * (pesoMaximo-pesoMinimo)) + pesoMinimo;
	}
	

	/////////////////////////////////////////
	//	M�TODOS P�BLICOS
	/////////////////////////////////////////
	
	/**Calcular la salida del perceptron para los valores de entrada.
	 * La funci�n toma el valor de otra neurona de entrada y devuelve el control, si aun no tiene los valores de todas
	 * las neuronas entrantes. En el momento en que lo tenga, calcula su salida e invoca el c�lculo en las neuronas salientes.*/
	@SuppressWarnings("unchecked")
	@Override
	public void calcular(float in, INeurona origen)
	{
		//1. Comprobar si el valor para la neurona origen era null. Si as� era, tenemos una entrada m�s
		if (entrantes.get(origen)==null)
			entrantesListas++;
		
		//2 Actualizar el valor para la neurona origen
		entrantes.put(origen, new Float(in));
		
		//3. Si ya est�n listas todas las entrantes, calcular y pasar el resultado a las salientes
		if(entrantesListas == entrantes.size())
		{
			//3.1 Hacer la suma ponderada
			float suma = 0;
			
			Iterator <INeurona> it = entrantes.keySet().iterator();
			
			while(it.hasNext())
			{
				INeurona n = it.next();
				
				if((entrantes.get(n) == null))
					System.out.println("(entrantes.get(n) == null) ");
				if(pesos.get(n) == null)
					System.out.println("(pesos.get(n) == null) "+pesos.size() +" "+entrantes.size());
				
				suma += entrantes.get(n).floatValue() * pesos.get(n).floatValue();
			}
			
			//3.2 Calcular el valor de la funci�n
			float valor = funcion.calcula(suma);
			
			
			//3.3 Guardar las entradas en la lista de �ltimas entradas
			ultimaEntrada = (Map<INeurona, Float>) entrantes.clone();
			
			//3.4 Reiniciar las listas
			entrantes = (HashMap<INeurona, Float>) entrantesSinDatos.clone();
			entrantesListas = 1; //1 porque la neurona umbral siempre est� lista.
			
			//3.5 Llamar a la funci�n calcular de todas las neuronas salientes
			it = salientes.keySet().iterator();
			while(it.hasNext())
				it.next().calcular(valor, this);
			
			ultimaSalida=valor;
		}
		//4.En otro caso acabamos		
	}

	/**Funci�n de retropropagaci�n.
	 * Recibe el resultado de la funci�n delta del resultado esperado, multiplicado por el peso que asocia la neurona origen a este perceptron.
	 * De esta forma para obtener el sumatorio no necesitamo conocer los pesos que las neuronas salientes adjudican a este perceptron*/
	@SuppressWarnings("unchecked")
	@Override
	public void retropropagar(float deltaPonderado,INeurona origen)
	{
		//1. Comprobar si el valor para la neurona origen era null. Si as� era, tenemos una entrada m�s
		if (salientes.get(origen)==null)
			salientesListas++;

		//2 Actualizar el valor para la neurona origen
		salientes.put(origen, new Float(deltaPonderado));
		
		//3. Si ya est�n listas todas las salientes efectuar la retroprogramaci�n
		if(salientesListas == salientes.size())
		{
			//3.1 Hacer la suma de las deltas salientes.
			float suma = 0;
			
			Iterator <INeurona> it = salientes.keySet().iterator();
			

			while(it.hasNext())
			{
				INeurona n = it.next();
				suma += salientes.get(n);
			}
			
			//3.2 Calcular el valor de la regla delta
			float deltaMinuscula = ultimaSalida*(1-ultimaSalida)*suma;
			
			
			//3.3 Reiniciar las listas de datos de retropropagaci�n
			salientes = (Map<INeurona, Float>) salientesSinDatos.clone();
			salientesListas = 0;
			
			//3.4 Llamar a la funci�n retropropagar de todas las neuronas entrantes pas�ndole el delta min�scula ponderado por su peso
			it = entrantes.keySet().iterator();
			
			while(it.hasNext())
			{
				INeurona neuro = it.next();
				neuro.retropropagar(pesos.get(neuro).floatValue() *deltaMinuscula, this);
			}
						
			//3.5 Actualizar los pesos		
			it = pesos.keySet().iterator();
			
			while(it.hasNext())
			{
				INeurona neuro = it.next();
				
				//Calcular el nuevo diferencial
				float diferencial = (deltaMinuscula * fCorreccion * ultimaEntrada.get(neuro).floatValue() + momento*diferencialesAnteriores.get(neuro).floatValue());
				
				//Actualizar los pesos con el nuevo diferencial
				pesos.put(neuro, new Float( pesos.get(neuro).floatValue()+ diferencial));
				
				//Guardar el diferencial para tenerlo para calcular el momento de la pr�xima iteraci�n.
				diferencialesAnteriores.put(neuro, new Float(diferencial));
			}
		}
		//4.En otro caso acabamos
		
	}

	/**Funci�n para conectar un perceptr�n a la salida*/
	@Override
	public void addElementoEntrante(INeurona entrante)
	{
		// A�adir la entrante
		entrantes.put(entrante, null);
		entrantesSinDatos.put(entrante, null);
		pesos.put(entrante, new Float(nuevoPeso()));
		
		//Iniciamos todos los diferenciales anteriores a 0
		diferencialesAnteriores.put(entrante, new Float(0));
		
	}

	/**Funci�n para conectar un perceptr�n como entrada*/
	@Override
	public void addElementoSaliente(INeurona saliente)
	{
		salientes.put(saliente, null);
		salientesSinDatos.put(saliente, null);		
	}

	/**Redefinicion de toString*/
	public String toString()
	{
		String sal = "[ "; 

		Iterator<Entry<INeurona,Float>> it = pesos.entrySet().iterator();

		if(it.hasNext())
		{
			Entry <INeurona,Float> e = it.next();
			sal += e.getValue();
		}
		
		while (it.hasNext())
		{
			Entry <INeurona,Float> e = it.next();
			sal +=", " + e.getValue();
		}
		
		sal += " :"+ this.ultimaSalida + "]";
		
		return sal;
	}
	
	/**Recorre los pesos de las neuronas entrantes en orden de inserci�n -por ser pesos un LinkedHashMap- y devuelve una copia de los valores.*/
	public List <Float> getPesos()
	{
		List <Float> sal = new ArrayList<Float>();
		
		//Recorrer los pesos de las neuronas en orden a�adiendo los pesos a la lista de salida
		Iterator <Float> it = pesos.values().iterator();
		
		while (it.hasNext())
		{
			sal.add(new Float(it.next()));
		}
			
		return sal;
	}
	
	/**Recorre los pesos de las neuronas entrantes en orden de inserci�n -por ser pesos un LinkedHashMap- y sustituye los pesos por una copia de los pasados como parametro.
	 * Si el n�mero de pesos de la lista pasada como argumento no es igual al n�mero de neuronas entrantes, eleva un IllegalArgumentException.*/
	public void setPesos(List <Float> in)
	{

		if (pesos.size() != in.size())
			throw new IllegalArgumentException("El n�mero de pesos es desigual al n�mero de neuronas entrantes.");
		
		Iterator <INeurona> itPesos = pesos.keySet().iterator();
		Iterator <Float> itIn = in.iterator();
		
		while(itPesos.hasNext())
			pesos.put(itPesos.next(), new Float(itIn.next()));
	}

	/**Convierte la salida de getPesos() en un String donde cada peso est� separado por un espacio.*/
	public String getPesosString()
	{
		String sal = "";
		
		List <Float> lisPesos = getPesos();
		
		Iterator <Float> it = lisPesos.iterator();
		while(it.hasNext())
			sal+=it.next().toString()+" ";
		
		return sal;
	}
	
	/**Envoltorio de void setPesos(List <Float> in) que crea la lista de float a partir de un String*/
	public void setPesos(String s)
	{
		String [] trocitos = s.split(" ", 0);
		
		List <Float> pesosFloat = new ArrayList<Float>();
		
		for (int i = 0 ;i<trocitos.length; i++)
			pesosFloat.add(new Float(trocitos[i]));
		
		setPesos(pesosFloat);
	}
}
