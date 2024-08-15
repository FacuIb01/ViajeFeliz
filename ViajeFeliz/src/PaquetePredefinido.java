
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

public class PaquetePredefinido {
	private int codigo;
	private List<ServicioSimples> servicios;
	private double costoTotal;

	public PaquetePredefinido(int codigo) {
		super();
		setServicios();
		setCodigo(codigo);
	}
	
	
	public boolean añadirServicio(ServicioSimples servicio) {
		try {
			servicios.add(servicio);
			setCostoTotal(servicio.costoTotal);
			return true;
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	}
	
	public Date averiguarFechaInicio() {
		if (servicios != null) {
			Date fechaDeInicioPaquete = convertirStringADate(servicios.get(0).fechaInicio);
			for (ServicioSimples s : servicios) {
				if (convertirStringADate(s.fechaInicio).before(fechaDeInicioPaquete)) {
					fechaDeInicioPaquete = convertirStringADate(s.fechaInicio);
				}
			}
			return fechaDeInicioPaquete;
		}
		return null;
	}
	
	
	public List<String> listarservicios(){
		List<String> lista = new ArrayList<>();
		for (ServicioSimples s : servicios) {
			lista.add(s.getClass().getSimpleName());
		}
		return lista;
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
	
	//funcion para testear
	public void verServicios() {
		servicios.forEach(t -> System.out.println(t.toString()));
	}
	//funcion para testear
	

	// GETTERS Y SETTERS
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public List<ServicioSimples> getServicios() {
		return servicios;
	}

	public void setServicios() {
		this.servicios = new ArrayList<ServicioSimples>();
	}

	public double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(double costoTotalServ) {
		this.costoTotal += costoTotalServ;
	}
	
	// GETTERS Y SETTERS

	@Override
	public String toString() {
		return "PaquetePredefinido [codigo=" + codigo + ", servicios=" + servicios + ", costoTotal=" + costoTotal + "]";
	}
	
	

}
