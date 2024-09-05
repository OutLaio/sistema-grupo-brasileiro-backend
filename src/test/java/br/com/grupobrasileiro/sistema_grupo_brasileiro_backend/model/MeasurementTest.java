package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class MeasurementTest {

    @Test
    public void testEqualsAndHashCode() {
        Measurement measurement1 = new Measurement(1L, 10.0f, 20.0f, null);
        Measurement measurement2 = new Measurement(1L, 10.0f, 20.0f, null);
        Measurement measurement3 = new Measurement(2L, 15.0f, 25.0f, null);

        // Test equality
        assertEquals(measurement1, measurement2);
        assertNotEquals(measurement1, measurement3);

        // Test hashCode
        assertEquals(measurement1.hashCode(), measurement2.hashCode());
        assertNotEquals(measurement1.hashCode(), measurement3.hashCode());
    }
}
