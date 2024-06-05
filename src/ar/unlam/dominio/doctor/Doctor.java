package ar.unlam.dominio.doctor;

import java.util.ArrayList;
import java.util.List;

import ar.unlam.dominio.Persona;
import ar.unlam.dominio.paciente.Paciente;

public  class Doctor extends Persona {
	
	private String matricula;
	private List<Paciente>pacientes;

	public Doctor(Integer codigo , String nombre, String apellido, Integer edad, Integer dni, Integer telefono,String matricula) {
		super(codigo , nombre, apellido, edad, dni, telefono);
		this.matricula = matricula;
		this.pacientes = new ArrayList<>();
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public void asignarPaciente(Paciente paciente) {
		this.pacientes.add(paciente);	
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	@Override
	public String toString() {
		return "Doctor [matricula=" + matricula + ", pacientes";
	}
	
	

}
