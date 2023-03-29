package es.unizar.eina.vv6f.pruebas.mockito.ejercicio1;

public interface Publisher {
	void add(Subscriber subscriber);
	void publish(String message);
}
