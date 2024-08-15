

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.management.RuntimeErrorException;

public class Alquiler extends ServicioSimples{

	private String pais;
	private String ciudad;
	private String fechaDevolucion;
	private double garantia;
		
	public Alquiler(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad,
			String fechaDevolucion, double garantia) {
		super(costoBase, fechaInicio, cantidad);
		setPais(pais);
		setCiudad(ciudad);
		setFechaDevolucion(fechaDevolucion);
		setGarantia(garantia);
		setCostoTotal();
	}
	
	
	//GETTERS Y SETTERS
	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		try {
			verificarSiContieneNumeros(pais);
			this.pais = pais;
		}catch(Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		try {
			verificarSiContieneNumeros(ciudad);
			this.ciudad = ciudad;
		}catch(Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	}

	public String getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(String fechaDevolucion) {
		fechaDevolucionAntesDeFechaInicio(this.fechaInicio, fechaDevolucion);
		this.fechaDevolucion = fechaDevolucion;
	}

	public double getGarantia() {
		return garantia;
	}

	public void setGarantia(double garantia) {
		if(garantia <= 0) {
			throw new Error("la garantia debe ser mayor a 0");
		}
		this.garantia = garantia;
	}
	
	
	/*	FUNCIONES AUXILIARES*/
	public void fechaDevolucionAntesDeFechaInicio(String fechaInicioSinParsear, String fechaSalidaSinParsear) {
		Date fechaInicio = convertirStringADate(fechaInicioSinParsear);
		Date fechaSalida = convertirStringADate(fechaSalidaSinParsear);
		
		if(fechaInicio.after(fechaSalida)) {
			throw new Error("la fecha de inicio es despues de la fecha de salida");
		}
	}
	
	
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
	
	
	/*	FUNCIONES AUXILIARES*/
	
	
	
	public void setCostoTotal() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String fechaParseadaInicio = fechaInicio.replace("-", "/");
		String fechaParseadaDevolucion = fechaDevolucion.replace("-", "/");
		long dias = ChronoUnit.DAYS.between(LocalDate.parse(fechaParseadaInicio, formatter),
				LocalDate.parse(fechaParseadaDevolucion, formatter));
		if (cantidad <= 4) {
			this.costoTotal = this.costoBase * dias + this.garantia;
		} else if (cantidad >= 5 && cantidad <= 8) {
			this.costoTotal = (this.costoBase * dias) * 2 + this.garantia;
		} else if (cantidad > 8) {
			this.costoTotal = (this.costoBase * dias) * 3 + this.garantia;
		}
	}

	@Override
	public String toString() {
		return "Alquiler [cantidad= " + cantidad + ", fechaInicio= "+ fechaInicio +", costoBase=" + costoBase +", pais=" + pais + ", ciudad=" + ciudad + ", fechaDevolucion=" + fechaDevolucion + ", garantia="
				+ garantia + "]";
	}

}
