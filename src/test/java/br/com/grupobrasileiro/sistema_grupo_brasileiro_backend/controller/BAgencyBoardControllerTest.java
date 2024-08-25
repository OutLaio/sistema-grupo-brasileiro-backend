package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.BAgencyBoardForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateBAgencyBoardForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.BAgencyBoardService;

class BAgencyBoardControllerTest {

    @Mock
    private BAgencyBoardService bAgencyBoardService;

    @InjectMocks
    private BAgencyBoardController bAgencyBoardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBAgencyBoard() {
        BAgencyBoardForm form = new BAgencyBoardForm(
            "Location A",
            true,
            "Type A",
            "Material A",
            "Some observations",
            1L
        );

        ResponseEntity<Void> response = bAgencyBoardController.createBAgencyBoard(form);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(bAgencyBoardService, times(1)).save(form);
    }

    @Test
    void testGetBAgencyBoardById() {
        Long id = 1L;
        BAgencyBoardView view = new BAgencyBoardView(
            id,
            "Location A",
            true,
            "Type A",
            "Material A",
            "Some observations",
            1L
        );
        when(bAgencyBoardService.getBAgencyBoardById(id)).thenReturn(view);

        ResponseEntity<BAgencyBoardView> response = bAgencyBoardController.getBAgencyBoardById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(view, response.getBody());
        verify(bAgencyBoardService, times(1)).getBAgencyBoardById(id);
    }

    @Test
    void testGetBAgencyBoardByIdNotFound() {
        Long id = 1L;
        when(bAgencyBoardService.getBAgencyBoardById(id)).thenThrow(new RuntimeException("BAgencyBoard not found with id: " + id));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bAgencyBoardController.getBAgencyBoardById(id);
        });

        assertEquals("BAgencyBoard not found with id: " + id, exception.getMessage());
        verify(bAgencyBoardService, times(1)).getBAgencyBoardById(id);
    }

    @Test
    void testUpdateBAgencyBoard() {
        Long id = 1L;
        UpdateBAgencyBoardForm form = new UpdateBAgencyBoardForm(
            "Updated Location",
            false,
            "Updated Type",
            "Updated Material",
            "Updated observations"
        );
        BAgencyBoardView updatedView = new BAgencyBoardView(
            id,
            "Updated Location",
            false,
            "Updated Type",
            "Updated Material",
            "Updated observations",
            1L
        );
        when(bAgencyBoardService.updateBAgencyBoard(id, form)).thenReturn(updatedView);

        ResponseEntity<BAgencyBoardView> response = bAgencyBoardController.updateBAgencyBoard(id, form);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedView, response.getBody());
        verify(bAgencyBoardService, times(1)).updateBAgencyBoard(id, form);
    }

    @Test
    void testUpdateBAgencyBoardNotFound() {
        Long id = 1L;
        UpdateBAgencyBoardForm form = new UpdateBAgencyBoardForm(
            "Updated Location",
            false,
            "Updated Type",
            "Updated Material",
            "Updated observations"
        );
        when(bAgencyBoardService.updateBAgencyBoard(id, form)).thenThrow(new RuntimeException("BAgencyBoard not found with id: " + id));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bAgencyBoardController.updateBAgencyBoard(id, form);
        });

        assertEquals("BAgencyBoard not found with id: " + id, exception.getMessage());
        verify(bAgencyBoardService, times(1)).updateBAgencyBoard(id, form);
    }

    @Test
    void testListBAgencyBoards() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        Page<BAgencyBoardView> viewPage = new PageImpl<>(Collections.singletonList(new BAgencyBoardView(
            1L,
            "Location A",
            true,
            "Type A",
            "Material A",
            "Some observations",
            1L
        )));
        when(bAgencyBoardService.bAgencyBoardAll(pageRequest)).thenReturn(viewPage);

        ResponseEntity<Page<BAgencyBoardView>> response = bAgencyBoardController.listBAgencyBoards(0, 10, "ASC", "name");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(viewPage, response.getBody());
        verify(bAgencyBoardService, times(1)).bAgencyBoardAll(pageRequest);
    }
}

