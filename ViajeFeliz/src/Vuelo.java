

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.RuntimeErrorException;

public class Vuelo extends ServicioSimples {
	private String pais;
	private String ciudad;
	private String fechaLlegada;
	private double tasa;

	public Vuelo(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, String fechaLlegada,
			double tasa) {
		super(costoBase, fechaInicio, cantidad);
		setPais(pais);
		setCiudad(ciudad);
		setFechaLlegada(fechaLlegada);
		setTasa(tasa);
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

	public String getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(String fechaLlegada) {
		fechaInicioDespuesFechaLlegada(this.fechaInicio, fechaLlegada);
		this.fechaLlegada = fechaLlegada;
	}

	public double getTasa() {
		return tasa;
	}

	public void setTasa(double tasa) {
		if(tasa <= 0) {
			throw new Error("la tasa debe ser mayor a 0");
		}
		this.tasa = tasa;
	}
	
	/*	FUNCIONES AUXILIARES*/
	
	public void fechaInicioDespuesFechaLlegada(String fechaInicioSinParsear, String fechaSalidaSinParsear) {
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

	@Override
	public String toString() {
		return "Vuelo [cantidad= " + cantidad + ", fechaInicio= "+ fechaInicio +", costoBase=" + costoBase +", pais=" + pais + ", ciudad=" + ciudad + ", fechaLlegada=" + fechaLlegada + ", tasa=" + tasa + "]";
	}

	
	public void setCostoTotal() {
		double subTotal = (costoBase * cantidad);
		double tasaAplicada = subTotal*tasa;
		this.costoTotal =  subTotal + tasaAplicada;
		
	}
	
}
