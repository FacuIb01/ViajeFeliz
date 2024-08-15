

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Alojamiento extends ServicioSimples {

	private String pais;
	private String ciudad;
	private String fechaSalida;
	private String hotel;
	private double costoTraslado;

	public Alojamiento(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad,
			String fechaSalida, String hotel, double costoTraslado) {
		super(costoBase, fechaInicio, cantidad);
		setPais(pais);
		setCiudad(ciudad);
		setFechaSalida(fechaSalida);
		setHotel(hotel);
		setCostoTraslado(costoTraslado);
		setCostoTotal();
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public double getCostoTraslado() {
		return costoTraslado;
	}

	public void setCostoTraslado(double costoTraslado) {
		this.costoTraslado = costoTraslado;
	}

	
	public void setCostoTotal() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String fechaParseadaInicio = fechaInicio.replace("-", "/");
		String fechaParseadaSalida = fechaSalida.replace("-", "/");
		long dias = ChronoUnit.DAYS.between(LocalDate.parse(fechaParseadaInicio, formatter),LocalDate.parse(fechaParseadaSalida, formatter));
		int baseDoble = (cantidad + 1) / 2;
		if (cantidad != 5) {
			this.costoTotal = dias * baseDoble * costoBase + costoTraslado;
		} else {
			this.costoTotal = dias * 2.5 * costoBase + costoTraslado;
		}
	}

	@Override
	public String toString() {
		return "Alojamiento [cantidad= " + cantidad + ", fechaInicio= " + fechaInicio + ", costoBase=" + costoBase
				+ ", pais=" + pais + ", ciudad=" + ciudad + ", fechaSalida=" + fechaSalida + ", hotel=" + hotel
				+ ", costoTraslado=" + costoTraslado + "]";
	}

}
