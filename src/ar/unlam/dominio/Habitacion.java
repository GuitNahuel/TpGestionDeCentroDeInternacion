package ar.unlam.dominio;

import ar.unlam.dominio.paciente.Paciente;

public class Habitacion {
	private Integer  idHabitacion;
	private Paciente paciente;
	private String   codigo;
	private Boolean  isDisponible;
	
	public Habitacion(Integer idHabitacion , String codigo) {
		this.idHabitacion = idHabitacion;
		this.paciente 	  = null;
		this.isDisponible = true;
		this.codigo 	  = codigo;
	}

	public Integer getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(Integer idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getIsDisponible() {
		return isDisponible;
	}
	
	public void ocuparHabitacion() {
		this.isDisponible = false;
	}
	
	public void desalojarHabitacion() {
		this.isDisponible = true;
	}
	 

	
	
	
}
