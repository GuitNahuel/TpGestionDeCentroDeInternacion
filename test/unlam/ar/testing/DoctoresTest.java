package unlam.ar.testing;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.unlam.dominio.Habitacion;
import ar.unlam.dominio.Enum.EstadoDeSalud;
import ar.unlam.dominio.Enum.Pronostico;
import ar.unlam.dominio.Enum.TipoDeCobertura;
import ar.unlam.dominio.doctor.Doctor;
import ar.unlam.dominio.doctor.DoctorNoEncontradoException;
import ar.unlam.dominio.paciente.InformacionMedica;
import ar.unlam.dominio.paciente.InternacionNoEncontradaExeption;
import ar.unlam.dominio.paciente.Paciente;
import ar.unlam.dominio.paciente.PacienteNoEncontradoException;
import ar.unlam.dominio.CentroDeInternacion;

public class DoctoresTest {
	// Este test tiene todos los tests necesarios para poder tener un seguimiento
	// correcto y eficiente de los pacientes ingresados al sistema.

	private CentroDeInternacion internacion;
	private String nombre = "Hospital 100% legal";

	@Before
	public void init() {
		this.internacion = new CentroDeInternacion(nombre);
	}

	// Esto esta hecho por si se pone mal el DNI que retorne false si el DNI ya
	// existe
	@Test
	public void dadoQueUnDoctorFueAgregadoQueNoSePuedaAgregarOtroConElMismoDni() { // ?¿ mimsa matricula o mismo dni
		// Preparacion
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 1129, "MHJ208");
		Doctor doctorDos = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 1129, "MHJ2018");

		// Ejecucion
		internacion.agregarDoctor(doctor);
		Boolean sePudo = internacion.agregarDoctor(doctorDos);

		// Verificacion
		assertFalse(sePudo);
	}

//	
	@Test
	public void dadoQueExisteUnDoctorConPacientesAsignadosMostrarlos()
			throws DoctorNoEncontradoException, PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion

		Habitacion h1 = new Habitacion(1, "P1_H101");
		Habitacion h2 = new Habitacion(2, "P1_H2101");

		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);

		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");
		Paciente paciente = this.crearPaciente(2, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Paciente pacienteDos = this.crearPaciente(2, "Ignacio", "Guitierrez", 20, 4444224, 11293,
				TipoDeCobertura.PLAN1500);

		internacion.agregarDoctor(doctor);
		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);
		internacion.ingresarPaciente(pacienteDos, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 2);

		// Ejecucion
		int pacientesEsperados = 2;
		List<Paciente> pacientesObtenidos = internacion.obtenerPacientesDeUnDoctor("MHJ208");

		// Verificacion
		assertEquals(pacientesEsperados, pacientesObtenidos.size());
	}

	@Test(expected = DoctorNoEncontradoException.class)
	public void dadoQueNoExisteUnDoctorMostrarSusPacientesInexistentes() throws DoctorNoEncontradoException {
		// Preparacion
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");
		Paciente paciente = this.crearPaciente(2, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Paciente pacienteDos = this.crearPaciente(2, "Ignacio", "Guitierrez", 20, 4444224, 11293,
				TipoDeCobertura.PLAN1500);

		internacion.agregarDoctor(doctor);

		// Ejecucion
		int pacientesEsperados = 2;
		List<Paciente> pacientesObtenidos = internacion.obtenerPacientesDeUnDoctor("noExiste");

		// Verificacion
		assertEquals(pacientesEsperados, pacientesObtenidos.size());
	}

	@Test
	public void queSePuedaBuscarUnDoctorPorMatricula() throws DoctorNoEncontradoException { // Seria mejor por DNI o
																							// codigo
		// Preparacion
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");
		Doctor doctorDos = this.crearDoctor(1, "Raul", "Gutierrez", 50, 321823542, 11293, "MHJ2081");

		internacion.agregarDoctor(doctor);
		internacion.agregarDoctor(doctorDos);

		// Ejecucion
		Doctor doctorEsperado = doctor;
		Doctor doctorObtenido = internacion.buscarDoctor("MHJ208");

		// Verificacion
		assertEquals(doctorEsperado, doctorObtenido);
	}

	@Test(expected = DoctorNoEncontradoException.class)
	public void queNoSePuedaBuscarUnDoctorPorMatriculaInexistente() throws DoctorNoEncontradoException { // seria mejor
																											// por dni o
																											// codigo
		// Preparacion
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHfdsJ208");
		Doctor doctorDos = this.crearDoctor(1, "Raul", "Gutierrez", 50, 321823542, 11293, "MHJ2081");

		internacion.agregarDoctor(doctor);
		internacion.agregarDoctor(doctorDos);

		// Ejecucion
		Doctor doctorEsperado = doctor;
		Doctor doctorObtenido = internacion.buscarDoctor("MHJ208");

		// Verificacion
		assertEquals(doctorEsperado, doctorObtenido);
	}

	// Filtros de ordenamiento

	private Paciente crearPaciente(Integer codigo, String nombre, String apellido, Integer edad, Integer dni,
			Integer telefono, TipoDeCobertura plan) {
		InformacionMedica informacionMedica = this.crearInformacionMedica();
		return new Paciente(codigo, nombre, apellido, edad, dni, telefono, plan, informacionMedica);
	}

	private InformacionMedica crearInformacionMedica() {
		return new InformacionMedica("b negativo ", "nuez", "", "");
	}

	private Doctor crearDoctor(Integer codigo, String nombre, String apellido, Integer edad, Integer dni,
			Integer telefono, String matricula) {
		return new Doctor(codigo, nombre, apellido, edad, dni, telefono, matricula);
	}

}
