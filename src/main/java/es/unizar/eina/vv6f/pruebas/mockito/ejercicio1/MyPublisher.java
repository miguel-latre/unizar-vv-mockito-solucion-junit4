package es.unizar.eina.vv6f.pruebas.mockito.ejercicio1;

import java.util.ArrayList;
import java.util.List;

public class MyPublisher implements Publisher {
	
	private final List<Subscriber> subscribers = new ArrayList<>();

	@Override
	public void add(Subscriber subscriber) {
		subscribers.add(subscriber);
	}

	@Override
	public void publish(String message) {
		for(Subscriber subscriber: subscribers) {
			subscriber.receive(message);
		}
	}
}
