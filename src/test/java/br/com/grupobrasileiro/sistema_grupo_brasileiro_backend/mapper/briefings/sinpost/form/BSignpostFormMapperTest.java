package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sinpost.form;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.form.BSignpostFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.MaterialRepository;

/**
 * Testa a classe BSignpostFormMapper.
 * Verifica se o mapeamento entre BSignpostForm e BSignpost ocorre conforme esperado.
 */
public class BSignpostFormMapperTest {

    @InjectMocks
    private BSignpostFormMapper bSignpostFormMapper;

    @Mock
    private MaterialRepository materialRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o mapeamento de um BSignpostForm válido para um BSignpost.
     * Verifica se as propriedades são mapeadas corretamente e se as propriedades não mapeadas são nulas.
     */
    @Test
    @DisplayName("Should return a BSignpost with null properties when mapping a valid BSignpostForm")
    void BSignpostQuandoMapearBSignpostFormValido() {
        // Dados de teste
        BSignpostForm signpostForm = new BSignpostForm(
                1L, // idMaterial (não usado no mapper, mas incluído para alinhamento com o DTO)
                "Localização da Placa",
                "Setor"
        );

        // Mapeamento
        BSignpost result = bSignpostFormMapper.map(signpostForm);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull(); // Verifica se o ID é null
        assertThat(result.getMaterial()).isNull(); // Verifica se o material é null
        assertThat(result.getBriefing()).isNull(); // Verifica se o briefing é null
        assertThat(result.getBoardLocation()).isEqualTo(signpostForm.boardLocation()); // Verifica se boardLocation está correto
        assertThat(result.getSector()).isEqualTo(signpostForm.sector()); // Verifica se o sector está correto
    }

    /**
     * Testa que o método map lança uma exceção ao tentar mapear um BSignpostForm nulo.
     */
    @Test
    @DisplayName("Should throw exception when mapping null BSignpostForm")
    void shouldThrowExceptionWhenMappingNullBSignpostForm() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> bSignpostFormMapper.map(null), 
            "Mapping should throw a NullPointerException for null BSignpostForm");
    }

    /**
     * Testa o mapeamento de um BSignpostForm com campos vazios.
     * Verifica se os valores mapeados correspondem aos valores fornecidos, que devem ser nulos ou vazios.
     */
    @Test
    @DisplayName("Should map BSignpostForm with empty fields correctly")
    void shouldMapBSignpostFormWithEmptyFields() {
        // Dados de teste
        BSignpostForm signpostForm = new BSignpostForm(
                1L,
                "", // Localização vazia
                ""  // Setor vazio
        );

        // Mapeamento
        BSignpost result = bSignpostFormMapper.map(signpostForm);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isEqualTo(""); // Verifica se boardLocation está vazio
        assertThat(result.getSector()).isEqualTo(""); // Verifica se o sector está vazio
    }

    /**
     * Testa o mapeamento de um BSignpostForm com valores nulos.
     * Verifica se os valores mapeados correspondem a nulos quando os campos no DTO são nulos.
     */
    @Test
    @DisplayName("Should map BSignpostForm with null fields correctly")
    void shouldMapBSignpostFormWithNullFields() {
        // Dados de teste
        BSignpostForm signpostForm = new BSignpostForm(
                1L,
                null, // Localização nula
                null  // Setor nulo
        );

        // Mapeamento
        BSignpost result = bSignpostFormMapper.map(signpostForm);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isNull(); // Verifica se boardLocation está nulo
        assertThat(result.getSector()).isNull(); // Verifica se o sector está nulo
    }
}
