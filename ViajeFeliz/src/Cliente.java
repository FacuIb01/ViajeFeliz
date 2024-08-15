

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.management.RuntimeErrorException;

public class Cliente {
	private String dni;
	private String nombre;
	private String direccion;
	private PaquetePersonal paquete;
	private LinkedHashMap<Integer, PaquetePersonal> historialPaquetes;

	public Cliente(String dni, String nombre, String direccion) {
		setDni(dni);
		setNombre(nombre);
		setDireccion(direccion);
		setHistorialPaquetes();
	}

	void crearPaquete() {
		PaquetePersonal paq = new PaquetePersonal();
		setPaquete(paq);
	}

	// SOBRECARGA AñadirAPaquetePersonal
	public boolean añadirAPaquetePersonal(int codigo, ServicioSimples serv) {
		try {
			paquete.añadir(codigo, serv);
			return true;
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}

	}

	public boolean añadirAPaquetePersonal(int codigo, PaquetePredefinido paquet) {
		try {
			paquete.añadir(codigo, paquet);
			return true;
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}

	}
	// SOBRECARGA AñadirAPaquetePersonal

	// Quitar de paquete Personal
	public PaquetePredefinido quitarDePaquete(int codigo) {
		try {

			PaquetePredefinido paq = paquete.quitar(codigo);

			return paq;
		} catch (Error e) {
			throw new RuntimeErrorException(e, e.getMessage());
		}
	}

	// Quitar de paquete Personal

	// Obtener Costo Paquete Personal

	public double obtenerCostoPaquete(int codigoPaq) {
		if (historialPaquetes.containsKey(codigoPaq)) {
			double costoTotal = historialPaquetes.get(codigoPaq).getCostoTotal();
			return costoTotal;
		} else {

			if (codigoPaq == paquete.getCodigoUnico()) {
				return paquete.getCostoTotal();
			} else {
				throw new Error("No existe paquete con ese codigo");

			}

		}
	}
	// Obtener Costo Paquete Personal

	// SOBRECARGA pagarContratacion

	public double pagarContratacion(Date fechaPago) {
		if (getPaquete() != null) {
			if (paquete.añadirfechaDePago(fechaPago)) {
				double costo = paquete.getCostoTotal();
				historialPaquetes.put(paquete.getCodigoUnico(), paquete);
				paquete = null;
				return costo;
			}
		} else {
			System.out.println("No existe una contratacion abierta");
		}
		return 0;
	}

	// Devuelve los codigos unicos de cada paquete
	LinkedList<Integer> historialDeContrataciones() {
		LinkedList<Integer >listaCodigos = new LinkedList<>();
			
		for (Integer codigos : getHistorialPaquetes().keySet()) {
			listaCodigos.addLast(codigos);
		}
		return listaCodigos;
	}

	// SOBRECARGA
	public String contratacionesSinIniciar(Date fecha) {
		String s = "";
		for (PaquetePersonal p : historialPaquetes.values()) {
			if (p.averiguarFechaInicio().after(fecha)) {
				s += nombre + " | " + convertirDateAString(p.averiguarFechaInicio()) + " | " + p.listarservicios() + "\n";
			}
		}
		return s;
	}

	public String contratacionesQueInicianEnFecha(Date fecha) {
		String entrada = "";
		for (PaquetePersonal p : historialPaquetes.values()) {
			
			if (p.averiguarFechaInicio().equals(fecha)) {
				entrada += p.getCodigoUnico() + " - (" + dni + " " + nombre + ")";
			}
		}
		return entrada;
	}
	
	public static String convertirDateAString(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fecha);
    }

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", nombre=" + nombre + ", direccion=" + direccion + ", paquete=" + paquete
				+ ", historialPaquetes=" + historialPaquetes + "]";
	}
	
	public void verificarSiContieneNumeros(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                throw new Error("el nombre contiene numeros.");
            }
        }
    }
	private boolean verificarSiContieneLetras(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new Error("El dni no puede contener letras");
            }
        }
        return true;
    }

	// GETTERS Y SETTERS
	public PaquetePersonal getPaquete() {
		return paquete;
	}

	public void setPaquete(PaquetePersonal paq) {
		this.paquete = paq;
	}

	public String getDni() {
		return dni;
	}

    public void setDni(String dni) {
        if(dni.length() == 8) {
            verificarSiContieneLetras(dni);
            this.dni = dni;
        }else {
            throw new Error("El dni debe tener 8 caracteres");
        }
    }

	public void setHistorialPaquetes() {
		this.historialPaquetes = new LinkedHashMap<>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
        verificarSiContieneNumeros(nombre);
        this.nombre = nombre;
    }

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public HashMap<Integer, PaquetePersonal> getHistorialPaquetes() {
		return historialPaquetes;
	}
	// GETTERS Y SETTERS
}
