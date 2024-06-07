package ar.unlam.dominio.paciente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import ar.unlam.dominio.Habitacion;
import ar.unlam.dominio.Enum.EstadoDeSalud;
import ar.unlam.dominio.Enum.Pronostico;
import ar.unlam.dominio.doctor.Doctor;

public class Internacion {
	
	private Integer       codigo;
	private Paciente	  paciente;
	private Doctor  	  doctor;
	private Habitacion    habitacion;
	private EstadoDeSalud estado;
	private Pronostico    pronostico;
	
	private LocalDate fechaDeIngreso;
	private LocalDate fechaDeSalida;
	private Boolean   internacionActiva;
	
	private List<Historial>historial;
		
	public Internacion(Paciente paciente , Doctor doctor , Habitacion habitacion ,EstadoDeSalud estado , Pronostico pronostico , Integer codigo ) {
		this.paciente = paciente;
		this.doctor   = doctor;
		this.habitacion = habitacion;
		this.estado     = estado;
		this.pronostico = pronostico;
		
		this.fechaDeSalida     = null;
		this.internacionActiva = true;
		this.fechaDeIngreso = LocalDate.now();
		this.codigo 	    = codigo;
		this.historial = new ArrayList<>();
	}
	
	public void setInternacionActiva(Boolean internacionActiva) {
		this.internacionActiva = internacionActiva;
	}

	public LocalDate getFechaDeSalida() {
		return fechaDeSalida;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public LocalDate getFechaDeIngreso() {
		return fechaDeIngreso;
	}

	public Boolean getInternacionActiva() {
		return internacionActiva;
	}

	public Boolean isInternacionActiva() {
		return this.internacionActiva;
	}
	
	public EstadoDeSalud getEstado() {
		return estado;
	}

	public Pronostico getPronostico() {
		return pronostico;
	}
	
	public Habitacion getHabitacion() {
		return habitacion;
	}
	
	public Paciente getPaciente() {
		return paciente;
	}
	
	public List<Historial>obtenerHistorial(){
		return this.historial;
	}
	
	public void nuevaEvolucionDelPaciente(EstadoDeSalud Nuevoestado , Pronostico Nuevopronostico) {
		Historial informacionAntigua = new Historial(this.estado , this.pronostico);
		
		this.historial.add(informacionAntigua);
		
		this.estado = Nuevoestado;
		this.pronostico = Nuevopronostico;
	}
	
	public Historial obtenerLaEvulucionMasReciente() {
	Comparator<Historial> comparador = Comparator.comparing(Historial::getFechaDeModificacion);

	Historial evolucionMasReciente = Collections.max(historial, comparador);

	return evolucionMasReciente;
	
	}
	//metodo para probar los direntes test que requieran fechas diferentes
	public void setFechaDeIngreso(LocalDate fecha) {
		this.fechaDeIngreso = LocalDate.of(2024, 5, 3);
	}

	public void finalizarInternacion() {
		this.paciente.alta();
		this.internacionActiva = false;
		this.fechaDeSalida = LocalDate.now();
	}
	
	public void liberarHabitacion() {
		this.habitacion.desalojarHabitacion();;
	}
	
}
