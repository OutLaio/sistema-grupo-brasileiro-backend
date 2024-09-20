//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//public class MeasurementViewTest {
//
//    @Test
//    void testMeasurementViewConstructor() {
//        // Given
//        Float height = 10.5f;
//        Float length = 20.0f;
//
//        // When
//        MeasurementView measurementView = new MeasurementView(height, length);
//
//        // Then
//        assertEquals(height, measurementView.height(), "Height should be equal to the value provided");
//        assertEquals(length, measurementView.length(), "Length should be equal to the value provided");
//    }
//
//    @Test
//    void testMeasurementViewEquality() {
//        // Given
//        Float height = 15.5f;
//        Float length = 25.0f;
//        MeasurementView measurementView1 = new MeasurementView(height, length);
//        MeasurementView measurementView2 = new MeasurementView(height, length);
//
//        // When & Then
//        assertEquals(measurementView1, measurementView2, "MeasurementView objects with the same height and length should be equal");
//    }
//
//    @Test
//    void testMeasurementViewInequality() {
//        // Given
//        Float height1 = 10.5f;
//        Float length1 = 20.0f;
//        Float height2 = 15.5f;
//        Float length2 = 25.0f;
//        MeasurementView measurementView1 = new MeasurementView(height1, length1);
//        MeasurementView measurementView2 = new MeasurementView(height2, length2);
//
//        // When & Then
//        assertNotEquals(measurementView1, measurementView2, "MeasurementView objects with different height or length should not be equal");
//    }
//}
