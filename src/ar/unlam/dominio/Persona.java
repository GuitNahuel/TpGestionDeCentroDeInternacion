package ar.unlam.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Persona {
	protected Integer codigo;
	protected String  nombre;
	protected String  apellido;
	protected Integer edad;
	protected Integer dni;
	protected Integer telefono;
	private LocalDate fechaDeNacimiento;
	
	public Persona(Integer codigo , String nombre, String apellido, Integer edad, Integer dni , Integer telefono) {
		this.codigo   = codigo;
		this.nombre   = nombre;
		this.apellido = apellido;
		this.edad 	  = edad;
		this.dni      = dni;
		this.telefono = telefono;
		this.fechaDeNacimiento = calcularFechaDeNacimiento(edad);
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public Integer getDni() {
		return dni;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public Integer getTelefono() {
		return telefono;
	}
	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}
	public String obtenerFechaDeNacimiento() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaFormateada = fechaDeNacimiento.format(formatter);

		return fechaFormateada;
	}
	
	public LocalDate calcularFechaDeNacimiento(Integer edad) {
		LocalDate fechaActual  = LocalDate.now();
		return fechaActual.minusYears(edad);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codigo, dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(dni, other.dni);
	}

}
