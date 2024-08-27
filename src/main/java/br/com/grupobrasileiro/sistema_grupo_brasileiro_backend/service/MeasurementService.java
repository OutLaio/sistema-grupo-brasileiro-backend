package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.MeasurementForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.MeasurementView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.MeasurementFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.MeasurementViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Measurement;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.MeasurementsRepository;

@Service
public class MeasurementService {
	
	@Autowired
	private MeasurementsRepository measurementsRepository;
	
	@Autowired
	private MeasurementFormMapper measurementFormMapper;
	
	@Autowired
	private MeasurementViewMapper measurementViewMapper;
	
	@Transactional
    public void save(MeasurementForm measurementForm) {
		Measurement measurement = measurementFormMapper.map(measurementForm);
        measurementsRepository.save(measurement);   
	}
	
	@Transactional(readOnly = true)
	public Page<MeasurementView> getMeasurementsAll(PageRequest pageRequest) {
		Page<Measurement> measurementPage = measurementsRepository.findAll(pageRequest);
	    return measurementPage.map(measurementViewMapper::map);
	}
	
	@Transactional(readOnly = true)
	public List<MeasurementView> getMeasurementsAgencyBoards(Long id) {
	    return measurementsRepository.findAll().stream()
	        .filter(measurement -> measurement.getBAgencyBoard().getId().equals(id))
	        .map(measurement -> measurementViewMapper.map(measurement))
	        .collect(Collectors.toList());
	}
	
    @Transactional(readOnly = true)
    public MeasurementView getMeasurementById(Long id) {
    	Measurement measurement = measurementsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Measurement não encontrado"));
        return measurementViewMapper.map(measurement);
    }
    
    @Transactional(readOnly = true)
    public MeasurementView updateMeasurement(Long id, MeasurementForm measurementForm) {
    	Measurement measurement = measurementsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Measurement não encontrado"));
    	
    	measurement.setHeight(measurementForm.height());
    	measurement.setLength(measurementForm.length());
    	
        return measurementViewMapper.map(measurement);
    }
	    
}









