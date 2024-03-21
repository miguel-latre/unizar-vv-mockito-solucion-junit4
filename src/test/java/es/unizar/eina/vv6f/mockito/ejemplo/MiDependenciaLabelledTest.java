package es.unizar.eina.vv6f.mockito.ejemplo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Basado en: Lars Vogel, Fabian Pfaff. «Unit tests with Mockito – Tutorial».
 * Vogella. Version 2.3,16.08.2021.
 * <a href="http://www.vogella.com/tutorials/Mockito/article.html">Unit tests with Mockito - Tutorial</a>
 * [accedido el 19-3-2024]
 */

@RunWith(MockitoJUnitRunner.class)
public class MiDependenciaLabelledTest {

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

        // other alternatives for verifying the number of method calls for a method
        verify(mock, never()).noUsar();
        verify(mock, atLeastOnce()).probar(12);
        verify(mock, atLeast(2)).obtenerId(); // called at least twice
        verify(mock, atMost(3)).obtenerId(); // called at most 3 times
        verify(mock, times(5)).llamar5veces(); // called five times
        verifyNoMoreInteractions(mock);
    }
}
