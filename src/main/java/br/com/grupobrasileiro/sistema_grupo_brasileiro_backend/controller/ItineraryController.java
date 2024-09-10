//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ItineraryForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ItineraryView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.ItineraryService;
//
//@RestController
//@RequestMapping("/api/v1/itineraries")
//public class ItineraryController {
//
//    @Autowired
//    private ItineraryService itineraryService;
//
//    @PostMapping
//    public ResponseEntity<Void> createItinerary(@RequestBody @Validated ItineraryForm itineraryForm) {
//        itineraryService.save(itineraryForm);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ItineraryView> getItineraryById(@PathVariable Long id) {
//        ItineraryView itineraryView = itineraryService.getItineraryById(id);
//        return ResponseEntity.ok(itineraryView);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ItineraryView> updateItinerary(@PathVariable Long id, @RequestBody @Validated ItineraryForm form) {
//        ItineraryView updatedItineraryView = itineraryService.updateItinerary(id, form);
//        return ResponseEntity.ok(updatedItineraryView);
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<ItineraryView>> getAllItineraries(@RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "10") Integer size,
//      		@RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
//    		@RequestParam(value = "orderBy", defaultValue = "name" ) String orderBy) {
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//        Page<ItineraryView> itinerariesPage = itineraryService.getAllItineraries(pageRequest);
//        return ResponseEntity.ok(itinerariesPage);
//    }
//}
