package es.unizar.eina.vv6f.pruebas.mockito.ejercicio1;

import java.util.ArrayList;
import java.util.List;

// TODO Escribir pruebas para esta clase en JUnit4 con Mockito
public class MyPublisher implements Publisher {
	
	private List<Subscriber> subscribers = new ArrayList<Subscriber>();

	@Override
	public void add(Subscriber subscriber) {
		// TODO completar
	}

	@Override
	public void publish(String message) {
		// TODO completar
	}

}
