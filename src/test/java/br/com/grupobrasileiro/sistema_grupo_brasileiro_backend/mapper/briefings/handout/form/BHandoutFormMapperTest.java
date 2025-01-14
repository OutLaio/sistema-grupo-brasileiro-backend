package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.handout.HandoutTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.BHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form.BHandoutFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.HandoutType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BHandoutFormMapperTest {

    @InjectMocks
    private BHandoutFormMapper bHandoutFormMapper;

    @Mock
    private HandoutTypeRepository handoutTypeRepository;

    private BHandoutForm validBHandoutForm;

    private HandoutType validHandoutType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criação de um objeto válido para o teste
        validBHandoutForm = new BHandoutForm(1L);
        validHandoutType = new HandoutType(1L, "Valid Handout Type");
    }

    @Test
    void shouldMapBHandoutFormToBHandoutSuccessfully() {
        // Simulando o comportamento do repositório para retornar um HandoutType válido
        when(handoutTypeRepository.findById(validBHandoutForm.idHandoutType()))
            .thenReturn(Optional.of(validHandoutType));

        // Executando o mapeamento
        BHandout result = bHandoutFormMapper.map(validBHandoutForm);

        // Verificando se o resultado é não nulo
        assertNotNull(result);

        // Verificando se o campo `handoutType` foi mapeado corretamente
        assertEquals(validHandoutType, result.getHandoutType());

        // Verificando se os outros campos são mapeados corretamente (neste caso, eles são `null`).
        assertNull(result.getId());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenHandoutTypeIsNotFound() {
        // Simulando o comportamento do repositório para retornar um Optional vazio
        when(handoutTypeRepository.findById(validBHandoutForm.idHandoutType()))
            .thenReturn(Optional.empty());

        // Executando o mapeamento e verificando se a exceção é lançada
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> bHandoutFormMapper.map(validBHandoutForm)
        );

        // Verificando a mensagem da exceção
        assertEquals("Handout Type not found for ID: 1", exception.getMessage());
    }
}
