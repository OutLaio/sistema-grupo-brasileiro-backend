//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.BAgencyBoardForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateBAgencyBoardForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.BAgencyBoardView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.BAgencyBoardService;
//
//
//@RestController
//@RequestMapping("/api/v1/b-agency-boards")
//public class BAgencyBoardController {
//
//    @Autowired
//    private BAgencyBoardService bAgencyBoardService;
//
//    // Endpoint para salvar um novo BAgencyBoard
//    @PostMapping
//    public ResponseEntity<Void> createBAgencyBoard(@RequestBody BAgencyBoardForm bAgencyBoardForm) {
//        bAgencyBoardService.save(bAgencyBoardForm);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    // Endpoint para obter um BAgencyBoard por ID
//    @GetMapping("/{id}")
//    public ResponseEntity<BAgencyBoardView> getBAgencyBoardById(@PathVariable Long id) {
//        BAgencyBoardView bAgencyBoardView = bAgencyBoardService.getBAgencyBoardById(id);
//        return ResponseEntity.ok(bAgencyBoardView);
//    }
//
//    // Endpoint para atualizar um BAgencyBoard
//    @PutMapping("/{id}")
//    public ResponseEntity<BAgencyBoardView> updateBAgencyBoard(@PathVariable Long id, @RequestBody UpdateBAgencyBoardForm form) {
//        BAgencyBoardView updatedBAgencyBoard = bAgencyBoardService.updateBAgencyBoard(id, form);
//        return ResponseEntity.ok(updatedBAgencyBoard);
//    }
//
//    // Endpoint para listar todos os BAgencyBoards com paginação
//    @GetMapping
//    public ResponseEntity<Page<BAgencyBoardView>> listBAgencyBoards(@RequestParam(defaultValue = "0") Integer page,
//                                                                    @RequestParam(defaultValue = "10") Integer size,
//                                                              		@RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
//                                                            		@RequestParam(value = "orderBy", defaultValue = "name" ) String orderBy) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        Page<BAgencyBoardView> bAgencyBoardPage = bAgencyBoardService.bAgencyBoardAll(pageRequest);
//        return ResponseEntity.ok(bAgencyBoardPage);
//    }
//
//}
