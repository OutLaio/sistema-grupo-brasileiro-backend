package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.BAgencyBoardForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateBAgencyBoardForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.BAgencyBoardFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.BAgencyBoardViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.BAgencyBoardRepository;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class BAgencyBoardServiceTest {

    @Mock
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Mock
    private BAgencyBoardFormMapper bAgencyBoardFormMapper;

    @Mock
    private BAgencyBoardViewMapper bAgencyBoardViewMapper;

    @InjectMocks
    private BAgencyBoardService bAgencyBoardService;

    private BAgencyBoard bAgencyBoard;
    private BAgencyBoardForm bAgencyBoardForm;
    private BAgencyBoardView bAgencyBoardView;
    private UpdateBAgencyBoardForm updateBAgencyBoardForm;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setId(1L);
        bAgencyBoard.setBoardLocation("Location");
        bAgencyBoard.setBoardType("Type");
        bAgencyBoard.setMaterial("Material");
        bAgencyBoard.setObservations("Observations");
        bAgencyBoard.setCompanySharing(true);

        bAgencyBoardForm = new BAgencyBoardForm(
            "Location",
            true,
            "Type",
            "Material",
            "Observations",
            1L
        );

        bAgencyBoardView = new BAgencyBoardView(
            1L,
            "Location",
            true,
            "Type",
            "Material",
            "Observations",
            1L
        );

        updateBAgencyBoardForm = new UpdateBAgencyBoardForm(
            "New Location",
            false,
            "New Type",
            "New Material",
            "New Observations"
        );
    }

    @Test
    @Transactional
    public void testSave() {
        when(bAgencyBoardFormMapper.map(any(BAgencyBoardForm.class))).thenReturn(bAgencyBoard);
        when(bAgencyBoardRepository.save(any(BAgencyBoard.class))).thenReturn(bAgencyBoard);

        bAgencyBoardService.save(bAgencyBoardForm);

        verify(bAgencyBoardFormMapper, times(1)).map(bAgencyBoardForm);
        verify(bAgencyBoardRepository, times(1)).save(bAgencyBoard);
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetBAgencyBoardById() {
        when(bAgencyBoardRepository.findById(anyLong())).thenReturn(java.util.Optional.of(bAgencyBoard));
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);

        BAgencyBoardView result = bAgencyBoardService.getBAgencyBoardById(1L);

        assertThat(result).isEqualTo(bAgencyBoardView);
        verify(bAgencyBoardRepository, times(1)).findById(1L);
        verify(bAgencyBoardViewMapper, times(1)).map(bAgencyBoard);
    }

    @Test
    @Transactional
    public void testUpdateBAgencyBoard() {
        when(bAgencyBoardRepository.findById(anyLong())).thenReturn(java.util.Optional.of(bAgencyBoard));
        when(bAgencyBoardRepository.save(any(BAgencyBoard.class))).thenReturn(bAgencyBoard);
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);

        BAgencyBoardView result = bAgencyBoardService.updateBAgencyBoard(1L, updateBAgencyBoardForm);

        assertThat(result).isEqualTo(bAgencyBoardView);
        verify(bAgencyBoardRepository, times(1)).findById(1L);
        verify(bAgencyBoardRepository, times(1)).save(any(BAgencyBoard.class));
        verify(bAgencyBoardViewMapper, times(1)).map(bAgencyBoard);
    }

    @Test
    @Transactional
    public void testBAgencyBoardAll() {
        Page<BAgencyBoard> page = new PageImpl<>(java.util.Collections.singletonList(bAgencyBoard));
        when(bAgencyBoardRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);

        Page<BAgencyBoardView> result = bAgencyBoardService.bAgencyBoardAll(PageRequest.of(0, 10));

        assertThat(result).isNotNull();
        assertThat(result.getContent()).containsExactly(bAgencyBoardView);
        verify(bAgencyBoardRepository, times(1)).findAll(any(PageRequest.class));
        verify(bAgencyBoardViewMapper, times(1)).map(bAgencyBoard);
    }

    @Test
    @Transactional
    public void testSaveWithException() {
        when(bAgencyBoardFormMapper.map(any(BAgencyBoardForm.class))).thenThrow(new RuntimeException("Mapper error"));

        assertThrows(RuntimeException.class, () -> bAgencyBoardService.save(bAgencyBoardForm));
        verify(bAgencyBoardFormMapper, times(1)).map(bAgencyBoardForm);
        verify(bAgencyBoardRepository, times(0)).save(any(BAgencyBoard.class));
    }

    @Test
    @Transactional
    public void testUpdateBAgencyBoardNotFound() {
        when(bAgencyBoardRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bAgencyBoardService.updateBAgencyBoard(1L, updateBAgencyBoardForm));
        verify(bAgencyBoardRepository, times(1)).findById(1L);
        verify(bAgencyBoardRepository, times(0)).save(any(BAgencyBoard.class));
    }

    @Test
    @Transactional
    public void testSaveWithInvalidData() {
        
        BAgencyBoardForm invalidForm = new BAgencyBoardForm(
            null, // boardLocation é nulo
            true,
            "Type",
            "Material",
            "Observations",
            1L
        );

        when(bAgencyBoardFormMapper.map(any(BAgencyBoardForm.class))).thenReturn(bAgencyBoard);

         assertThrows(ConstraintViolationException.class, () -> bAgencyBoardService.save(invalidForm));
        verify(bAgencyBoardFormMapper, times(1)).map(invalidForm);
        verify(bAgencyBoardRepository, times(0)).save(any(BAgencyBoard.class));
    }
}
