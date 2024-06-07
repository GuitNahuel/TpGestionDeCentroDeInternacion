package ar.unlam.dominio;

import java.time.LocalDate;

import ar.unlam.dominio.paciente.Paciente;

public class Recibo {
	
	private String nombre;
	private Double pagado;
	private Double precio;
	private Double precioFinal;
	private LocalDate fechaDeIngreso;
	private LocalDate fechaDeSalida;
	private Paciente paciente;

	public Recibo(String nombre , Double pagado, Double precio, Double precioFinal, LocalDate fechaDeIngreso, LocalDate fechaDeSalida,Paciente paciente) {
		this.nombre = nombre;
		this.pagado = pagado;
		this.precio = precio;
		this.precioFinal    = precioFinal;
		this.fechaDeIngreso = fechaDeIngreso;
		this.fechaDeSalida  = fechaDeSalida;
		this.paciente = paciente;
	}

	public Double getPagado() {
		return pagado;
	}

	public Double getPrecio() {
		return precio;
	}

	public Double getPrecioFinal() {
		return precioFinal;
	}

	public LocalDate getFechaDeIngreso() {
		return fechaDeIngreso;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return "Hospital " + this.getNombre() + "\n" +
			   "Recibo de alta hospitalaria \n "  +
			   "\n" +
			   "Paciente: " + paciente.getNombre() +"\n" + 
			   "Dni: "      + paciente.getDni()    +"\n" +
			   "fecha de ingreso: " + this.fechaDeIngreso +"\n" +
			   "fecha alta: " + this.fechaDeSalida 	      +"\n" +
			   "\n" +
			   "Tipo de plan: "  + paciente.getPlan() + "\n" +
			   "Precio sin Descuento: " + precio + "\n" +
			   "Precio mas el cubierto: " + precioFinal   + "\n" + 
			   "Total pagado: "  + pagado         + "\n";
	}
}
