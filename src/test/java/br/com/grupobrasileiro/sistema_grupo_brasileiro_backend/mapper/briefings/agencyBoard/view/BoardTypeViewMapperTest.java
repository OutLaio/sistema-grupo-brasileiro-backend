package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.BoardTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;

/**
 * Testa a classe BoardTypeViewMapper.
 * Verifica se o mapeamento lida com BoardType nulo corretamente.
 */
public class BoardTypeViewMapperTest {

    @InjectMocks
    private BoardTypeViewMapper boardTypeViewMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker(); // Inicializa o Faker
    }

    /**
     * Testa o mapeamento de BoardType para BoardTypeView.
     * Verifica se um BoardType é corretamente mapeado para um BoardTypeView.
     */
    @Test
    void deveMapearBoardTypeParaBoardTypeView() {
        // Dados de teste usando Faker
        BoardType boardType = new BoardType();
        boardType.setId(faker.number().randomNumber());
        boardType.setDescription(faker.lorem().word());

        // Mapeamento
        BoardTypeView result = boardTypeViewMapper.map(boardType);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(boardType.getId());
        assertThat(result.description()).isEqualTo(boardType.getDescription());
    }

    /**
     * Testa que o método map retorna null ao receber BoardType nulo.
     */
    @Test
    void deveRetornarNullParaBoardTypeNulo() {
        BoardTypeView result = boardTypeViewMapper.map(null);
        assertThat(result).isNull();
    }

    /**
     * Testa o mapeamento de BoardType com campos nulos para BoardTypeView.
     * Verifica se o método map lida corretamente com campos nulos no BoardType.
     */
    @Test
    void deveMapearBoardTypeComCamposNulosParaBoardTypeView() {
        BoardType boardType = new BoardType();
        boardType.setId(null);
        boardType.setDescription(null);

        // Mapeamento
        BoardTypeView result = boardTypeViewMapper.map(boardType);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull();
        assertThat(result.description()).isNull();
    }
}
