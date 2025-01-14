package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.form;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.form.BStickerForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sticker.form.BStickerFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.BSticker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerInformationType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.sticker.StickerInformationTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.sticker.StickerTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BStickerFormMapperTest {

    @Mock
    private StickerTypeRepository stickerTypeRepository;

    @Mock
    private StickerInformationTypeRepository stickerInformationTypeRepository;

    @InjectMocks
    private BStickerFormMapper bStickerFormMapper;

    private BStickerForm validStickerForm;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validStickerForm = new BStickerForm(1L, 2L, "Marketing", "Observação adicional");
    }

    @Test
    void testMapToBSticker() {
        // Mockando os repositórios
        when(stickerTypeRepository.findById(1L)).thenReturn(java.util.Optional.of(new StickerType()));
        when(stickerInformationTypeRepository.findById(2L)).thenReturn(java.util.Optional.of(new StickerInformationType()));

        // Executando o mapeamento
        BSticker bSticker = bStickerFormMapper.map(validStickerForm);

        // Verificando que o mapeamento foi feito corretamente
        assertNotNull(bSticker);
        assertEquals("Marketing", bSticker.getSector());
        assertEquals("Observação adicional", bSticker.getObservations());
    }

    @Test
    void testMapToBStickerStickerTypeNotFound() {
        // Mockando que o StickerType não foi encontrado
        when(stickerTypeRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Verificando que uma exceção é lançada quando não encontra o StickerType
        assertThrows(EntityNotFoundException.class, () -> bStickerFormMapper.map(validStickerForm));
    }

    @Test
    void testMapToBStickerStickerInformationTypeNotFound() {
        // Mockando que o StickerInformationType não foi encontrado
        when(stickerTypeRepository.findById(1L)).thenReturn(java.util.Optional.of(new StickerType()));
        when(stickerInformationTypeRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        // Verificando que uma exceção é lançada quando não encontra o StickerInformationType
        assertThrows(EntityNotFoundException.class, () -> bStickerFormMapper.map(validStickerForm));
    }
}
