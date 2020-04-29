package es.unizar.eina.vv6f.pruebas.mockito.ejercicio1;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class MyPublisherTest {
    private  static final String FIRST_MESSAGE = "1st message";

    @Test
    public void test_1subscriber_1message() {
        // Creación del mock
        Subscriber subscriber = Mockito.mock(Subscriber.class);

        // Creación del objeto de pruebas
        Publisher publisher = new MyPublisher();

        // Configuración del objeto de pruebas para que utilice el mock:
        // suscripción del mock al objeto de pruebas
        publisher.add(subscriber);

        // Ejecución del objeto de pruebas. Se supone que este debe interactuar con el mock.
        publisher.publish(FIRST_MESSAGE);

        // Comprobación del estado del mock
        verify(subscriber).receive(eq(FIRST_MESSAGE));
    }
}