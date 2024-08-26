package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.BAgencyBoardForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.MeasurementForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateBAgencyBoardForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.MeasurementView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.BAgencyBoardService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.MeasurementService;

@RestController
@RequestMapping("/api/v1/b-agency-boards")
public class BAgencyBoardController {

    @Autowired
    private BAgencyBoardService bAgencyBoardService;
    
    @Autowired
    private MeasurementService measurementService;
    
	
    // Endpoint para salvar um novo BAgencyBoard
    @PostMapping
    public ResponseEntity<Void> createBAgencyBoard(@RequestBody BAgencyBoardForm bAgencyBoardForm) {
        bAgencyBoardService.save(bAgencyBoardForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Endpoint para obter um BAgencyBoard por ID
    @GetMapping("/{id}")
    public ResponseEntity<BAgencyBoardView> getBAgencyBoardById(@PathVariable Long id) {
        BAgencyBoardView bAgencyBoardView = bAgencyBoardService.getBAgencyBoardById(id);
        return ResponseEntity.ok(bAgencyBoardView);
    }

    // Endpoint para atualizar um BAgencyBoard
    @PutMapping("/{id}")
    public ResponseEntity<BAgencyBoardView> updateBAgencyBoard(@PathVariable Long id, @RequestBody UpdateBAgencyBoardForm form) {
        BAgencyBoardView updatedBAgencyBoard = bAgencyBoardService.updateBAgencyBoard(id, form);
        return ResponseEntity.ok(updatedBAgencyBoard);
    }

    // Endpoint para listar todos os BAgencyBoards com paginação
    @GetMapping
    public ResponseEntity<Page<BAgencyBoardView>> listBAgencyBoards(@RequestParam(defaultValue = "0") Integer page, 
                                                                    @RequestParam(defaultValue = "10") Integer size,
                                                              		@RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
                                                            		@RequestParam(value = "orderBy", defaultValue = "name" ) String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BAgencyBoardView> bAgencyBoardPage = bAgencyBoardService.bAgencyBoardAll(pageRequest);
        return ResponseEntity.ok(bAgencyBoardPage);
    }
    
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
    public ResponseEntity<Void> createMeasurement(@RequestBody MeasurementForm measurementForm) {
    	measurementService.save(measurementForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    
    @GetMapping("/measurements-id-agencyBoards/{id}")
    public ResponseEntity<List<MeasurementView>> getMeasurementsIdAgencyBoards(@PathVariable Long id) {
    	List<MeasurementView> measurementViews = measurementService.getMeasurementsAgencyBoards(id);
        return ResponseEntity.ok(measurementViews);
    }
    
            
    
}
