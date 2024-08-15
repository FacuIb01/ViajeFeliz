
public abstract class ServicioSimples {
	double costoBase;
	String fechaInicio;
	int cantidad;
	double costoTotal;

	public ServicioSimples(double costoBase, String fechaInicio, int cantidad) {
		setCostoBase(costoBase);
		setFechaInicio(fechaInicio);
		setCantidad(cantidad);
	}
	
	
	public void setCostoBase(double costoBase) {
		if(costoBase <= 0) {
			throw new Error("El costo del servicio no puede ser menor o igual a 0");
		}else {
			this.costoBase = costoBase;
		}
	}
	
	public void setFechaInicio(String fechaInicio) {
		String[] fechaSeparada = fechaInicio.split("-");
		int año = Integer.parseInt(fechaSeparada[0]);
		int mes = Integer.parseInt(fechaSeparada[1]);
		int dia = Integer.parseInt(fechaSeparada[2]);
		
		if(año < 2024) {
			throw new Error("El año no puede ser menor a 2024");
		}
		
		if(mes < 1 || mes > 12) {
			throw new Error("El mes no puede ser menor a 1 o mayor a 12");
		}
		
		if(dia < 1 || dia > 31) {
			throw new Error("El dia no puede ser menor a 1 o mayor a 31");
		}
		
		if(mes ==  4|| mes ==  6||mes ==  9|| mes ==  11) {
			if(dia == 31) {
				throw new Error("El mes no tiene 31 dias");
			}
		}
		
		if(mes == 2) {
			if(dia > 29) {
				throw new Error("El mes tiene 29 dias");
			}
		}
		
		this.fechaInicio = fechaInicio;
	
	}
	
	public void setCantidad(int cantidad) {
		if(cantidad <= 0) {
			throw new Error("La cantidad debe ser superior a 0");
		}
		
		this.cantidad = cantidad;
	}
	

	public double getCostoTotal() {
		return this.costoTotal;
	}
	
	public double getCostoBase() {
		return costoBase;
	}
	
	
	public void verificarSiContieneNumeros(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                throw new Error("La cadena contiene números.");
            }
        }
    }
	//Funcion a reescribir en cada hijo de ServicioSimples
	public void setCostoTotal() {}
	
	@Override
	public String toString() {
		return "ServicioSimples [costoBase=" + costoBase + ", fechaInicio=" + fechaInicio + ", cantidad=" + cantidad
				+ "]";
	}
	
}
