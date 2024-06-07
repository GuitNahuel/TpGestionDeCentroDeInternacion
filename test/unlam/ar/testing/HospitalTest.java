package unlam.ar.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.unlam.dominio.CentroDeInternacion;
import ar.unlam.dominio.Habitacion;
import ar.unlam.dominio.Enum.EstadoDeSalud;
import ar.unlam.dominio.Enum.Pronostico;
import ar.unlam.dominio.Enum.TipoDeCobertura;
import ar.unlam.dominio.doctor.Doctor;
import ar.unlam.dominio.paciente.InformacionMedica;
import ar.unlam.dominio.paciente.InternacionNoEncontradaExeption;
import ar.unlam.dominio.paciente.Paciente;
import ar.unlam.dominio.paciente.PacienteNoEncontradoException;

public class HospitalTest {

	/*
	 * Este test valida los principales agregados funcionen correctamenta para
	 * permitir el correcto funcionamiento del programa. Tambien cuentan con sus
	 * respectivos test que serian importantes a la hora de una consulta, generarla
	 * sobre el estado del hospital a si como los test para validar los distintos
	 * filtros para el ingreso al hospital.
	 * 
	 * A continuacion los test que presenta esta clase:
	 * 
	 * queSePuedaAgregarUnaHabitacion queSePuedaAgregarUnDoctor
	 * queSePuedaIngresarUnPaciente cabe aclarar que al ingresar un paciente se
	 * inicia el proceso de internacion queNoSePuedaIngresarUnPaciente: si no
	 * existen doctores y no se encuentren habitaciones disponibles
	 * dadoQueExistePacientesObtenerLaListaDeEllos
	 * dadoQueExistenPacientesObtenerLaListaDeInternaciones en el hospital
	 * dadoQueExistenDoctoresObtenerUnaListaDeEllos lo mismo para las habitaciones
	 * dadoQueExistenHabitacionesDisponiblesObtenerUnaListaDeEllas
	 * 
	 */

	private CentroDeInternacion internacion;
	private String nombre = "Hospital 100% legal";

	@Before
	public void init() {
		this.internacion = new CentroDeInternacion(nombre);
	}

	@Test
	public void queSePuedaAgregarUnaHabitacion() {
		// Preparacion
		Habitacion habitacion = new Habitacion(1, "P1_H101");

		// Ejecucion
		Boolean sePudo = internacion.agregarHabitacion(habitacion);

		// Verificacion
		assertTrue(sePudo);
	}

	@Test
	public void queSePuedaAgregarUnDoctor() {
		// Preparacion
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		// Ejecucion
		Boolean sePudo = internacion.agregarDoctor(doctor);

		// Verificacion
		assertTrue(sePudo);
	}

