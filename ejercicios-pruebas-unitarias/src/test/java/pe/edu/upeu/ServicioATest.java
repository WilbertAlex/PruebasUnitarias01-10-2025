package pe.edu.upeu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ServicioATest {

    @Test
    void testSumar(){
        int a=10, b=2;
        ServicioA servicioA=new ServicioAImpl();
        int resultado=servicioA.sumar(a, b);
        Assertions.assertEquals(12,resultado);
    }
}
