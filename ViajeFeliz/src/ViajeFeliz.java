

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.management.RuntimeErrorException;

public class ViajeFeliz implements Existir {
	String cuit;
	HashMap<String, Cliente> clientes;
	HashMap<Integer, PaquetePredefinido> paquetesPredef;
	HashMap<Integer, ServicioSimples> serviciosSimples;

	public ViajeFeliz(String cuit) {
		setCuit(cuit);
		this.clientes = new HashMap<>();
		this.paquetesPredef = new HashMap<>();
		this.serviciosSimples = new HashMap<>();
	}

	/**
	 * 2) Registrar un cliente nuevo con su nombre, dirección y dni.
	 * 
	 * @param dni
	 * @param nombre
	 * @param direccion
	 */
	void registrarCliente(String dni, String nombre, String direccion) {
			if (existe(dni)) {
				throw new RuntimeException("El cliente ya existe");
			}
			Cliente clien = new Cliente(dni, nombre, direccion);

			clientes.put(dni, clien);

	};

	/**
	 * 3) Registrar un servicio de Vuelo.
	 * 
	 * @param costoBase
	 * @param fechaInicio
	 * @param cantidad
	 * @param pais
	 * @param ciudad
	 * @param fechaLlegada
	 * @param tasa
	 * @return
	 */
	int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad,
			String fechaLlegada, double tasa) {
		try {
			Vuelo vuelo = new Vuelo(costoBase, fechaInicio, cantidad, pais, ciudad, fechaLlegada, tasa);

			int codigo = setearCodigoRandom();

			serviciosSimples.put(codigo, vuelo);

			
			return codigo;
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	};

	/**
	 * 
	 * 3) Registrar un servicio de Alojamiento
	 * 
	 * @param costoBase
	 * @param fechaInicio
	 * @param cantidad
	 * @param pais
	 * @param ciudad
	 * @param fechaSalida
	 * @param hotel
	 * @param costoTraslado
	 * @return
	 */
	int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad,
			String fechaLSalida, String hotel, double costoTraslado) {
		try {
			Alojamiento alojamiento = new Alojamiento(costoBase, fechaInicio, cantidad, pais, ciudad, fechaLSalida,
					hotel, costoTraslado);

			int codigo = setearCodigoRandom();

			serviciosSimples.put(codigo, alojamiento);

			return codigo;
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}

	};

	/**
	 * 3) Registrar un servicio de Alquiler
	 * 
	 * @param costoBase
	 * @param fechaInicio
	 * @param cantidad
	 * @param pais
	 * @param ciudad
	 * @param fechaDevolucion
	 * @param garantia
	 * @return
	 */
	int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, double garantia,
			String fechaDevolucion) {

		try {
			Alquiler alquiler = new Alquiler(costoBase, fechaInicio, cantidad, pais, ciudad, fechaDevolucion, garantia);

			int codigo = setearCodigoRandom();

			serviciosSimples.put(codigo, alquiler);
			return codigo;

		} catch (Error e) {

			throw new RuntimeErrorException(e, e.getMessage());
		}

	}

	/**
	 * 
	 * 3) Registrar un servicio de Excursion
	 * 
	 * @param costoBase
	 * @param fechaInicio
	 * @param cantidad
	 * @param lugarSalida
	 * @param esDiaCompleto
	 * @return codigo de servicio unico
	 */
	int crearServicio(double costoBase, String fechaInicio, int cantidad, String lugarSalida, boolean esDiaCompleto) {
		try {
			Excursion excursion = new Excursion(costoBase, fechaInicio, cantidad, lugarSalida, esDiaCompleto);

			int codigo = setearCodigoRandom();

			serviciosSimples.put(codigo, excursion);
			return codigo;
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	}

	/**
	 * 4) Crear un paquete predefinido con los servicios recibidos por Parametro. Se
	 * pueden crear varias copias del paquete para su uso.
	 * 
	 * @param cantPaquetes
	 * @param codigosDeServicios
	 * @return arreglo con los codigos de los paquetes generados.
	 */
	int[] crearPaquetesPredefinidos(int cantPaquetes, int[] codigosDeServicios) {
		int[] codigoPaquetes = new int[cantPaquetes];

		for (int i = 0; i < cantPaquetes; i++) {
			int codigo = setearCodigoRandom();
			PaquetePredefinido paquetePredef = new PaquetePredefinido(codigo);

			// segundo for
			agregarServicioAPaquetePredefinido(paquetePredef, codigosDeServicios);
			// segundo for

			this.paquetesPredef.put(codigo, paquetePredef); // se guarda el paquetePredefinido en el hashmap de paquetes

			codigoPaquetes[i] = codigo;
		}

		eliminarServiciosLuegoDeGuardarEnPaquete(codigosDeServicios);

		return codigoPaquetes;
	};

	public void agregarServicioAPaquetePredefinido(PaquetePredefinido paquetePredef, int[] codigosDeServicios) {
		for (int j = 0; j < codigosDeServicios.length; j++) {
			ServicioSimples serv = serviciosSimples.get(codigosDeServicios[j]);
			paquetePredef.añadirServicio(serv);
		}
	}

	public void eliminarServiciosLuegoDeGuardarEnPaquete(int[] codigosDeServicios) {
		for (int i = 0; i < codigosDeServicios.length; i++) {

			serviciosSimples.remove(codigosDeServicios[i]);
		}
	}

	/**
	 * 5) Se Inicia la contratacion de un servicio o paquete predefinido por parte
	 * del cliente. Si tiene contrataciones previas sin finalizar, se debe lanzar
	 * una excepción.
	 * 
	 * 
	 * @param dni
	 * @param codServicio
	 * @return codigo de paquete personalizado asociado a la contratación
	 */
	int iniciarContratacion(String dni, int codServicio) {
		try {
			existe(dni);
			Cliente clien = clientes.get(dni);
			if (clien.getPaquete() == null) {
				clien.crearPaquete();
				if (serviciosSimples.containsKey(codServicio)) {
					clien.añadirAPaquetePersonal(codServicio, serviciosSimples.get(codServicio));
				} else {
					clien.añadirAPaquetePersonal(codServicio, paquetesPredef.get(codServicio));
				}
				return clien.getPaquete().getCodigoUnico();
			} else {
				throw new RuntimeException("el cliente tiene paquetes sin finalizar");
			}
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	};

	/**
	 * 6) Agrega un servicio o paquete predefinido a la contratación vigente de un
	 * cliente. Al asociar el servicio con el cliente. se debe quitar del registro
	 * de servicios que tiene la empresa.
	 * 
	 * @param dni
	 * @param codServicio
	 */
	void agregarServicioAContratacion(String dni, int codServicio) {
		try {
			existe(dni); // verificamos que exista tanto el cliente como el servicio

			Cliente clien = clientes.get(dni);
			if (existeServicio(codServicio)) {
				ServicioSimples serv = serviciosSimples.get(codServicio);
				quitarServicio(codServicio);
				clien.añadirAPaquetePersonal(codServicio, serv);
			} else if (existePaquete(codServicio)) {

				PaquetePredefinido paq = paquetesPredef.get(codServicio);
				quitarPaquetePredef(codServicio);
				clien.añadirAPaquetePersonal(codServicio, paq);
			} else {
				throw new Error("el paquete o servicio no existe");
			}

		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	};



	private void quitarServicio(int codServicio) {
		serviciosSimples.remove(codServicio);
	}

	private void quitarPaquetePredef(int codServicio) {
		paquetesPredef.remove(codServicio);
	}

	/**
	 * 7) Quitar un servicio de la contratacion vigente del cliente. Si es un
	 * servicio simple, se borra del sistema. TODO: Si es un paquete, se debe
	 * regresar al sistema como paquete disponible
	 * 
	 * 
	 * @param dni
	 * @param codServicio
	 */
	void quitarServicioDeContratacion(String dni, int codServicio) {
		try {
			existe(dni);// revisamos que exista el dni

			Cliente clien = clientes.get(dni);
			PaquetePredefinido paquete = clien.quitarDePaquete(codServicio);
			if (paquete != null) {
				paquetesPredef.put(codServicio, paquete);
			}

		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}

	};

	/**
	 * 8) Calcular el total a pagar por alguno de los paquetes personalizados que
	 * contrató el cliente. Para esto se identifica al cliente con el dni y el
	 * paquete personalizado por su codigo unico. Puede ser el paquete asociado a la
	 * contratación actual o alguno de los paquetes contratados en el pasado.
	 * 
	 * @param dni
	 * @param codPaquetePersonalizado
	 * @return
	 */
	double calcularCostoDePaquetePersonalizado(String dni, int codPaquetePersonalizado) {

		try {
			existe(dni);

			Cliente clien = clientes.get(dni);

			double costo = clien.obtenerCostoPaquete(codPaquetePersonalizado);

			return costo;
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	};

	/**
	 * 9) Efectiviza la contratación registrando que fué pagado y registrando la
	 * fecha de pago.
	 * 
	 * La fecha de pago debe ser anterior que la fecha de inicio del paquete
	 * personalizado.
	 * 
	 * 
	 * @param dni
	 * @param fechaPago
	 * @return
	 */
	double pagarContratacion(String dni, String fechaPago) {
		try {
			Cliente clien = clientes.get(dni);
			Date fecha = convertirStringADate(fechaPago);
			return clien.pagarContratacion(fecha);
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	};

	/**
	 * 10) Devolver un listado con los codigos unicos de los paquetes que contrató
	 * un cliente desde que lo registró la empresa, conociendo su dni.
	 * 
	 * @param dni
	 * @return
	 */
	LinkedList<Integer> historialDeContrataciones(String dni) {
		try {
			Cliente clien = clientes.get(dni);
			return clien.historialDeContrataciones();
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	};

	/**
	 * 11) Devolver los paquetes que aún no se iniciaron dando nombre del cliente,
	 * fecha de inicio y los datos de los servicios contratados. " {nombre_cliente}
	 * | {fecha_inicio} | [ {tipo_servicio_1}, {tipo_servicio_2}] Ejemplo: Homero |
	 * 2024-02-14 | [Vuelo, Alojamiento, Excursion]
	 * 
	 * @param fecha
	 * @return
	 */
	String contratacionesSinIniciar(String fecha) {
		String paquetes = "";
		for (Cliente cl : clientes.values()) {
			paquetes += cl.contratacionesSinIniciar(convertirStringADate(fecha));
		}
		return paquetes;
	};

	/**
	 * 12) Devuelve una lista con los datos de las contrataciones que se inician en
	 * la fecha pasada por parámetro. Indicando a que cliente pertenecen. Formato de
	 * cada entrada: "{codigoUnicoContratacion} - ({dniCliente} {nombreCliente})"
	 * 
	 * @param fecha
	 * @return
	 */
	List<String> contratacionesQueInicianEnFecha(String fecha) {
		List<String> lista = new ArrayList<>();
		for (Cliente cl : clientes.values()) {
			lista.add(cl.contratacionesQueInicianEnFecha(convertirStringADate(fecha)));				
		}
		return lista;
	};

	/**
	 * 13)
	 * Devuelve un conjunto con las claves de los servicios y paquetes
	 * predefinidos ofertados por la empresa.
	 * 
	 * @return
	 */
	public Set<Integer> obtenerCodigosCatalogo(){
		
		Set<Integer> codigos = new HashSet<Integer>();
		
		Set<Integer> clavesServicios = serviciosSimples.keySet();
		Set<Integer> clavesPaquetes = paquetesPredef.keySet();
		agregarCodigoAConjunto(clavesServicios,codigos);
	    
		agregarCodigoAConjunto(clavesPaquetes,codigos);
		
	    return codigos;
		
	}
	
	
	private void agregarCodigoAConjunto(Set<Integer> map, Set<Integer> codigos) {
		for (Integer key : map) {
		      codigos.add(key);
		}
	}

	//
	//
	//
	
	
	
	/*	FUNCIONES AUXILIARES	*/
	
	//CODIGO UNICO
	public int codigoRandom() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			Random r = new Random();
			int valorRan = r.nextInt(10) + 1;
			str.append(valorRan);
		}

		int strParseado = Integer.parseInt(str.toString());

		return strParseado;
	}

	public int setearCodigoRandom() {
		int codigo = codigoRandom();

		while (this.paquetesPredef.containsKey(codigo) && this.serviciosSimples.containsKey(codigo)) {
			System.out.println("a");
			codigo = codigoRandom();
		}

		return codigo;
	}
	
	//CODIGO UNICO
	
	//EXISTENCIAS e implementacion interfaz
	
	boolean existe(String dni) {
		return clientes.containsKey(dni);
	}
	
	@Override
	public boolean existeServicio(int codigo) {
		return serviciosSimples.containsKey(codigo);
	}
	

	@Override
	public boolean existePaquete(int codigo) {
		return paquetesPredef.containsKey(codigo);
	}

	//EXISTENCIAS e implementacion interfaz

	
	//CONVERTIR STRING A DATE
	public static Date convertirStringADate(String fechaStr) {
		String fechaStrFormateado = fechaStr.replace("-", "/");
		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
		formato.setLenient(false);
		Date fecha = null;
		try {
			fecha = formato.parse(fechaStrFormateado);
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fecha;
		
	}
	
	//CONVERTIR STRING A DATE
	
	/*	FUNCIONES AUXILIARES	*/
	
	/*	SETTER	*/
	
	public void setCuit(String cuit) {
		if(cuit.length() > 13) {
			throw new Error("El cuit solo puede tener 13 caracteres");
		}
		this.cuit = cuit;
	}
	
	/*	SETTER	*/

	@Override
	public String toString() {
		return "ViajeFeliz [cuit=" + cuit + "\n" + ", clientes=" + clientes + "\n" + " paquetesPredef=" + paquetesPredef
				+ "\n" + ", serviciosSimples=" + serviciosSimples + "]";
	}
	
	
	/*
	public int[] paquetesPredef() {
		int[] paqArray = { 0, 0, 0 };
		System.out.print("Los paquetes son: ");
		System.out.println(this.paquetesPredef.size());
		int i = 0;
		for (Entry<Integer, PaquetePredefinido> paq : paquetesPredef.entrySet()) {
			if (paq.getKey() != null) {
				paqArray[i] = paq.getKey();
			}
			paq.getValue().verServicios();
		}

		return paqArray;
	}

	public int[] servicios() {
		int[] serArray = { 0, 0, 0, 0 };
		System.out.print("Los servicios son: ");
		System.out.println(this.serviciosSimples.size());
		int i = 0;
		for (Entry<Integer, ServicioSimples> serv : serviciosSimples.entrySet()) {
			if (serv.getKey() != null) {
				serArray[i] = serv.getKey();
			}
			i++;
			System.out.println("clave=" + serv.getKey() + ", valor=" + serv.toString());
		}

		return serArray;
	}
	*/
}
