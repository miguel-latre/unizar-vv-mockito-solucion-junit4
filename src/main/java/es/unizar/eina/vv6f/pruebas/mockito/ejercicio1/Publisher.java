package es.unizar.eina.vv6f.pruebas.mockito.ejercicio1;

public interface Publisher {

	public abstract void add(Subscriber subscriber);

	public abstract void publish(String message);

}