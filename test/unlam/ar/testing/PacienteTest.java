package unlam.ar.testing;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import ar.unlam.dominio.CentroDeInternacion;
import ar.unlam.dominio.Habitacion;
import ar.unlam.dominio.HabitacionNoEncontradaExeption;
import ar.unlam.dominio.Persona;
import ar.unlam.dominio.Enum.EstadoDeSalud;
import ar.unlam.dominio.Enum.Pronostico;
import ar.unlam.dominio.Enum.TipoDeCobertura;
import ar.unlam.dominio.doctor.Doctor;
import ar.unlam.dominio.doctor.DoctorNoEncontradoException;
import ar.unlam.dominio.paciente.InformacionMedica;
import ar.unlam.dominio.paciente.InternacionNoEncontradaExeption;
import ar.unlam.dominio.paciente.Paciente;
import ar.unlam.dominio.paciente.PacienteNoEncontradoException;

public class PacienteTest {

	/*
	 * Este test presenta los filtros necesarios para el correcto ingreso de un
	 * paciente al centro de internacion, asi como la validacion de algunas
	 * excepciones.
	 * 
	 * Importantes aclaraciones, se concidera la misma persona
	 * a los pacientes que tengan el mismo codigo y el mismo DNI si uno de los dos
	 * datos no coincide con un paciente existente eso quiere decir que el paciente
	 * no existe o hubo un error al ingresar un DNI existente en un nuevo paciente
	 * en cuyo caso no se iniciara una internacion.
	 * 
	 * A continuacion los test que presenta esta clase:
	 * 
	 * iniciarUnaInternacion
	 * dadoQueYaExisteUnPacienteQueNoSePuedaAgregarOtroPacienteConElMismoDni
	 * dadoQueYaExisteUnPacienteQueNoSePuedaAgregarPeroSiIniciarUnaNuevaInternacion
	 * dadoQueExisteUnPacienteQueSeLeAsigneUnaHabitacionAlCrearLaInternacion
	 * dadoQueExisteUnPacienteQueSeLeAsigneUnDoctorAlCrearLaInternacion
	 * dadoQueYaExisteUnPacienteQueSePuedaObtenerSuInformacionDeContactoDeEmergencia
	 * dadoQueExisteUnPacienteQueSePuedaEncontrarPorSuDni
	 * dadoQueNoExisteUnPacienteQueNoSePuedaEncontrarPorSuDni
	 * dadoQueExisteUnPacienteObtenerSuInformacionMedica
	 * dadoQueExisteUnPacienteObtenerSuFechaDeNacimiento
	 */

	private CentroDeInternacion internacion;
	private String nombre = "Hospital 100% legal";

	@Before
	public void init() {
		this.internacion = new CentroDeInternacion(nombre);
	}

	@Test
	public void dadoQueYaExisteUnPacienteQueSePuedaIniciarUnaInternacion()
			throws InternacionNoEncontradaExeption, PacienteNoEncontradoException, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		// Ejecucion
		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		int internacionesEsperadas = 1;
		int internacionesObtenidas = internacion.obtenerListaDeInternaciones().size();

		// Verificacion
		assertEquals(internacionesEsperadas, internacionesObtenidas);
	}

	@Test(expected = PacienteNoEncontradoException.class)
	public void dadoQueYaExisteUnPacienteQueNoSePuedaAgregarOtroConElMismoDni()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Habitacion h2 = new Habitacion(2, "P2_H101");

		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Paciente pacienteDos = this.crearPaciente(2, "Fernando", "Guitierrez", 20, 44444, 11293,
				TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		// Ejecucion
		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);

		internacion.ingresarPaciente(pacienteDos, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		Boolean sePudo = internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO,
				Pronostico.NO_PUEDE_RESOLVER_BUGS, 2);

		// Verificacion
		assertFalse(sePudo);
	}

	@Test
	public void dadoQueYaExisteUnPacienteQueNoSePuedaAgregarOtroConElMismoDniPeroSiInicarLaInternacion()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Habitacion h2 = new Habitacion(2, "P2_H101");

		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		// Ejecucion
		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		Boolean sePudo = internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO,
				Pronostico.NO_PUEDE_RESOLVER_BUGS, 2);

		// Verificacion
		assertTrue(sePudo);
	}

	@Test
	public void dadoQueExisteUnPacienteQueSEPuedaAsiganarleUnaHabitacionAlCrearLaInternacion()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		// Ejecucion
		String habitacionEsperada = "P1_H101";
		Habitacion habitacionObtenida = internacion.obtenerHabitacionDeUnPaciente(44444);

		// Verificacion
		assertEquals(habitacionEsperada, habitacionObtenida.getCodigo());
	}

	@Test
	public void dadoQueExisteUnPacienteQueSePuedaAsiganarleUnDoctor()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		// Ejecucion
		Doctor docotrEsperado = doctor;
		Doctor doctorObtenido = internacion.verDoctorDelPaciente(44444);

		// Verificacion
		assertEquals(docotrEsperado.getMatricula(), doctorObtenido.getMatricula());
	}

	@Test
	public void dadoQueYaExisteUnPacienteObtenerSuInformacionDeContactoDeEmergencia()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");

		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Persona persona = new Persona(1, "Rick", "Morty", 19, 15555, 115727);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		paciente.setContactoEmergencia(persona);

		// Ejecucion
		Persona personaEsperada = persona;
		Persona personaObtenida = internacion.buscarPaciente(44444).getContactoEmergencia();

		// Verificacion
		assertEquals(personaEsperada.getDni(), personaObtenida.getDni());
	}

	@Test
	public void dadoQueExisteUnPacienteObtenerSuInformacionMedica() {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.iniciarInternacion(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		// Ejecucion
		InformacionMedica informacionEsperada = new InformacionMedica("b negativo ", "nuez", "", "");

		// Verificacion
		assertEquals(informacionEsperada.toString(), paciente.getInformacionMedica().toString());
	}

	@Test
	public void dadoQueExisteUnPacienteObtenerSuFechaDeNacimiento() {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.iniciarInternacion(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		// Ejecucion
		LocalDate fechaDeNacimiento = LocalDate.now().minusYears(paciente.getEdad());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaEsperada = fechaDeNacimiento.format(formatter);

		// Verificacion
		assertEquals(fechaEsperada, paciente.obtenerFechaDeNacimiento());
	}

	@Test
	public void dadoQueExisteUnPacienteQueSEPuedaBuscarPorDni()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		// Ejecucion
		Paciente pacienteEsperado = paciente;
		Paciente pacienteObtenido = internacion.buscarPaciente(44444);

		// Verificacion
		assertEquals(pacienteEsperado, pacienteObtenido);
	}

	@Test(expected = PacienteNoEncontradoException.class)
	public void dadoQueNoExisteUnPacienteQueNoSEPuedaEncontrarPorDni()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		// Ejecucion
		Paciente pacienteEsperado = paciente;
		Paciente pacienteObtenido = internacion.buscarPaciente(444144);

		// Verificacion
		assertNotEquals(pacienteEsperado, pacienteObtenido);
	}

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
