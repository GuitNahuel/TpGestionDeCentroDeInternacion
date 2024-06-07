package ar.unlam.dominio.metodo.de.pago;

public abstract class CalcularPrecio {
	
	private Double comision;
	private Double precioOrigen;
	
	public CalcularPrecio(Double precioOrigen , Double comision) {
		this.precioOrigen = precioOrigen;
		this.comision = comision;
	}
	
	public abstract Double calcularPrecioFinal();
	
	protected Double calcularPrecioConComision() {
		return (comision * precioOrigen) / 100;
	}
	
	public Double getComision() {
		return comision;
	}


	public Double getPrecioOrigen() {
		return precioOrigen;
	}
	
}
