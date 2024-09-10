//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CompanyForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.CompanyView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ItineraryView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.CompanyService;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/api/v1/companies")
//public class CompanyController {
//
//    @Autowired
//    private CompanyService companyService;
//
//    @PostMapping("/new")
//    public ResponseEntity<CompanyView> save(@Valid @RequestBody CompanyForm body) {
//    	CompanyView companyView = companyService.save(body);
//    	return ResponseEntity.status(HttpStatus.CREATED).body(companyView);
//
//    }
//
//    @Cacheable("all")
//    @GetMapping("/all")
//    public ResponseEntity<Page<CompanyView>> companyAll(
//        @RequestParam(defaultValue = "0") Integer page,
//        @RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
//        @RequestParam(value = "orderBy", defaultValue = "name" ) String orderBy,
//        @RequestParam(defaultValue = "10") int size) {
//
//        PageRequest pageRequest  = PageRequest.of(page, size, Direction.valueOf(direction),  orderBy);
//        Page<CompanyView> companyViews = companyService.companyAll(pageRequest);
//        return ResponseEntity.ok(companyViews);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CompanyView> getCompanyById(@PathVariable Long id) {
//        CompanyView companyView = companyService.getCompanyById(id);
//        return ResponseEntity.ok(companyView);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CompanyView> updateCompany(@PathVariable Long id, @RequestBody @Validated CompanyForm form) {
//        CompanyView updatedCompanyView = companyService.updateCompany(id, form);
//        return ResponseEntity.ok(updatedCompanyView);
//    }
//
//}
