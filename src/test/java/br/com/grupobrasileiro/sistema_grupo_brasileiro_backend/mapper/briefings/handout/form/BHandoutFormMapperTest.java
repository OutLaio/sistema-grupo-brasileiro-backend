package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.BHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form.BHandoutFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.HandoutType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.handout.HandoutTypeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BHandoutFormMapperTest {

    @InjectMocks
    private BHandoutFormMapper bHandoutFormMapper;

    @Mock
    private HandoutTypeRepository handoutTypeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        HandoutType handoutType = new HandoutType();
        when(handoutTypeRepository.findById(anyLong())).thenReturn(Optional.of(handoutType));
    }

    @Test
    public void testMap() {
        BHandoutForm bHandoutForm = new BHandoutForm(1L);
        BHandout result = bHandoutFormMapper.map(bHandoutForm);

        assertNotNull(result);
        assertNull(result.getId());
        assertNotNull(result.getHandoutType());
        assertNull(result.getBriefing());
    }
}
