package unlam.ar.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.unlam.dominio.CentroDeInternacion;
import ar.unlam.dominio.Habitacion;
import ar.unlam.dominio.Recibo;
import ar.unlam.dominio.Enum.EstadoDeSalud;
import ar.unlam.dominio.Enum.Pronostico;
import ar.unlam.dominio.Enum.TipoDeCobertura;
import ar.unlam.dominio.doctor.Doctor;
import ar.unlam.dominio.paciente.InformacionMedica;
import ar.unlam.dominio.paciente.Internacion;
import ar.unlam.dominio.paciente.InternacionNoEncontradaExeption;
import ar.unlam.dominio.paciente.Paciente;
import ar.unlam.dominio.paciente.PacienteNoEncontradoException;

public class DarDeAltaTest {

	/*
	 * Esta clase valida el correcto funcionamiento del programa a la hora de dar de
	 * alta un paciente y controlar que el metodo de cobro por cada plan que
	 * funcione correctamente
	 * 
	 * A continuacion los test que presentan son:
	 * 
	 * queSePuedaDarDeAltaAUnPaciente
	 * dadoQueUnPasienteFueDadoDeAltaLiberarSuHabitacion
	 * queSePuedaObtenerElPrecioAPagarPorElPlan5000DeUnPacienteDadoDeAlta
	 * queSePuedaObtenerElPrecioAPagarPorElPlan3000DeUnPacienteDadoDeAlta
	 * queSePuedaObtenerElPrecioAPagarPorElPlan1500DeUnPacienteDadoDeAlta
	 * queSePuedaObtenerElPrecioAPagarSinPlanDeUnPacienteDadoDeAlta
	 * 
	 */

	private CentroDeInternacion internacion;
	private String nombre = "Hospital 100% legal";

	@Before
	public void init() {
		this.internacion = new CentroDeInternacion(nombre);
	}

	@Test
	public void queSePuedaDarDeAltaUnPaciente() throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		// Ejecucion
		internacion.darDeAlta(44444);

		// Verificacion
		assertFalse(paciente.isSigueInternado());
	}

	@Test
	public void dadoQueUnPacienteFueDadoDeAltaLiberarSuHabitacion()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Habitacion h2 = new Habitacion(2, "P1_H101");

		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);

		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);

		internacion.darDeAlta(44444);

		// Ejecucion
		int habitacionesEsperadas = 2;
		int habitacionesObtenidas = internacion.obtenerListaDeHabitacionesDesocupadas().size();
		// Verificacion
		assertEquals(habitacionesEsperadas, habitacionesObtenidas);
	}

	// Plan PLAN1500 va a tener cubierto el 30% del valor de la internación.
	@Test
	public void queSePuedaObtenerElPrecioAPagarPorElPlan1500DeUnPacienteDadoDeAlta()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN1500);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);
		Internacion auxiliar = internacion.buscarInternacionDeUnPaciente(1, 44444);

		auxiliar.setFechaDeIngreso(null);

		internacion.darDeAlta(44444);

		// a tener en cuenta que el numero a pagar incrementara al pasar mas dias

		// Ejecucion
		Double montoEsperado = 231000D;
		Double montoRecibido = internacion.darDeAlta(44444).getPrecioFinal();

		// Verificacion
		assertEquals(montoEsperado, montoRecibido);
	}

	// Plan PLAN3000 va a tener cubierto el 50% del valor de la internación.
	@Test
	public void queSePuedaObtenerElPrecioAPagarPorElPlan3000DeUnPacienteDadoDeAlta()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN3000);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);
		Internacion auxiliar = internacion.buscarInternacionDeUnPaciente(1, 44444);

		auxiliar.setFechaDeIngreso(null);

		internacion.darDeAlta(44444);

		// a tener en cuenta que el numero a pagar incrementara al pasar mas dias

		// Ejecucion
		Double montoEsperado = 165000D;
		Double montoRecibido = internacion.darDeAlta(44444).getPrecioFinal();

		// Verificacion
		assertEquals(montoEsperado, montoRecibido);
	}

	// plan PLAN5000 va a tener cubiero el 70% del valor de la internacion.
	@Test
	public void queSePuedaObtenerElPrecioAPagarPorElPlan5000DeUnPacienteDadoDeAlta()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN5000);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);
		Internacion auxiliar = internacion.buscarInternacionDeUnPaciente(1, 44444);

		auxiliar.setFechaDeIngreso(null);

		internacion.darDeAlta(44444);

		// A tener en cuenta que el numero a pagar incrementara al pasar mas dias

		// Ejecucion
		Double montoEsperado = 99000D;
		Double montoRecibido = internacion.darDeAlta(44444).getPrecioFinal();

		// Verificacion
		assertEquals(montoEsperado, montoRecibido);
	}

	@Test
	public void queSePuedaObtenerElPrecioAPagarSinPlanDeUnPacienteDadoDeAlta()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.SIN_PLAN);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);
		Internacion auxiliar = internacion.buscarInternacionDeUnPaciente(1, 44444);

		auxiliar.setFechaDeIngreso(null);

		internacion.darDeAlta(44444);

		// A tener en cuenta que el numero a pagar incrementara al pasar mas dias

		// Ejecucion
		Double montoEsperado = 330000D;
		Double montoRecibido = internacion.darDeAlta(44444).getPrecioFinal();

		// Verificacion
		assertEquals(montoEsperado, montoRecibido);
	}

	@Test
	public void queSePuedaDarDeAltaObtenerElRecibo()
			throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		// Preparacion
		Habitacion h1 = new Habitacion(1, "P1_H101");
		Paciente paciente = this.crearPaciente(1, "Ignacio", "Guitierrez", 20, 44444, 11293, TipoDeCobertura.PLAN3000);
		Doctor doctor = this.crearDoctor(1, "Raul", "Gutierrez", 50, 182354, 11293, "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);

		internacion.ingresarPaciente(paciente, doctor, EstadoDeSalud.CODEANDO, Pronostico.NO_PUEDE_RESOLVER_BUGS, 1);
		Internacion auxiliar = internacion.buscarInternacionDeUnPaciente(1, 44444);

		auxiliar.setFechaDeIngreso(null);

		Recibo recibo = internacion.darDeAlta(44444);

		// Ejecucion
		String esperado = recibo.toString();
		Recibo recibido = internacion.darDeAlta(44444);

		System.out.println(recibido.toString());
		// Verificacion
		assertEquals(esperado, recibido.toString());
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
