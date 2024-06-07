package ar.unlam.dominio;

import ar.unlam.dominio.Enum.EstadoDeSalud;
import ar.unlam.dominio.Enum.Pronostico;
import ar.unlam.dominio.doctor.Doctor;
import ar.unlam.dominio.doctor.DoctorNoEncontradoException;
import ar.unlam.dominio.paciente.Internacion;
import ar.unlam.dominio.paciente.InternacionNoEncontradaExeption;
import ar.unlam.dominio.paciente.Paciente;
import ar.unlam.dominio.paciente.PacienteNoEncontradoException;

public interface ICentroDeInternacion {
	Boolean agregarHabitacion(Habitacion habitacion);
	
	Boolean agregarDoctor(Doctor doctor);
	
	Boolean ingresarPaciente(Paciente paciente, Doctor doctor, EstadoDeSalud estado, Pronostico pronostico,
			Integer codigo) throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption;
	
	Boolean iniciarInternacion(Paciente paciente, Doctor doctor, EstadoDeSalud estado, Pronostico pronostico,
			Integer codigo);

	Doctor buscarDoctor(String matricula) throws DoctorNoEncontradoException;

	Paciente buscarPaciente(Integer dni) throws PacienteNoEncontradoException;

	Internacion buscarInternacionDeUnPaciente(Integer codigoInternacion, Integer dni)
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption;

}
