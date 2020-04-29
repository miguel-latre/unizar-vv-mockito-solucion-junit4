package es.unizar.eina.vv6f.pruebas.mockito.ejercicio1;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class MyPublisherTest {
    private  static final String FIRST_MESSAGE = "1st message";
    private static final String SECOND_MESSAGE = "2nd message";

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

    @Test
    public void test_2subscribers_1message() {
        // Creación de los mocks
        Subscriber subscriber1 = Mockito.mock(Subscriber.class);
        Subscriber subscriber2 = Mockito.mock(Subscriber.class);

        // Creación del objeto de pruebas
        Publisher publisher = new MyPublisher();

        // Configuración del objeto de pruebas para que utilice los mocks:
        // suscripción de los mocks al objeto de pruebas
        publisher.add(subscriber1);
        publisher.add(subscriber2);

        // Ejecución del objeto de pruebas. Se supone que este debe interactuar con los mocks.
        publisher.publish(FIRST_MESSAGE);

        // Comprobación del estado de los mocks
        verify(subscriber1).receive(eq(FIRST_MESSAGE));
        verify(subscriber2).receive(eq(FIRST_MESSAGE));
    }

    @Test
    public void test_2subscribers_2messages() {
        // Creación de los mocks
        Subscriber subscriber1 = Mockito.mock(Subscriber.class);
        Subscriber subscriber2 = Mockito.mock(Subscriber.class);

        // Creación del objeto de pruebas
        Publisher publisher = new MyPublisher();
        // Configuración del objeto de pruebas para que utilice los mocks:
        // suscripción de los mocks al objeto de pruebas
        publisher.add(subscriber1);
        publisher.add(subscriber2);

        // Ejecución del objeto de pruebas, enviado ahora dos mensajes
        publisher.publish(FIRST_MESSAGE);
        publisher.publish(SECOND_MESSAGE);

        // Comprobación del estado de los mocks
        verify(subscriber1).receive(eq(FIRST_MESSAGE));
        verify(subscriber2).receive(eq(FIRST_MESSAGE));
        verify(subscriber1).receive(eq(SECOND_MESSAGE));
        verify(subscriber2).receive(eq(SECOND_MESSAGE));
    }

}