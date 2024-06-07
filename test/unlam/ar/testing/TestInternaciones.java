package unlam.ar.testing;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import ar.unlam.dominio.*;
import ar.unlam.dominio.Enum.EstadoDeSalud;
import ar.unlam.dominio.Enum.Pronostico;
import ar.unlam.dominio.Enum.TipoDeCobertura;
import ar.unlam.dominio.doctor.Doctor;
import ar.unlam.dominio.doctor.DoctorNoEncontradoException;
import ar.unlam.dominio.paciente.InformacionMedica;
import ar.unlam.dominio.paciente.Internacion;
import ar.unlam.dominio.paciente.InternacionNoEncontradaExeption;
import ar.unlam.dominio.paciente.Paciente;
import ar.unlam.dominio.paciente.PacienteNoEncontradoException;

public class TestInternaciones {

	/* Este test tiene los principales test del programa asi como la mayor cantidad de test 
	 * se encarga de validar los filtros necesarios para el correcto funcionamiento de una internacion 
	 * asi como que no ocurra exepciones al momento de comprobar informacion
	 * a continuacion los test que presentan son:
	 * 
	 * dadoQueExisteInternacionesObtenerLaInternacionMasReciente
	 * dadoQueExisteUnPacienteObtenerLaCantidadDeInternacionesQueTuvo
	 * dadoQueExisteUnPacienteObtenerElUltimoDoctorPorElCualSeAtendio
	 * dadoQueExisteUnPacienteDadoDeAltaObtenerElUltimoDoctorPorElCualSeAtendio
	 * dadoQueExisteUnPacienteObtenerLaCantidadDeDoctoresConLosQueSeAtendio
	 * dadoQueExisteUnPacienteObtenerLaInternacionMasReciente
	 * dadoQueExisteUnPacienteObtenerSuEstadoDeSalud
	 * dadoQueExisteUnPacienteObtenerSuPronostico
	 * dadoQueExisteUnPacienteObtenerElTipoDePlan
	 * dadoQueExisteUnPacienteConInternacionesBuscarPorCodigoDeInternacion
	 * dadoQueNoExisteUnaInternacionNoObtenerla
	 */
	
	private CentroDeInternacion internacion;
	private String nombre = "Hospital 100% legal";
	
