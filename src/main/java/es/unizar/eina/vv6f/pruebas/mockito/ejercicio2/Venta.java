package es.unizar.eina.vv6f.pruebas.mockito.ejercicio2;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;

/**
 * Los objetos de esta clase representan ventas realizadas por una empresa
 */
public class Venta {
	
	/**
	 * Código del producto vendido en esta venta
	 */
	private int producto;

	/**
	 * Código del cliente al que se le realizó esta venta
	 */
	private int cliente;

	/**
	 * Cantidad de producto vendido en esta venta
	 */
	private int cantidad;

	/**
	 * Precio unitario del producto aplicado en esta venta
	 */
	private double precioUnitario;

	/**
	 * Pre: cantidad > 0; precioUnitario > 0.0
	 * 
	 * Post: Ha inicializado los atributos de esta nueva venta a partir de
	 * los valores de los parámetros
	 */
	public Venta(int producto, int cliente, int cantidad,
			double precioUnitario) {
		this.producto = producto;
		this.cliente = cliente;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}

	/**
	 * Post: Ha devuelto el código del producto vendido en esta venta
	 */
	public int producto() {
		return producto;
	}

	/**
	 * Post: Ha devuelto la cantidad de producto vendido en esta venta
	 */
	public int cantidad() {
		return cantidad;
	}

	/**
	 * Post: Ha devuelto el precio unitario del producto aplicado en esta
	 * venta
	 */
	public double precioUnitario() {
		return precioUnitario;
	}

	/**
	 * Post: Ha devuelto el código del cliente al que se le realizó esta
	 * venta
	 */
	public int cliente() {
		return cliente;
	}

	/**
	 * Pre: f no es nulo y no ha sido cerrado previamente
	 * 
	 * Post: Ha leído los datos de una venta de «f» y ha devuelto un nuevo
	 * objeto de la clase Venta creado a partir de los datos leídos. Si no
	 * había datos suficientes o se ha producido algún error en la lectura,
	 * ha devuelto «null»
	 */
	public static Venta leerVenta(DataInput f) throws IOException {
		try {
			int producto = f.readInt();
			int cliente = f.readInt();
			int cantidad = f.readInt();
			double precioUnitario = f.readDouble();
			return new Venta(producto, cliente, cantidad, precioUnitario);
		}
		catch (EOFException ex) {
			return null;
		}
	}
}
