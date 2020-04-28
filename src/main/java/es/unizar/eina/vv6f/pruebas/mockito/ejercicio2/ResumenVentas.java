package es.unizar.eina.vv6f.pruebas.mockito.ejercicio2;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Esta clase tiene un método main que, al ser ejecutado, solicita al
 * operador el nombre de un fichero de ventas y escribe en la pantalla un
 * resumen de las ventas, listando, para cada código de cliente, el importe
 * total de las compras que ha realizado.
 * 
 * La estructura de los ficheros binarios de ventas responde al siguiente
 * esquema:
 * 
 * <pre>
 * «fichero_ventas» ::= { «venta» }
 * «venta» ::= «producto» «cliente» «cantidad» «precioUnitario»
 * «producto» ::= int -- Código de producto
 * «cliente» ::= int -- Código de cliente
 * «cantidad» ::= int -- Unidades vendidas
 * «precioUnitario» ::= double -- Precio unitario
 * </pre>
 * 
 * Los datos de ventas de estos ficheros están ordenados en orden creciente
 * de códigos de cliente, de forma que las ventas de un mismo cliente están
 * almacenadas, necesariamente, de forma consecutiva.
 * 
 * La interacción con el operador es como sigue:
 * 
 * <pre>
 * Nombre del fichero de ventas: v2014.dat
 * 
 * Resumen de ventas por cliente almacenados en el fichero anterior
 * 
 * Cliente   Importe
 * -------   -------
 *     150     15,00
 *     159    250,00
 * </pre>
 */

public class ResumenVentas {

	public static final String FORMATO_RESUMEN_CLIENTE = "%7d %9.2f%n";

	/**
	 * Teclado como Scanner
	 */
	private static final Scanner teclado = new Scanner(System.in);

	/**
	 * Pre: ---
	 * 
	 * Post: Ha solicitado al operador el nombre de un fichero de ventas y
	 * ha escrito en la pantalla un resumen de las ventas, listando, para
	 * cada código de cliente, el importe total de las compras que ha
	 * realizado.
	 */
	public static void main(String[] args) {
		System.out.print("Nombre del fichero de ventas: ");
		File fichero = new File(teclado.nextLine());
		try (DataInputStream dataInput =
				new DataInputStream(new FileInputStream(fichero))) {
			escribirResumen(dataInput);
		}
		catch (FileNotFoundException ex) {
			System.out.println("Error. El fichero no existe: "
					+ ex.getMessage());
		}
		catch (IOException ex) {
			System.out.println("Error de E/S: " + ex.getMessage());
		}
	}

	/**
	 * Pre: ---
	 * 
	 * Post: Ha escrito un encabezado a los datos del resumen
	 */
	protected static void escribirEncabezado() {
		System.out.println();
		System.out.println("Resumen de ventas por cliente almacenados "
				+ "en el fichero anterior");
		System.out.println();
		System.out.println("Cliente   Importe");
		System.out.println("-------   -------");
	}

	/**
	 * Pre: f es no nulo y permite leer de un fichero binario de ventas con
	 * la estructura y características planteadas en el enunciado
	 * 
	 * Post: Ha escrito en la pantalla un resumen de las ventas, listando,
	 * para cada código de cliente, el importe total de las compras que ha
	 * realizado.
	 */
	protected static void escribirResumen(DataInput f) {
		try {
			escribirEncabezado();
			/*
			 * Recorrido del fichero y escritura de resumen. Se utilizan dos
			 * objetos venta: el denominado «penultima» representa la
			 * penúltima venta leía del fichero, cuyo importe ya está
			 * acumulado en «total»; el objeto «ultima» representa la última
			 * venta leída, que todavía hay que procesar: si sigue siendo
			 * del mismo cliente, se acumula su importe en «total»; si es de
			 * un cliente distinto, se escribe el total del cliente anterior
			 * y se da un nuevo valor a total como el importe de la primera
			 * compra del nuevo cliente.
			 */
			Venta penultima = Venta.leerVenta(f);

			if (penultima != null) {
				// Valor inicial de «total» con el importe de la primera
				// compra del primer cliente
				double total =
						penultima.precioUnitario() * penultima.cantidad();
				Venta ultima = Venta.leerVenta(f);

				while (ultima != null) {
					// Se averigua si las dos últimas compras corresponden
					// al mismo cliente
					if (penultima.cliente() == ultima.cliente()) {
						// Se acumula el importe de la última venta en el
						// total del cliente
						total +=
								ultima.precioUnitario() * ultima.cantidad();
					}
					else {
						// Las dos últimas ventas son de clientes distintos:
						// Escritura del importe total del cliente anterior
						System.out.printf(FORMATO_RESUMEN_CLIENTE,
								penultima.cliente(), total);
						// Nuevo valor de total para el nuevo cliente con el
						// importe de su primera compra
						total = ultima.precioUnitario() * ultima.cantidad();
					}
					// Para la siguiente iteración, leemos un datos más. La
					// última venta leída en esta iteración será la
					// penúltima en la siguiente
					penultima = ultima;
					ultima = Venta.leerVenta(f);
				}
				// Escritura del importe total del último cliente del fichero
				System.out.printf(FORMATO_RESUMEN_CLIENTE, penultima.cliente(), total);
			}
		}
		catch (IOException ex) {
			System.out.println("Error de E/S: " + ex.getMessage());
		}
	}
}
