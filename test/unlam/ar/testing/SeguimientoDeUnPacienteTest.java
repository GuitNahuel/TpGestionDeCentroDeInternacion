package unlam.ar.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.unlam.dominio.CentroDeInternacion;
import ar.unlam.dominio.Habitacion;
import ar.unlam.dominio.HabitacionNoEncontradaExeption;
import ar.unlam.dominio.Enum.EstadoDeSalud;
import ar.unlam.dominio.Enum.Pronostico;
import ar.unlam.dominio.Enum.TipoDeCobertura;
import ar.unlam.dominio.doctor.Doctor;
import ar.unlam.dominio.doctor.DoctorNoEncontradoException;
import ar.unlam.dominio.paciente.Historial;
import ar.unlam.dominio.paciente.InformacionMedica;
import ar.unlam.dominio.paciente.Internacion;
import ar.unlam.dominio.paciente.InternacionNoEncontradaExeption;
import ar.unlam.dominio.paciente.Paciente;
import ar.unlam.dominio.paciente.PacienteNoEncontradoException;

public class SeguimientoDeUnPacienteTest {

	/* Este test contendra todos los test necesarios para seguir la evolucion de un paciente a lo largo de una internacion para poder seguir de cerca su
	 * evolucion.
	 * 
	 * Cosas a tener en cuenta: Si el estado de salud del paciente sufrio una evolucion y el pronostico dado no, o en viceversa,  
	 * agregar el anterior pronostico con el nuevo estado de salud para poder obtener un mejor seguimiento de la evolucion del paciente
	 * 
	 * A continuacion los test se presentar: 
	 * 
	 * dadoUnPacienteInternadoPoderCambiarSuEstadoDeSalud
	 * dadoUnPacienteInternadoPoderCambiarSuPronostico
	 * dadoUnPacienteInternadoPoderObtenerLaCantidadDeEvolucionesQueTuvoElPaciente
	 * dadoUnPacienteInternadoObtenerLaEvolucionMasReciente
	 * 
	 */
	
	private CentroDeInternacion internacion;
	private String nombre = "Hospital 100% legal";
	
	@Before 
	public void init() {
		this.internacion = new CentroDeInternacion(nombre);
	}
	@Test
	public void dadoUnPacienteInternadoPoderCambiarSuEstadoDeSalud() throws InternacionNoEncontradaExeption, PacienteNoEncontradoException, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor     = this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarHabitacion(h1);
		internacion.agregarDoctor(doctor);
		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.ENFERMEDAD_GRAVE , Pronostico.MODERADO, 1);

		Internacion internacionEsperada = internacion.obtenerLaInternacionMasRecienteDeUnPaciente(44444);

		internacion.anotarEvolucionDelPaciente(internacionEsperada , EstadoDeSalud.ENFERMEDAD_LEVE , Pronostico.MODERADO);
		
		//Ejecucion
		EstadoDeSalud estadoEsperado = EstadoDeSalud.ENFERMEDAD_LEVE;
		EstadoDeSalud estadoObtenido = internacionEsperada.getEstado();
		//Verificacion
		assertEquals(estadoEsperado, estadoObtenido);
	}
	@Test
	public void dadoUnPacienteInternadoPoderCambiarSuPronostico() throws InternacionNoEncontradaExeption, PacienteNoEncontradoException, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor     = this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarHabitacion(h1);
		internacion.agregarDoctor(doctor);
		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.ENFERMEDAD_GRAVE , Pronostico.MODERADO, 1);

		Internacion internacionEsperada = internacion.obtenerLaInternacionMasRecienteDeUnPaciente(44444);

		internacion.anotarEvolucionDelPaciente(internacionEsperada , EstadoDeSalud.AGUDO , Pronostico.FAVORABLE);
		
		//Ejecucion
		Pronostico pronosticoEsperado = Pronostico.FAVORABLE;
		Pronostico pronosticoObtenido = internacionEsperada.getPronostico();
		//Verificacion
		assertEquals(pronosticoEsperado, pronosticoObtenido);
	}
	@Test
	public void dadoUnPacienteInternadoPoderObtenerLaCantidadDeEvolucionesQueTuvoElPaciente() throws InternacionNoEncontradaExeption, PacienteNoEncontradoException, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor     = this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarHabitacion(h1);
		internacion.agregarDoctor(doctor);
		
		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.ENFERMEDAD_GRAVE , Pronostico.MODERADO, 1);

		Internacion internacionEsperada = internacion.obtenerLaInternacionMasRecienteDeUnPaciente(44444);

		internacion.anotarEvolucionDelPaciente(internacionEsperada , EstadoDeSalud.AGUDO , Pronostico.FAVORABLE);

		internacion.anotarEvolucionDelPaciente(internacionEsperada , EstadoDeSalud.ENFERMEDAD_LEVE , Pronostico.FAVORABLE);

		//Ejecucion
		int cantidadEsperada = 2;
		//Verificacion
		assertEquals(cantidadEsperada, internacionEsperada.obtenerHistorial().size());
	}
	@Test
	public void dadoUnPacienteInternadoObtenerLaEvolucionMasReciente() throws InternacionNoEncontradaExeption, PacienteNoEncontradoException, DoctorNoEncontradoException, HabitacionNoEncontradaExeption {
		//Preparacion
		Habitacion h1     = new Habitacion(1 , "P1_H101");
		Paciente paciente = this.crearPaciente(1 ,"Ignacio" , "Guitierrez" , 20 , 44444 , 11293 , TipoDeCobertura.PLAN1500);
		Doctor doctor     = this.crearDoctor(1 , "Raul", "Gutierrez" , 50 , 182354, 11293 , "MHJ208");

		internacion.agregarHabitacion(h1);
		internacion.agregarDoctor(doctor);

		internacion.ingresarPaciente(paciente, doctor  , EstadoDeSalud.ENFERMEDAD_GRAVE , Pronostico.MODERADO, 1);

		Internacion internacionEsperada = internacion.obtenerLaInternacionMasRecienteDeUnPaciente(44444);
		
		internacion.anotarEvolucionDelPaciente(internacionEsperada , EstadoDeSalud.AGUDO , Pronostico.FAVORABLE);

		// Estado de salud enfermedad grave.
		Historial informacionAntigua = internacionEsperada.obtenerLaEvulucionMasReciente();
		
		// Seteo la fecha de modificacion de la informacion para darle una fecha antigua.
		
		informacionAntigua.setFechaModificacion(null);
		
		internacion.anotarEvolucionDelPaciente(internacionEsperada , EstadoDeSalud.CRITICO, Pronostico.FAVORABLE);

		
		//Ejecucion
		EstadoDeSalud estadoEsperado = EstadoDeSalud.AGUDO;
		EstadoDeSalud estadoObtenido = internacionEsperada.obtenerLaEvulucionMasReciente().getEstadoAnterior();
		
		//Verificacion
		assertEquals(estadoEsperado, estadoObtenido);
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
