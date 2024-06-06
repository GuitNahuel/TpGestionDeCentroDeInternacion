package ar.unlam.dominio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.unlam.dominio.Enum.EstadoDeSalud;
import ar.unlam.dominio.Enum.Pronostico;
import ar.unlam.dominio.Enum.TipoDeCobertura;
import ar.unlam.dominio.doctor.Doctor;
import ar.unlam.dominio.doctor.DoctorNoEncontradoException;
import ar.unlam.dominio.metodo.de.pago.CalcularPrecio;
import ar.unlam.dominio.metodo.de.pago.PrecioConPlan;
import ar.unlam.dominio.metodo.de.pago.PrecioSinPlan;
import ar.unlam.dominio.paciente.Internacion;
import ar.unlam.dominio.paciente.InternacionNoEncontradaExeption;
import ar.unlam.dominio.paciente.Paciente;
import ar.unlam.dominio.paciente.PacienteNoEncontradoException;


public class CentroDeInternacion implements ICentroDeInternacion {
	private static final Integer COSTO_DIA = 10000;

	private String nombre;
	private List<Doctor> doctores;
	private List<Paciente>pacientes;
	private List<Habitacion>habitaciones;
	private List<Internacion>internacionPaciente;


	public CentroDeInternacion(String nombre) {
		this.doctores      = new ArrayList<>();
		this.pacientes     = new ArrayList<>();
		this.habitaciones  = new ArrayList<>();
		this.nombre		   = nombre;
		this.internacionPaciente = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public List<Doctor> obtenerListaDeDoctores() {
		return this.doctores;
	}

	public List<Paciente> obtenerListaDePacientes() {
		return pacientes;
	}

	public List<Habitacion>obtenerListaDeHabitaciones(){
		return habitaciones;
	}

	public List<Internacion>obtenerListaDeInternaciones(){
		return this.internacionPaciente;
	}
	
	@Override
	public Boolean agregarHabitacion(Habitacion habitacion) {
		return habitaciones.add(habitacion);
	}

	private boolean siElDoctorExiste(Doctor doctor) {return doctores.contains(doctor);}

	@Override
	public Boolean agregarDoctor(Doctor doctor) {
		if(siElDoctorExiste(doctor))return false;

		return doctores.add(doctor);

	}

	private Boolean siElPacienteExiste(Paciente paciente) {return pacientes.contains(paciente);}
	
	@Override
	public Boolean ingresarPaciente(Paciente paciente , Doctor doctor , EstadoDeSalud estado , Pronostico pronostico , Integer codigo) throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		if(!(siElDoctorExiste(doctor)))return false;

		if(!(siQuedanHabitacionesDisponibles())) return false;

		if(siElPacienteExiste(paciente)){

			finalizarInternacion(paciente.getDni());
			// este return esta para que no compruebe que el dni existe porque si el paciente ya existe el dni tambien existira
			return iniciarInternacion(paciente, doctor, estado, pronostico , codigo); 
		}

		//este esta para que no se cancele la internacion a un paciente existente cuando pongamos un dni existente en la lista pero en otra persona
		if(siExisteElMismoDni(paciente.getDni())) return false;	

		//si no existe el mismo dni y la persona no existe lo agregamos como un nuevo paciente e iniciamos su internacion
		pacientes.add(paciente);

		return iniciarInternacion(paciente, doctor, estado, pronostico , codigo);
	} 

	private Boolean siExisteElMismoDni(Integer dni) {
		for (Paciente paciente : pacientes) {
			if(paciente.getDni().equals(dni)) return true;
		}
		return false;
	}

	private Boolean siQuedanHabitacionesDisponibles() {
		for (Habitacion h : habitaciones) {
			if(h.getIsDisponible())return true;
		}
		return false; 
	}

	public Habitacion obtenerHabitacionDisponible() {
		for (Habitacion h : habitaciones) {

			if(h.getIsDisponible()) {
				return h;
			}
		}
		// si el codigo esta bien echo nunca deberia entrar en este sentencia ya que solo se puede iniciar una internacion si hay habitaciones disponibles
		return null;
	}

