package ar.unlam.dominio.metodo.de.pago;

public class PrecioSinPlan extends CalcularPrecio {

	public PrecioSinPlan(Double precioOrigen, Double comision) {
		super(precioOrigen, comision);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double calcularPrecioFinal() {
		
		return super.getPrecioOrigen();
	}

}
