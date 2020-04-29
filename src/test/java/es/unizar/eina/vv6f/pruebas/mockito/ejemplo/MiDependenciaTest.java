package es.unizar.eina.vv6f.pruebas.mockito.ejemplo;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


/**
 * Basado en: Lars Vogel, Fabian Pfaff. «Unit tests with Mockito – Tutorial».
 * Vogella. Version 1.9. 10-4-2017. 
 * http://www.vogella.com/tutorials/Mockito/article.html [accedido el 28-4-2020]
 */
public class MiDependenciaTest {

    @Test
    public void testSetReturnValue_1() {
        //  create mock
        MiDependencia mock = Mockito.mock(MiDependencia.class);

        // define return value for method obtenerId()
        when(mock.obtenerId()).thenReturn(43);

        // use mock in test....
        assertEquals(43, mock.obtenerId());
    }


    @Test
    public void testSetReturnValue_2() {
        //  create mock
        MiDependencia mock = Mockito.mock(MiDependencia.class);

        // define return value for method obtenerId()
        doReturn(43).when(mock).obtenerId();

        // use mock in test....
        assertEquals(43, mock.obtenerId());
    }


    // Demonstrates the return of multiple values
    @Test
    public void testMoreThanOneReturnValue_1()  {
        Iterator iterator = mock(Iterator.class);
        when(iterator.next()).thenReturn("Mockito").thenReturn("rocks");
        String result = iterator.next() + " " + iterator.next();
        assertEquals("Mockito rocks", result);
    }


    // Demonstrates the return of multiple values
    @Test
    public void testMoreThanOneReturnValue_2()  {
        Iterator iterator = mock(Iterator.class);
        when(iterator.next()).thenReturn("Mockito", "rocks");
        String result = iterator.next() + " " + iterator.next();
        assertEquals("Mockito rocks", result);
    }


    // this test demonstrates how to return values based on the input
    @Test
    public void testReturnValueDependentOnMethodParameter()  {
        Comparable c = mock(Comparable.class);
        when(c.compareTo("Mockito")).thenReturn(1);
        when(c.compareTo("Eclipse")).thenReturn(2);
        assertEquals(1, c.compareTo("Mockito"));
        assertEquals(2, c.compareTo("Eclipse"));
    }


    @Test
    public void testReturnValueIndependentOnMethodParameter()  {
        Comparable c = mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        assertEquals(-1, c.compareTo(9));
        assertEquals(0, c.compareTo("Mockito"));
    }


    // return a value based on the type of the provide parameter
    @Test
    public void testReturnValueInDependentOnMethodClass()  {
        Comparable c= mock(Comparable.class);
        when(c.compareTo(isA(Object.class))).thenReturn(0);
        when(c.compareTo(isA(MiDependencia.class))).thenReturn(87);
        when(c.compareTo(isA(String.class))).thenReturn(105);
        //assert
        assertEquals(87 , c.compareTo(new MiDependencia()));
        assertEquals(105 , c.compareTo("String"));
        assertEquals(0 , c.compareTo(System.out));
    }


    // this test demonstrates how use doThrow
    @Test(expected = IOException.class)
    public void testForIOException() throws IOException {
        // create an configure mock
        OutputStream mockStream = mock(OutputStream.class);
        doThrow(new IOException()).when(mockStream).close();

        // use mock
        OutputStreamWriter streamWriter = new OutputStreamWriter(mockStream);
        streamWriter.close();
    }


    @Test
    public void testVerify()  {
        // Creación y configuración del mock
        MiDependencia mock = Mockito.mock(MiDependencia.class);

        // Invocación a varios métodos del mock
        mock.probar(12);
        mock.obtenerId();
        mock.obtenerId();
        for (int i = 0; i < 5; i++) {
            mock.llamar5veces();
        }

        // Comprobación: el método probar ha sido invocado una vez con el parámetro 12
        verify(mock).probar(eq(12));

        // Comprobación: el método obtenerId ha sido invocado 2 veces
        verify(mock, times(2)).obtenerId();

        // Otras comprobaciones:
        verify(mock, never()).noUsar();         // el método «noUsar» no se invocó nunca
        verify(mock, atLeastOnce()).probar(12); // el método «probar» con parámetro 12 se invocó al menos una vez
        verify(mock, atLeast(2)).obtenerId();   // el método «obtenerId» se invocó al menos 2 veces
        verify(mock, atMost(3)).obtenerId();    // el método «obtenerId» se invocó como mucho 3 veces
        verify(mock, times(5)).llamar5veces();  // el método «llamar5veces» se exactamente 5 veces
    }
}