	@Override
	public Boolean iniciarInternacion(Paciente paciente , Doctor doctor , EstadoDeSalud estado , Pronostico pronostico , Integer codigo) {
		Habitacion h = obtenerHabitacionDisponible();

		Internacion internacion = new Internacion(paciente , doctor , h , estado , pronostico , codigo);

		doctor.asignarPaciente(paciente);
		h.ocuparHabitacion();
		h.setPaciente(paciente);

		this.internacionPaciente.add(internacion);

		return true;
	}

	@Override
	public Doctor buscarDoctor(String matricula) throws DoctorNoEncontradoException {
		for (Doctor d : doctores) {
			if (d.getMatricula().equals(matricula)) {
				return d;
			}
		}
		throw new DoctorNoEncontradoException("No existe la matricula");
	}
	
	@Override
	public Paciente buscarPaciente(Integer dni) throws PacienteNoEncontradoException {
		for (Paciente paciente : pacientes) {
			if (paciente.getDni().equals(dni)) {
				return paciente;
			}
		}
		throw new PacienteNoEncontradoException("No existe el dni");
	}

	public List<Habitacion>obtenerListaDeHabitacionesDesocupadas(){
		List<Habitacion>disponibles = new ArrayList<>();

		for (Habitacion h : habitaciones) {
			if(h.getIsDisponible())disponibles.add(h);
		}
		return disponibles;
	}

	public List<Paciente> obtenerPacientesDeUnDoctor(String matricula) throws DoctorNoEncontradoException {
		Doctor doctor = buscarDoctor(matricula);

		return doctor.getPacientes();
	}

	//primero comprueba las internaciones que estan activas si no encuentra ninguna activa compara la fecha de ingreso mas reciente a la actual
	public Internacion obtenerLaInternacionMasReciente(List<Internacion> internaciones) {	
		for (Internacion internacion : internaciones) {
			if(internacion.isInternacionActiva()) return internacion;
		}

		return encontrarFechaDeIngresoMasRecienteDeUnPaciente(internaciones);

	}
	
	private Internacion encontrarFechaDeIngresoMasRecienteDeUnPaciente(List<Internacion> internaciones) {
		Comparator<Internacion> comparador = Comparator.comparing(Internacion::getFechaDeIngreso);

		Internacion internacionReciente = Collections.max(internaciones, comparador);

		return internacionReciente;
	}

	public List<Internacion> obtenerInternacionesDeUnPaciente(Integer dni) throws PacienteNoEncontradoException{
		List<Internacion> internacion = new ArrayList<>();

		for (Internacion i : internacionPaciente) {
			if(i.getPaciente().getDni().equals(dni)) {
				internacion.add(i);
			}
		}

		return internacion;
	}

	public Internacion obtenerLaInternacionMasRecienteDeUnPaciente(Integer dni) throws PacienteNoEncontradoException {
		List<Internacion>internaciones = obtenerInternacionesDeUnPaciente(dni);

		Internacion masReciente = obtenerLaInternacionMasReciente(internaciones);

		return masReciente;
	}

	//probar
	public Doctor verDoctorDelPaciente(Integer dni) throws PacienteNoEncontradoException {
		Internacion masReciente = obtenerLaInternacionMasRecienteDeUnPaciente(dni);
		return masReciente.getDoctor();
	}

	public List<Doctor> obtenerListaDeDoctoresDeUnPaciente(Integer dni) throws PacienteNoEncontradoException {
		List<Internacion>internaciones = obtenerInternacionesDeUnPaciente(dni);

		List<Doctor> doctores = new ArrayList<>();

		for (Internacion i : internaciones) {
			doctores.add(i.getDoctor());
		}
		return doctores;
	}

	public EstadoDeSalud obtenerEstadoDeSalud(Integer dni) throws PacienteNoEncontradoException {
		Internacion masReciente = obtenerLaInternacionMasRecienteDeUnPaciente(dni);

		return masReciente.getEstado();
	}

