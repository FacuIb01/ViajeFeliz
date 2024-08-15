

import javax.management.RuntimeErrorException;

public class Excursion extends ServicioSimples {


	private String lugarSalida;
	private boolean esDiaCompleto;

	public Excursion(double costoBase, String fechaInicio, int cantidad, String lugarSalida, boolean esDiaCompleto) {
		super(costoBase, fechaInicio, cantidad);
		setLugarSalida(lugarSalida);
		setEsDiaCompleto(esDiaCompleto);
		setCostoTotal();
	}

	//GETTERS Y SETTERS
	public String getLugarSalida() {
		return lugarSalida;
	}

	public void setLugarSalida(String lugarSalida) {
		try {
			verificarSiContieneNumeros(lugarSalida);
			this.lugarSalida = lugarSalida;
		}catch(Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	}
	
	public boolean getEsDiaCompleto() {
		return esDiaCompleto;
	}

	public void setEsDiaCompleto(boolean esDiaCompleto) {
		this.esDiaCompleto = esDiaCompleto;
	}

	public void setCostoTotal() {
		if(this.cantidad >= 3) {
			if(getEsDiaCompleto()) {
				this.costoTotal = getCostoBase() * 2 * this.cantidad *0.90; 
			}else {
				this.costoTotal = getCostoBase()* this.cantidad * 0.90;
			}
		}else if(this.cantidad == 2) {
			if(getEsDiaCompleto()) {
				this.costoTotal = getCostoBase() * 2 * this.cantidad * 0.95; 
			}else {
				this.costoTotal = getCostoBase()* this.cantidad * 0.95;
			}
		}else {
			if(getEsDiaCompleto()) {
				this.costoTotal = getCostoBase() * 2 * this.cantidad ; 
			}else {
				this.costoTotal = getCostoBase() * this.cantidad;
			}
		}
	}
	
	
	@Override
	public String toString() {
		return "Excursion [cantidad= " + cantidad + ", fechaInicio= "+ fechaInicio +", costoBase=" + costoBase +", lugarSalida=" + lugarSalida + ", esDiaCompleto=" + esDiaCompleto + "]";
	}
	


}
