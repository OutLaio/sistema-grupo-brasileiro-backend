package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.MeasurementForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.MeasurementView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.MeasurementFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.MeasurementViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Measurement;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.MeasurementsRepository;

public class MeasurementServiceTest {

    @InjectMocks
    private MeasurementService measurementService;

    @Mock
    private MeasurementsRepository measurementsRepository;

    @Mock
    private MeasurementFormMapper measurementFormMapper;

    @Mock
    private MeasurementViewMapper measurementViewMapper;

    private MeasurementForm measurementForm;
    private Measurement measurement;
    private MeasurementView measurementView;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        measurementForm = new MeasurementForm(10.0f, 20.0f, 1L);
        measurement = new Measurement(1L, 10.0f, 20.0f, null);
        measurementView = new MeasurementView(10.0f, 20.0f);
    }

    @Test
    void testSave() {
        when(measurementFormMapper.map(any(MeasurementForm.class))).thenReturn(measurement);
        when(measurementsRepository.save(any(Measurement.class))).thenReturn(measurement);

        measurementService.save(measurementForm);

        verify(measurementFormMapper).map(measurementForm);
        verify(measurementsRepository).save(measurement);
    }

    @Test
    void testGetMeasurementsAll() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Measurement> measurementPage = new PageImpl<>(Collections.singletonList(measurement));
        when(measurementsRepository.findAll(any(PageRequest.class))).thenReturn(measurementPage);
        when(measurementViewMapper.map(any(Measurement.class))).thenReturn(measurementView);

        Page<MeasurementView> result = measurementService.getMeasurementsAll(pageRequest);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(measurementView);
    }

    @Test
    void testGetMeasurementsAgencyBoards() {
        when(measurementsRepository.findAll()).thenReturn(Collections.singletonList(measurement));
        when(measurementViewMapper.map(any(Measurement.class))).thenReturn(measurementView);

        var result = measurementService.getMeasurementsAgencyBoards(1L);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isEqualTo(measurementView);
    }

    @Test
    void testGetMeasurementById() {
        when(measurementsRepository.findById(anyLong())).thenReturn(Optional.of(measurement));
        when(measurementViewMapper.map(any(Measurement.class))).thenReturn(measurementView);

        MeasurementView result = measurementService.getMeasurementById(1L);

        assertThat(result).isEqualTo(measurementView);
    }

    @Test
    void testGetMeasurementByIdNotFound() {
        when(measurementsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> measurementService.getMeasurementById(1L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Measurement não encontrado");
    }

    @Test
    void testUpdateMeasurement() {
        when(measurementsRepository.findById(anyLong())).thenReturn(Optional.of(measurement));
        when(measurementViewMapper.map(any(Measurement.class))).thenReturn(measurementView);

        MeasurementView result = measurementService.updateMeasurement(1L, measurementForm);

        assertThat(result).isEqualTo(measurementView);
        verify(measurementsRepository).findById(1L);
    }

    @Test
    void testUpdateMeasurementNotFound() {
        when(measurementsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> measurementService.updateMeasurement(1L, measurementForm))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Measurement não encontrado");
    }
}