	@Before 
	public void init() {
		this.internacion = new CentroDeInternacion(nombre);
	}
	@Test
	public void dadoQueExisteInternacionesObtenerLaInternacionMasReciente() throws InternacionNoEncontradaExeption, PacienteNoEncontradoException, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Habitacion h2     = new Habitacion(2 , "P2_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);

		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1);
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.AGUDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 2);
		
		List<Internacion>internaciones = internacion.obtenerListaDeInternaciones();
		
		//Ejecucion
		EstadoDeSalud estadoMasReciente = EstadoDeSalud.AGUDO ;
		EstadoDeSalud estadoObtenido   = internacion.obtenerLaInternacionMasReciente(internaciones).getEstado();

		//Verificacion
		assertEquals(estadoMasReciente,estadoObtenido);
	}
	
	@Test
	public void dadoQueExisteUnPacienteObtenerLaCantidadDeInternacionesQueTuvo() throws InternacionNoEncontradaExeption, PacienteNoEncontradoException, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Habitacion h2     = new Habitacion(2 , "P2_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);

		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1);
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 2);
		
		//Ejecucion
		int cantidadDeInternaciones = 2;
		List<Internacion> cantidadDeInternacionesObtenidas = internacion.obtenerInternacionesDeUnPaciente(44444);

		//Verificacion
		assertEquals(cantidadDeInternaciones,cantidadDeInternacionesObtenidas.size());
	}
	
	@Test
	public void dadoQueExisteUnPacienteObtenerElUltimoDoctorPorElCualEstaAtendido() throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption{
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Habitacion h2     = new Habitacion(2 , "P2_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");
		Doctor doctorDos =  this.crearDoctor(2 , "Raul", "Gutierrez" , 50 , 1823514, 11293 , "MsHJ208");


		internacion.agregarDoctor(doctor);
		internacion.agregarDoctor(doctorDos);
		
		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);

		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1);
		
		internacion.ingresarPaciente(paciente, doctorDos  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 2);
		
				
		//Ejecucion
		Doctor doctorEsperado = doctorDos;
		Doctor doctorObtenido = internacion.verDoctorDelPaciente(44444);
		
		//Verificacion
		assertEquals(doctorEsperado.getDni() , doctorObtenido.getDni());
		
	}
	
	@Test
	public void dadoQueExisteUnPacienteDadoDeAltaObtenerElUltimoDoctorConElQueSeAtendio() throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption{
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Habitacion h2     = new Habitacion(2 , "P2_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");
		Doctor doctorDos =  this.crearDoctor(2 , "Raul", "Gutierrez" , 50 , 173514, 11293 , "MsHJ208");


		internacion.agregarDoctor(doctor);
		internacion.agregarDoctor(doctorDos);

		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);


		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1);
		
		Internacion i = internacion.buscarInternacionDeUnPaciente(1,44444);
		
		// Iniciamos una fecha de ingreso diferente al dia de hoy para poder obtener la internacion mas reciente.
		
		i.setFechaDeIngreso(null);
		
		internacion.ingresarPaciente(paciente, doctorDos  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 2 );

		internacion.finalizarInternacion(44444);
		
		//Ejecucion
		Doctor doctorEsperado = doctorDos;
		Doctor doctorObtenido = internacion.verDoctorDelPaciente(44444);;

		//Verificacion
		assertEquals(doctorEsperado.getDni() , doctorObtenido.getDni());

	}
	
	@Test
	public void dadoQueExisteUnPacienteObtenerConMuchasInternacionesMostrarLosDoctoresQueLoAtendieron() throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Habitacion h2     = new Habitacion(2 , "P2_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");
		Doctor doctorDos =  this.crearDoctor(2 , "Raul", "Gutierrez" , 50 , 1823514, 11293 , "MsHJ208");


		internacion.agregarDoctor(doctor);
		internacion.agregarDoctor(doctorDos);

		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);


		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1);

		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 2);


		//Ejecucion		
		int doctoresEsperados = 2;
		List<Doctor> doctorObtenido = internacion.obtenerListaDeDoctoresDeUnPaciente(44444);


		//Verificacion
		assertEquals(doctoresEsperados , doctorObtenido.size());
	}
	
	@Test
	public void dadoQueExisteUnPacienteObtenerLaInternacionMasReciente() throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption{
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Habitacion h2     = new Habitacion(2 , "P2_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");
		Doctor doctorDos =  this.crearDoctor(2 , "Raul", "Gutierrez" , 50 , 173514, 11293 , "MsHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarDoctor(doctorDos);

		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);

		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1);
		
		Internacion i = internacion.buscarInternacionDeUnPaciente(1,44444);

		// iniciamos una fecha de ingreso diferente al dia de hoy para poder obtener la internacion mas reciente.
		i.setFechaDeIngreso(null);

		internacion.ingresarPaciente(paciente, doctorDos  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 2);

		//Ejecucion
		Integer  codigoEsperado = 2 ;
		Internacion internacionObtenida = internacion.obtenerLaInternacionMasRecienteDeUnPaciente(44444);

		//Verificacion
		assertEquals(codigoEsperado , internacionObtenida.getCodigo());
	}

	@Test
	public void dadoQueExisteUnPacienteDadoDeAltaObtenerLaInternacionMasReciente() throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption{
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Habitacion h2     = new Habitacion(2 , "P2_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");
		Doctor doctorDos =  this.crearDoctor(2 , "Raul", "Gutierrez" , 50 , 173514, 11293 , "MsHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarDoctor(doctorDos);

		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);

		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1);

		Internacion i = internacion.buscarInternacionDeUnPaciente(1,44444);

		// Iniciamos una fecha de ingreso diferente al dia de hoy para poder obtener la internacion mas reciente.
		i.setFechaDeIngreso(null);

		internacion.ingresarPaciente(paciente, doctorDos  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 2);

		// Finilazamos la internacion para que no encuentre la internacion mas reciente por el isActiva.
		internacion.finalizarInternacion(44444);
		
		//Ejecucion
		Integer  codigoEsperado = 2 ;
		Internacion internacionObtenida = internacion.obtenerLaInternacionMasRecienteDeUnPaciente(44444);

		//Verificacion
		assertEquals(codigoEsperado , internacionObtenida.getCodigo());
	}

	@Test
	public void dadoQueExisteUnPacienteObtenerElEstadoDeSalud() throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Habitacion h2     = new Habitacion(2 , "P2_H101");


		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);
		internacion.agregarHabitacion(h2);
		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.AGUDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1 );

		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.CODEANDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 2);
				
		//Ejecucion
		EstadoDeSalud estadoDeSaludEsperado = EstadoDeSalud.CODEANDO;
		EstadoDeSalud estadoDeSaludObtenido = internacion.obtenerEstadoDeSalud(44444);
		
		//Verificacion
		assertEquals(estadoDeSaludEsperado,estadoDeSaludObtenido);
	}
	@Test
	public void dadoQueExisteUnPacienteObtenerElPronosticoDado() throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);
		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.AGUDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1);
				
		//Ejecucion
		Pronostico  pronosticoEsperado = Pronostico.NO_PUEDE_RESOLVER_BUGS;
		Pronostico  pronosticoObtenido = internacion.obtenerPronostico(44444);
		
		//Verificacion
		assertEquals(pronosticoEsperado, pronosticoObtenido);
	}
	@Test
	public void dadoQueExisteUnPacienteObtenerElTipoPlan() throws PacienteNoEncontradoException, InternacionNoEncontradaExeption, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);
		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.AGUDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1 );
				
		//Ejecucion
		TipoDeCobertura  coberturaEsperada = TipoDeCobertura.PLAN1500;
		TipoDeCobertura  coberturaObtenida = internacion.obtenerPlanDeUnPaciente(44444);
		
		//Verificacion
		assertEquals(coberturaEsperada, coberturaObtenida);
	}
	
	@Test
	public void dadoQueExisteUnPacienteBuscarInternacion() throws InternacionNoEncontradaExeption, PacienteNoEncontradoException, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);
		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.AGUDO , Pronostico.NO_PUEDE_RESOLVER_BUGS , 1);

		//Ejecucion
		Internacion internacionEsperada = internacion.buscarInternacionDeUnPaciente(1 , 44444);
		
		//Verificacion
		assertEquals(internacionEsperada.getCodigo(), internacion.obtenerLaInternacionMasRecienteDeUnPaciente(44444).getCodigo());
	}
	
	@Test(expected = InternacionNoEncontradaExeption.class)	
	public void dadoQueExisteUnPacienteBuscarInternacioInexistente() throws InternacionNoEncontradaExeption, PacienteNoEncontradoException, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");

		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor =  this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarDoctor(doctor);
		internacion.agregarHabitacion(h1);
		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.AGUDO , Pronostico.NO_PUEDE_RESOLVER_BUGS ,1);

		//Ejecucion
		Internacion internacionEsperada = internacion.buscarInternacionDeUnPaciente(3, 4444);
		
		//Verificacion
		assertEquals(internacionEsperada, internacion.obtenerLaInternacionMasRecienteDeUnPaciente(4444));
	}
	
	private Paciente crearPaciente(Integer codigo , String nombre, String apellido, Integer edad, Integer dni ,Integer telefono, TipoDeCobertura plan ) {
		InformacionMedica informacionMedica = this.crearInformacionMedica();
		return new Paciente(codigo  , nombre, apellido, edad, dni, telefono ,plan , informacionMedica);
	}
	
	private InformacionMedica crearInformacionMedica() {
		return new InformacionMedica("b negativo " , "nuez" , "" , "");
	}

	private Doctor crearDoctor(Integer codigo , String nombre, String apellido, Integer edad, Integer dni, Integer telefono,String matricula) {
		return new Doctor(codigo , nombre , apellido , edad , dni , telefono ,matricula);
	}
}
