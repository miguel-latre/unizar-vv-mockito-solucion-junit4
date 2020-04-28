package es.unizar.eina.vv6f.pruebas.mockito.ejemplo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Basado en: Lars Vogel, Fabian Pfaff. «Unit tests with Mockito – Tutorial».
 * Vogella. Version 1.9. 10-4-2017.
 * http://www.vogella.com/tutorials/Mockito/article.html [accedido el 28-4-2020]
 */

@RunWith(MockitoJUnitRunner.class)
public class MiDependenciaTestAnotada {

    @Mock
    private MiDependencia mock;

    @Test
    public void testVerify()  {
        // create and configure mock
        when(mock.obtenerId()).thenReturn(43);

        // call method probar on the mock with parameter 12
        mock.probar(12);
        mock.obtenerId();
        mock.obtenerId();
        for (int i = 0; i < 5; i++) {
            mock.llamar5veces();
        }

        // now check if method probar was called with the parameter 12
        verify(mock).probar(ArgumentMatchers.eq(12));

        // was the method obtenerId called twice?
        verify(mock, times(2)).obtenerId();

        // other alternatives for verifiying the number of method calls for a method
        verify(mock, never()).noUsar();
        verify(mock, atLeastOnce()).probar(12);
        verify(mock, atLeast(2)).obtenerId(); // called at least twice
        verify(mock, atMost(3)).obtenerId(); // called at most 3 times
        verify(mock, times(5)).llamar5veces(); // called five times
    }
}
