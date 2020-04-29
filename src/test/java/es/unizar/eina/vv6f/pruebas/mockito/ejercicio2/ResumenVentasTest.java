package es.unizar.eina.vv6f.pruebas.mockito.ejercicio2;

import org.junit.*;
import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ResumenVentasTest {

    /* Resultados esperados (textos esperados en la salida estándar) */
    private static final String RESULTADO_VACIO
            = System.lineSeparator()
            + "Resumen de ventas por cliente almacenados en el fichero anterior"
            + System.lineSeparator() + System.lineSeparator()
            + "Cliente   Importe" + System.lineSeparator()
            + "-------   -------" + System.lineSeparator();

    private static final String RESULTADO_UNA_VENTA
            = RESULTADO_VACIO
            + String.format(ResumenVentas.FORMATO_RESUMEN_CLIENTE, 150, 15.0);

    private static final String RESULTADO_CUATRO_VENTAS
            = RESULTADO_VACIO
            + String.format(ResumenVentas.FORMATO_RESUMEN_CLIENTE, 150, 115.0)
            + String.format(ResumenVentas.FORMATO_RESUMEN_CLIENTE, 159, 219.0);

    private static final String RESULTADO_DOS_VENTAS
            = RESULTADO_VACIO
            + String.format(ResumenVentas.FORMATO_RESUMEN_CLIENTE, 150, 15.0)
            + String.format(ResumenVentas.FORMATO_RESUMEN_CLIENTE, 159, 100.0);

    private static final String IO_EXCEPTION_MESSAGE = "Error inyectado";

    private static final String RESULTADO_IO_EXCEPTION
            = RESULTADO_VACIO
            + "Error de E/S: " + IO_EXCEPTION_MESSAGE + System.lineSeparator();


    /* Atributos para la gestión de la salida estándar y su sustituta */
    private OutputStream outSustituta;
    private static PrintStream outOriginal;


    /* Gestión de la salida estándar original antes y después de las pruebas:
       salvaguarda y restauración */
    @BeforeClass
    public static void guardarSystemOutOriginal() {
        outOriginal = System.out;
    }

    @AfterClass
    public static void restablecerSystemOutOriginal() {
        System.setOut(outOriginal);
    }


    /* Gestión del sustituto de la salida estándar antes y después de cada prueba: */
    @Before
    public void setUp() {
        outSustituta = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outSustituta));
    }

    @After
    public void tearDown() {
        // Cierra System.out e, indirectamente, outSustituta
        // Dado que outSustituta es un objeto en memoria, no es estrictamente
        // necesario, pero el protocolo en Java indica cerrar siempre los streams.
        System.out.close();
    }


    /* Tests para cada uno de los casos de prueba identificados: */
    @Test
    public void testEscribirResumen_ficheroVacio() throws IOException {
        final DataInput f = mock(DataInput.class);
        when(f.readInt()).thenThrow(new EOFException());
        ResumenVentas.escribirResumen(f);
        assertEquals(RESULTADO_VACIO, outSustituta.toString());
    }

    @Test
    public void testEscribirResumen_unaVenta() throws IOException {
        final DataInput f = mock(DataInput.class);
        when(f.readInt()).thenReturn(19115, 150, 15).thenThrow(new EOFException());
        when(f.readDouble()).thenReturn(1.00);
        ResumenVentas.escribirResumen(f);
        assertEquals(RESULTADO_UNA_VENTA, outSustituta.toString());
    }

    @Test
    public void testEscribirResumen_ficheroCuatroVentasDosClientesDistintos()
            throws IOException {
        final DataInput f = mock(DataInput.class);

        when(f.readInt()).thenReturn(19115, 150, 15)
                .thenReturn(19119, 150, 1)
                .thenReturn(28080, 159, 2)
                .thenReturn(28081, 159, 3)
                .thenThrow(new EOFException());

        when(f.readDouble()).thenReturn(1.00)
                .thenReturn(100.0)
                .thenReturn(75.0)
                .thenReturn(23.0);

        ResumenVentas.escribirResumen(f);
        assertEquals(RESULTADO_CUATRO_VENTAS,
                outSustituta.toString());
    }

    @Test
    public void testEscribirResumen_ficheroDosVentasClientesDistintos()
            throws IOException {
        final DataInput f = mock(DataInput.class);

        when(f.readInt()).thenReturn(19115, 150, 15)
                .thenReturn(19119, 159, 1)
                .thenThrow(new EOFException());

        when(f.readDouble()).thenReturn(1.00)
                .thenReturn(100.0);

        ResumenVentas.escribirResumen(f);
        assertEquals(RESULTADO_DOS_VENTAS,
                outSustituta.toString());
    }

    @Test
    public void testEscribirResumen_ficheroIOException() throws IOException {
        final DataInput f = mock(DataInput.class);
        when(f.readInt()).thenThrow(new IOException(IO_EXCEPTION_MESSAGE));
        ResumenVentas.escribirResumen(f);
        assertEquals(RESULTADO_IO_EXCEPTION, outSustituta.toString());
    }
}
