package ar.unlam.dominio.metodo.de.pago;

public class PrecioConPlan extends CalcularPrecio {

	public PrecioConPlan(Double precioOrigen, Double comision) {
		super(precioOrigen, comision);
	}

	@Override
	public Double calcularPrecioFinal() {
		Double precioMasComision = super.calcularPrecioConComision();
		return super.getPrecioOrigen() - precioMasComision;
	}
}
