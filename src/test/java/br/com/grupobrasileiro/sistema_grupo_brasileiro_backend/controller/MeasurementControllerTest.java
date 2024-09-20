//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;
//
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.MeasurementForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.MeasurementView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.MeasurementService;
//
//import java.util.Collections;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.javafaker.Faker;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(MeasurementController.class)
//public class MeasurementControllerTest {
//
//    @Mock
//    private MeasurementService measurementService;
//
//    @InjectMocks
//    private MeasurementController measurementController;
//
//    private MockMvc mockMvc;
//    private Faker faker;
//
//    @BeforeEach
//    void setUp() {
//        faker = new Faker();
//        mockMvc = MockMvcBuilders.standaloneSetup(measurementController).build();
//    }
//
//    @Test
//    void testGetMeasurementsAll() throws Exception {
//        // Configure the fake data
//        MeasurementView measurementView = new MeasurementView(10.0f, 20.0f); // Substitua pelos valores desejados
//        Page<MeasurementView> page = new PageImpl<>(Collections.singletonList(measurementView));
//        when(measurementService.getMeasurementsAll(any(PageRequest.class))).thenReturn(page);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/measurements/all-measurements")
//                .param("page", "0")
//                .param("size", "10")
//                .param("direction", "ASC")
//                .param("orderBy", "id"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].height").value(10.0f))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].length").value(20.0f));
//    }
//
//
//    @Test
//    void testCreateMeasurementSuccess() throws Exception {
//        // Prepare valid data
//        MeasurementForm measurementForm = new MeasurementForm(1.0f, 1.0f, 12345L);
//        String jsonContent = new ObjectMapper().writeValueAsString(measurementForm);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/measurements/new-measurements")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonContent))
//                .andExpect(status().isCreated());
//
//        verify(measurementService, times(1)).save(measurementForm);
//    }
//
//
//    @Test
//    void testCreateMeasurementValidationError() throws Exception {
//        // Prepare invalid data
//        MeasurementForm measurementForm = new MeasurementForm(0.0f, 0.0f, 12345L);
//        String jsonContent = new ObjectMapper().writeValueAsString(measurementForm);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/measurements/new-measurements")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonContent))
//                .andExpect(status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].defaultMessage").value("Height is required!"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[1].defaultMessage").value("Length is required!"));
//    }
//
//
//    @Test
//    void testUpdateMeasurement() throws Exception {
//        Long id = 12345L; // Substitua pelo valor desejado
//        MeasurementForm measurementForm = new MeasurementForm(1.0f, 2.0f, id);
//        MeasurementView measurementView = new MeasurementView(1.0f, 2.0f); // Ajuste os dados conforme necessário
//
//        when(measurementService.updateMeasurement(eq(id), any(MeasurementForm.class))).thenReturn(measurementView);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/measurements/{id}", id)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(measurementForm)))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.height").value(1.0f))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length").value(2.0f));
//    }
//
//}
