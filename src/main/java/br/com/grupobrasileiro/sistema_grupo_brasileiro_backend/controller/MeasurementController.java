package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.MeasurementForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.MeasurementView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.MeasurementService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/measurements")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;
    
    @Cacheable("all-measurements")
    @GetMapping("/all-measurements")
    public ResponseEntity<Page<MeasurementView>> measurementAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
            @RequestParam(value = "orderBy", defaultValue = "id" ) String orderBy,
            @RequestParam(defaultValue = "10") int size,
            Integer Id) {
    	
        PageRequest pageRequest  = PageRequest.of(page, size, Direction.valueOf(direction),  orderBy);
        Page<MeasurementView> measurementViews = measurementService.getMeasurementsAll(pageRequest);
        return ResponseEntity.ok(measurementViews);
    }
    
    @PostMapping("/new-measurements")
    public ResponseEntity<Void> createMeasurement(@Valid @RequestBody MeasurementForm measurementForm) {
    	measurementService.save(measurementForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    
    @PutMapping("/measurements/{id}")
    public ResponseEntity<MeasurementView> updateItinerary(@PathVariable Long id, @Valid @RequestBody MeasurementForm measurementForm) {
        MeasurementView updatedMeasurementView = measurementService.updateMeasurement(id, measurementForm);
        return ResponseEntity.ok(updatedMeasurementView);
    }

}
