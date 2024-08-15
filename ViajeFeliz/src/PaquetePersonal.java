

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.management.RuntimeErrorException;

public class PaquetePersonal implements Existir {
	private HashMap<Integer, ServicioSimples> servicios;
	private HashMap<Integer, PaquetePredefinido> paquetes;
	private int codigoUnico;
	private double costoTotal;
	private Date fechaDePago;

	public PaquetePersonal() {
		setPaquetes();
		setServicios();
		setCodigoUnico();
	}

	
	/*	quitar PaquetePredefinido o servicio, si quita un paquete, lo devuelve para	guardarlo en ViajeFeliz de nuevo.*/
	public PaquetePredefinido quitar(int codigo) {

		if (existeServicio(codigo)) {
			setCostoTotal(-servicios.get(codigo).costoTotal); // eliminamos del costo del paquete personal el costo del
																// servicio
			servicios.remove(codigo);
			return null;

		} else if (existePaquete(codigo)) {

			PaquetePredefinido paquete = paquetes.get(codigo);
			setCostoTotal(-paquetes.get(codigo).getCostoTotal()); // eliminamos del costo del paquete personal el costo
																	// del paquete predefinido
			paquetes.remove(codigo);
			return paquete;

		} else {
			throw new Error("no existe servicio o paquete");
		}
	}
	/*	Quitar PaquetePredefinido o servicio, si quita un paquete, lo devuelve para	guardarlo en ViajeFeliz de nuevo.*/
	
	
	/*	Sobrecarga añadir servicio o paquete predefinido	*/
	public boolean añadir(int codigo, ServicioSimples serv) {
		try {
			servicios.put(codigo, serv);
			setCostoTotal(serv.costoTotal);
			return true;
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	}

	public boolean añadir(int codigo, PaquetePredefinido paq) {
		try {
			paquetes.put(codigo, paq);
			setCostoTotal(paq.getCostoTotal()); // añadimos el costo al paquetePersonal
			return true;
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	}
	/*	Sobrecarga añadir servicio o paquete predefinido	*/

	/*	Añadir fecha de pago	*/

	public boolean añadirfechaDePago(Date fechaPago) {
		if (controlarFecha(fechaPago)) {
			this.fechaDePago = fechaPago;
			return true;
		} else {
			return false;
		}
	}

	public boolean controlarFecha(Date fechaPago) {
		for (ServicioSimples s : servicios.values()) {
			if (fechaPago.after(convertirStringADate(s.fechaInicio))) {
				throw new RuntimeException("La fecha de pago es incorrecto");
			}
		}
		for (PaquetePredefinido p : paquetes.values()) {
			for (ServicioSimples s : p.getServicios()) {
				if (fechaPago.after(convertirStringADate(s.fechaInicio))) {
					throw new RuntimeException("La fecha de pago es incorrecto");
				}
			}
		}
		return true;
	}
	/*	Añadir fecha de pago	*/

	
	/*	devuelve la fecha de inicio de un paquete	*/
	public Date averiguarFechaInicio() {

		if (servicios.size() != 0 && paquetes.size() != 0) {
			Date fechaInicioServicios = averiguarFechaInicioServicios();
			Date fechaDeInicioPaquete = averiguarFechaInicioPaquetes();
			if (fechaInicioServicios.before(fechaDeInicioPaquete)) {
				return fechaInicioServicios;
			}
			return fechaDeInicioPaquete;
		}
		if (paquetes.size() != 0 && servicios.size() == 0) {
			return averiguarFechaInicioPaquetes();
		}
		return averiguarFechaInicioServicios();
	}

	public Date averiguarFechaInicioServicios() {
		Date fechaDeInicioPaquete = convertirStringADate(servicios.values().iterator().next().fechaInicio);
		for (ServicioSimples s : servicios.values()) {
			if (convertirStringADate(s.fechaInicio).before(fechaDeInicioPaquete)) {
				fechaDeInicioPaquete = convertirStringADate(s.fechaInicio);
			}
		}
		return fechaDeInicioPaquete;
	}

	public Date averiguarFechaInicioPaquetes() {
		Integer primeraClave = paquetes.keySet().iterator().next();
		Date fechaDeInicioPaquete = paquetes.get(primeraClave).averiguarFechaInicio();
		for (PaquetePredefinido p : paquetes.values()) {
			p.averiguarFechaInicio();
		}
		return fechaDeInicioPaquete;
	}
	
	/*	devuelve la fecha de inicio de un paquete	*/
	
	


	
	
	/*	CodigoRandom	*/
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
	/*	CodigoRandom	*/
	
	/*	Convierte un string a date	*/
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
	/*	Convierte un string a date	*/
	
	
	/*	Funcion utilizada por cliente	*/
	public List<String> listarservicios(){
		List<String> lista = new ArrayList<>();
		for(ServicioSimples s : servicios.values()) {
			lista.add(s.getClass().getSimpleName());
		}
		for (PaquetePredefinido p : paquetes.values()) {
			lista.addAll(p.listarservicios());
		}
		return lista;
	}
	
	/*	Funcion utilizada por cliente	*/
	

	
	// GETTERS Y SETTERS
	public HashMap<Integer, ServicioSimples> getServicios() {
		return servicios;
	}

	public void setServicios() {
		this.servicios = new HashMap<>();
	}

	public HashMap<Integer, PaquetePredefinido> getPaquetes() {
		return paquetes;
	}

	public void setPaquetes() {
		this.paquetes = new HashMap<>();
	}

	public int getCodigoUnico() {
		return codigoUnico;
	}

	public void setCodigoUnico() {
		this.codigoUnico = codigoRandom();
	}

	public double getCostoTotal() {
		if (paquetes.size() + servicios.size() >= 3) {
			return costoTotal * 0.90;
		} else if (paquetes.size() + servicios.size() == 2) {
			return costoTotal * 0.95;
		} else {
			return costoTotal;
		}
	}

	public void setCostoTotal(double costoServOPaquete) {
		this.costoTotal += costoServOPaquete;
	}

	public Date getFechaDePago() {
		return fechaDePago;
	}

	public void setFechaDePago(Date fechaDePago) {
		this.fechaDePago = fechaDePago;
	}
	
	// GETTERS Y SETTERS

	/* IMPLEMENTACION DE LA INTERFAZ */
	@Override
	public boolean existeServicio(int codigo) {
		if (!servicios.containsKey(codigo)) {
			return false;
		}
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean existePaquete(int codigo) {
		// TODO Auto-generated method stub
		if (!paquetes.containsKey(codigo)) {
			return false;
		}
		return true;
	}

	/* IMPLEMENTACION DE LA INTERFAZ */
	
	

	@Override
	public String toString() {
		return "PaquetePersonal [servicios=" + servicios + ", paquetes=" + paquetes + ", codigoUnico=" + codigoUnico
				+ ", costoTotal=" + costoTotal + ", fechaDePago=" + fechaDePago + "]";
	}

	

	
}
