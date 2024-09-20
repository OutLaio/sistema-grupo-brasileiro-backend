//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.MeasurementView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Measurement;
//
//public class MeasurementViewMapperTest {
//
//    private MeasurementViewMapper measurementViewMapper;
//
//    @BeforeEach
//    void setUp() {
//        measurementViewMapper = new MeasurementViewMapper();
//    }
//
//    @Test
//    void testMap_Success() {
//        // Given
//        Float height = 10.5f;
//        Float length = 20.0f;
//        Measurement measurement = new Measurement(
//            null, // id, can be null for this test
//            height,
//            length,
//            null // bAgencyBoard, can be null for this test
//        );
//
//        // When
//        MeasurementView measurementView = measurementViewMapper.map(measurement);
//
//        // Then
//        assertNotNull(measurementView, "MeasurementView should not be null");
//        assertEquals(height, measurementView.height(), "Height should match");
//        assertEquals(length, measurementView.length(), "Length should match");
//    }
//}
