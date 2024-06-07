package ar.unlam.dominio.paciente;

import java.time.LocalDate;

import ar.unlam.dominio.Enum.EstadoDeSalud;
import ar.unlam.dominio.Enum.Pronostico;

public class Historial {

	private EstadoDeSalud estadoAnterior;
	private Pronostico pronosticoAnterior;
	private LocalDate fechaDeModificacion;

	public Historial(EstadoDeSalud estadoAnterior, Pronostico pronosticoAnterior) {
		this.estadoAnterior = estadoAnterior;
		this.pronosticoAnterior = pronosticoAnterior;
		this.fechaDeModificacion = LocalDate.now();
	}

	public EstadoDeSalud getEstadoAnterior() {
		return estadoAnterior;
	}

	public Pronostico getPronosticoAnterior() {
		return pronosticoAnterior;
	}

	public LocalDate getFechaDeModificacion() {
		return fechaDeModificacion;
	}

	//metodo para probar los direntes test que requieran fechas diferentes
	public void setFechaModificacion(LocalDate fecha) {
		this.fechaDeModificacion = LocalDate.of(2024, 5, 3);
	}

}
