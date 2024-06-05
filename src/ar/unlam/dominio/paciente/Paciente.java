package ar.unlam.dominio.paciente;

import ar.unlam.dominio.Persona;
import ar.unlam.dominio.Enum.TipoDeCobertura;
import ar.unlam.dominio.doctor.Doctor;

public class Paciente extends Persona  {

	private TipoDeCobertura plan;
	private Boolean    sigueInternado;
	private Persona  contactoEmergencia;
	private InformacionMedica informacionMedica;
	
	public Paciente(Integer codigo , String nombre, String apellido, Integer edad, Integer dni, Integer telefono , TipoDeCobertura plan , InformacionMedica informacionMedica) {
		super(codigo , nombre, apellido, edad, dni , telefono);
		this.plan = plan;
		this.informacionMedica  = informacionMedica;
		this.contactoEmergencia = null;
		this.sigueInternado     = true;
	}

	public TipoDeCobertura getPlan() {
		return plan;
	}

	public void setPlan(TipoDeCobertura plan) {
		this.plan = plan;
	} 
	
	public Boolean isSigueInternado() {
		return sigueInternado;
	}

	public Persona getContactoEmergencia() {
		return contactoEmergencia;
	}
	
	public InformacionMedica getInformacionMedica() {
		return informacionMedica;
	}
	
	public void setContactoEmergencia(Persona contactoEmergencia) {
		this.contactoEmergencia = contactoEmergencia;
	}
	
	private String informacionDeContactoDeEmergencia() {
		if(contactoEmergencia == null) return " Sin contacto de emergencia";

		String nombre 	= this.contactoEmergencia.getNombre();
		String apellido = this.contactoEmergencia.getApellido();
		Integer tel 	= this.contactoEmergencia.getTelefono();
		return "Nombre completo: " + nombre + " " + apellido + "\n" +
			   "Contacto de emergencia: " + tel;
	}
	
	public void alta() {
		this.sigueInternado = false;
	}

	@Override
	public String toString() {
		return "FichaMedica: " + "\n" + " Paciente:  " + super.getNombre() + " " + super.apellido + "\n" +
			   " Fecha Nacimiento: " 				   + super.obtenerFechaDeNacimiento() + "\n" + 
			   " Numero DNI: "  					   + super.getDni()               + "\n" + 
			   " Plan: " + plan + "\n" +
			   " Contacto de emergencia: " + "\n"      + informacionDeContactoDeEmergencia() + "\n" +
			   this.informacionMedica.toString() + "\n"; 
	}



	

	

	
	
	
}
