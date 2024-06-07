package ar.unlam.dominio.paciente;

public class InformacionMedica {
	String grupoSanguineo;
	String alergias;
	String historialEnfermedades;
	String historialCirugias;
	
	public InformacionMedica(String grupoSanguineo , String alergias , String historialEnfermedades , String historialCirugias) {
		this.grupoSanguineo = grupoSanguineo;
		this.alergias       = alergias;
		this.historialEnfermedades = historialEnfermedades;
		this.historialCirugias = historialCirugias;
	}

	public String getGrupoSanguineo() {
		return grupoSanguineo;
	}

	public String getAlergias() {
		if(alergias.isEmpty())return "Sin alergias registradas";
		return alergias;
	}

	public void setAlergias(String alergias) {
		this.alergias += alergias;
	}
	public void agregarAlergias(String alergias) {
		this.alergias += " " + alergias;
	}

	public String getHistorialEnfermedades() {
		if(historialEnfermedades.isEmpty())return "Sin enfermedades registradas";
		return historialEnfermedades;
	}

	public void setHistorialEnfermedades(String historialEnfermedades) {
		this.historialEnfermedades = historialEnfermedades;
	}

	public String getHistorialCirugias() {
		if(this.historialCirugias.isEmpty())return "sin cirugias registradas";
		return historialCirugias;
	}

	public void setHistorialCirugias(String historialCirugias) {
		this.historialCirugias = historialCirugias;
	}
	
	public void agregarHistorialCirugiaas(String historialCirugias) {
		this.historialCirugias += " " + historialCirugias;
	}

	@Override
	public String toString() {
		return " InformacionMedica grupoSanguineo: " + grupoSanguineo + "\n  "
			   +" Alergias: "              + getAlergias() +"\n"
			   +" HistorialEnfermedades: " + getHistorialEnfermedades() + 
			   "\n  HistorialCirugias: "   + getHistorialCirugias();
	}
	
	
	
}