	@Test
	public void queSePuedaIngresarUnPaciente() throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);

		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		Habitacion habitacion = this.creaHabitaciones(1, "P1_H101");

		// Ejecucion
		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(habitacion);

		Boolean sePudo = internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO,
				Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		// Verificacion
		assertTrue(sePudo);
	}

	@Test
	public void queNoSePuedaIngresarUnPacienteConUnDoctorinexistente()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion habitacion = this.creaHabitaciones(1, "P1_H101");
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);

		internacion.agregarHabitacion(habitacion);

		// Ejecucion

		Boolean sePudo = internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO,
				Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		// Verificacion
		assertFalse(sePudo);
	}

	@Test
	public void dadoQueNoHayHabitacionesQueNoPuedaIngresaUnPaciente()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Paciente pacienteRebotado = this.crearPaciente(4, "Ignacio", "Guitierrez", 20, 444434, 1131293,
				TipoDeCobertura.PLAN1500);

		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);

		// Ejecucion
		Boolean noSePudo = internacion.ingresarPaciente(pacienteRebotado, doctor, EstadoDeSalud.CODEANDO,
				Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		// Verificacion
		assertFalse(noSePudo);
	}

	@Test
	public void dadoQueExistenPacientesObtenerUnaListaDeEllos()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion habitacion = this.creaHabitaciones(1, "P1_H101");
		Habitacion habitacionDos = this.creaHabitaciones(2, "P2_H101");

		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);

		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarHabitacion(habitacion);
		internacion.agregarHabitacion(habitacionDos);

		internacion.agregarDoctor(doctor);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 2);

		// Ejecucion

		int pacientesEsperados = 1;
		int pacientesObtenidos = internacion.obtenerListaDePacientes().size();

		// Verificacion
		assertEquals(pacientesEsperados, pacientesObtenidos);
	}

	@Test
	public void dadoQueExistenPacientesObtenerUnaListaDeInternaciones()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion habitacion = this.creaHabitaciones(1, "P1_H101");
		Habitacion habitacionDos = this.creaHabitaciones(2, "P2_H101");
		Habitacion habitacionTres = this.creaHabitaciones(3, "P3_H101");

		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Paciente pacienteDos = this.crearPaciente(2, "Ignacio", "Guitierrez", 20, 4442144, 11293,
				TipoDeCobertura.PLAN1500);

		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarHabitacion(habitacion);
		internacion.agregarHabitacion(habitacionDos);
		internacion.agregarHabitacion(habitacionTres);

		internacion.agregarDoctor(doctor);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 2);

		internacion.ingresarPaciente(pacienteDos, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 3);

		// Ejecucion

		int pacientesEsperados = 3;
		int pacientesObtenidos = internacion.obtenerListaDeInternaciones().size();

		// Verificacion
		assertEquals(pacientesEsperados, pacientesObtenidos);
	}

	@Test
	public void dadoQueExistenDoctoresObtenerUnaListaDeEllos() {
		// Preparacion
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 1823514, 11293, "MfHJ208");
		Doctor doctorUno = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarDoctor(doctorUno);

		// Ejecucion
		int docotoresEsperados = 2;
		int doctoresObtenidos = internacion.obtenerListaDeDoctores().size();

		// Verificacion
		assertEquals(docotoresEsperados, doctoresObtenidos);
	}

	@Test
	public void dadoQueExistenHabitacionesObtenerUnaListaDeEllos() {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Habitacion h2 = new Habitacion(1, "P1_H102");
		Habitacion h3 = new Habitacion(1, "P2_H201");

		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);
		internacion.agregarHabitacion(h3);

		// Ejecucion
		int habitacionesEsperadas = 3;
		int habitacionesObtenidas = internacion.obtenerListaDeHabitaciones().size();

		// Verificacion
		assertEquals(habitacionesEsperadas, habitacionesObtenidas);
	}

	@Test
	public void dadoQueExistenHabitacionesObtenerUnaListaDeLasDesocupadas()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Habitacion h2 = new Habitacion(2, "P2_H102");
		Habitacion h3 = new Habitacion(3, "P3_H102");

		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);
		internacion.agregarHabitacion(h3);

		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Paciente pacienteDos = this.crearPaciente(2, "Ignacio", "Guitierrez", 20, 444424, 11293,
				TipoDeCobertura.PLAN1500);

		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);
		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 2);
		internacion.ingresarPaciente(pacienteDos, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 3);

		/*
		 * Devuelve uno ya que el primer paciente que se aloja en una habitacion pero al
		 * ingresar de nuevo se finaliza su anterior internacion lo cual devuelve la
		 * habitacion para ser ocupada al ocupar de nuevo una habitacion solo quedan 2
		 * la cual una es tomada por el pacienteDos
		 */

		// Ejecucion
		int habitacionesEsperadas = 1;
		int habitacionesObtenidas = internacion.obtenerListaDeHabitacionesDesocupadas().size();

		// Verificacion
		assertEquals(habitacionesEsperadas, habitacionesObtenidas);
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

	private Habitacion creaHabitaciones(Integer id, String codigo) {
		return new Habitacion(id, codigo);
	}

}
