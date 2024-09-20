//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.MeasurementForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Measurement;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.BAgencyBoardRepository;
//
//@SpringBootTest
//public class MeasurementFormMapperTest {
//
//    @Mock
//    private BAgencyBoardRepository bAgencyBoardRepository;
//
//    @InjectMocks
//    private MeasurementFormMapper measurementFormMapper;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testMap_Success() {
//        // Given
//        Long bAgencyBoardId = 1L;
//        MeasurementForm measurementForm = new MeasurementForm(10.5f, 20.0f, bAgencyBoardId);
//        BAgencyBoard bAgencyBoard = new BAgencyBoard(); // Create a mock or real object as needed
//
//        when(bAgencyBoardRepository.findById(bAgencyBoardId)).thenReturn(java.util.Optional.of(bAgencyBoard));
//
//        // When
//        Measurement measurement = measurementFormMapper.map(measurementForm);
//
//        // Then
//        assertNotNull(measurement, "Measurement should not be null");
//        assertEquals(measurementForm.height(), measurement.getHeight(), "Height should match");
//        assertEquals(measurementForm.length(), measurement.getLength(), "Length should match");
//        assertEquals(bAgencyBoard, measurement.getBAgencyBoard(), "BAgencyBoard should match");
//    }
//
//    @Test
//    void testMap_BAgencyBoardNotFound() {
//        // Given
//        Long bAgencyBoardId = 1L;
//        MeasurementForm measurementForm = new MeasurementForm(10.5f, 20.0f, bAgencyBoardId);
//
//        when(bAgencyBoardRepository.findById(bAgencyBoardId)).thenReturn(java.util.Optional.empty());
//
//        // When & Then
//        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
//            measurementFormMapper.map(measurementForm);
//        });
//
//        assertEquals("Measurement não encontrado com o ID: " + bAgencyBoardId, exception.getMessage());
//    }
//}