	public Pronostico obtenerPronostico(Integer dni) throws PacienteNoEncontradoException {
		Internacion masReciente = obtenerLaInternacionMasRecienteDeUnPaciente(dni);

		return masReciente.getPronostico();
	}

	public TipoDeCobertura obtenerPlanDeUnPaciente(Integer dni) throws PacienteNoEncontradoException {
		Paciente p = buscarPaciente(dni);
		return p.getPlan();
	}

	public Habitacion obtenerHabitacionDeUnPaciente(Integer dni) throws PacienteNoEncontradoException {
		Internacion masReciente = obtenerLaInternacionMasRecienteDeUnPaciente(dni);

		return masReciente.getHabitacion();
	}
	
	@Override
	public Internacion buscarInternacionDeUnPaciente(Integer codigoInternacion, Integer dni) throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		List<Internacion>internaciones = obtenerInternacionesDeUnPaciente(dni);

		for (Internacion i: internaciones) {
			if(i.getCodigo().equals(codigoInternacion)) return i;
		}

		throw new InternacionNoEncontradaExeption("Internacion no encontrada");

	}

	public void anotarEvolucionDelPaciente(Internacion internacionEsperada, EstadoDeSalud enfermedadLeve, Pronostico moderado) {
		internacionEsperada.nuevaEvolucionDelPaciente(enfermedadLeve, moderado);
	}
	
	public Recibo darDeAlta(Integer dni) throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		finalizarInternacion(dni);

		return generarRecibo(dni);
	}

	private Recibo generarRecibo(Integer dni) throws PacienteNoEncontradoException {
		Internacion masReciente = obtenerLaInternacionMasRecienteDeUnPaciente(dni);

		Double pagado 	   = 0.0;
		Double precio      = obtenerPrecioPorEstadia(masReciente.getFechaDeIngreso() , masReciente.getFechaDeSalida());
		Double precioFinal = obtenerPrecioFinal(masReciente.getPaciente() , precio);
		LocalDate fechaDeIngreso = masReciente.getFechaDeIngreso();
		LocalDate fechaDeSalida  = masReciente.getFechaDeSalida();
		Paciente paciente = masReciente.getPaciente();

		Recibo recibo = new Recibo(nombre , pagado , precio , precioFinal , fechaDeIngreso, fechaDeSalida , paciente);

		return recibo;
	}

	private Double obtenerPrecioFinal(Paciente paciente , Double precio) {
		CalcularPrecio precioEstadia = obtenerCalcularPrecio(paciente , precio);
		return precioEstadia.calcularPrecioFinal();
	}

	//plan PLAN5000   va a tener cubiero  el 70% del valor de la internacion.
	//Plan PLAN3000   va a tener cubierto el 50% del valor de la internación.
	//Plan PLAN1500   va a tener cubierto el 30% del valor de la internación.

	private CalcularPrecio obtenerCalcularPrecio(Paciente paciente , Double precio) {
		switch (paciente.getPlan()) {
		case PLAN5000:
			return new PrecioConPlan(precio, 70D);		
		case PLAN3000:
			return new PrecioConPlan(precio , 50D); 		
		case PLAN1500:
			return new PrecioConPlan(precio, 30D);
		case SIN_PLAN:
			return new PrecioSinPlan(precio , 0D);
		}
		return null;
	}


	private Double obtenerPrecioPorEstadia(LocalDate fechaDeIngreso, LocalDate fechaDeSalida) {
		Double diasInternado = (double) ChronoUnit.DAYS.between(fechaDeIngreso, fechaDeSalida);

		Double costoTotal = diasInternado * COSTO_DIA;

		return costoTotal;
	}

	public void finalizarInternacion(Integer dni)throws PacienteNoEncontradoException, InternacionNoEncontradaExeption {
		Internacion masReciente = obtenerLaInternacionMasRecienteDeUnPaciente(dni);

		masReciente.finalizarInternacion();
		masReciente.liberarHabitacion();
	}

	

}
