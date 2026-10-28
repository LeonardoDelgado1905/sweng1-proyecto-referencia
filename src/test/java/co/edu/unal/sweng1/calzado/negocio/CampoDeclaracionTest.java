package co.edu.unal.sweng1.calzado.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class CampoDeclaracionTest {
    
    @Test
    void campoVacioDebeGenerarExcepcionAlCorregir() {
        //Arrange - Crear un campo vacío
        CampoDeclaracion campo = new CampoDeclaracion("Peso bruto", "", null, 0.0, EstadoCampo.VACIO, false);

        //Act - Ejecutar Corrección vacía //Assert - Validar que se lanza la excepción CorreccionVaciaException
        assertThrows(CorreccionVaciaException.class, () -> campo.corregir(""));


        //Arrange - Crear un campo vacío
        CampoDeclaracion campo2 = new CampoDeclaracion("Peso bruto", "", null, 0.0, EstadoCampo.VACIO, false);

        //Act - Ejecutar Corrección vacía //Assert - Validar que se lanza la excepción CorreccionVaciaException
        assertThrows(CorreccionVaciaException.class, () -> campo.corregir(null));

        
        
    }
}
