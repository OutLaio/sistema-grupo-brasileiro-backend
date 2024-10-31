package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.BHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;

public class BHandoutFormMapperTest {

    @InjectMocks
    private BHandoutFormMapper bHandoutFormMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMap() {
        // Criando um objeto BHandoutForm para testar
        BHandoutForm bHandoutForm = new BHandoutForm(1L); // Definindo o ID do tipo de handout

        // Mapeando BHandoutForm para BHandout
        BHandout result = bHandoutFormMapper.map(bHandoutForm);

        // Verificando se o resultado não é nulo
        assertNotNull(result, "O resultado não deve ser nulo");
        
        // Verificando se o ID do BHandout é nulo, já que o método map não o inicializa
        assertNull(result.getId(), "O ID deve ser nulo");
        assertNull(result.getHandoutType(), "O tipo de handout deve ser nulo");
        assertNull(result.getBriefing(), "O briefing deve ser nulo");
    }
}