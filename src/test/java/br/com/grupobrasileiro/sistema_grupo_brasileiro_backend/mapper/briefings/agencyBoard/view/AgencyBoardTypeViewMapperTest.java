package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testa a classe AgencyBoardTypeViewMapper.
 * Verifica o mapeamento de AgencyBoardType para AgencyBoardTypeView.
 */
@ExtendWith(MockitoExtension.class)
public class AgencyBoardTypeViewMapperTest {

    private final Faker faker = new Faker();

    @InjectMocks
    private AgencyBoardTypeViewMapper mapper;

    /**
     * Testa o método map da classe AgencyBoardTypeViewMapper.
     * Verifica se o método mapeia corretamente um AgencyBoardType para um AgencyBoardTypeView.
     */
    @Test
    @DisplayName("Should map AgencyBoardType to AgencyBoardTypeView correctly")
    void shouldMap() {
        // Gerando dados de teste utilizando o Java Faker
        Long fakeId = faker.number().randomNumber();
        String fakeDescription = faker.lorem().sentence();

        // Criando um AgencyBoardType com valores falsos
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setId(fakeId);
        agencyBoardType.setDescription(fakeDescription);

        // Mapeia o AgencyBoardType para AgencyBoardTypeView
        AgencyBoardTypeView result = mapper.map(agencyBoardType);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se o campo id foi mapeado corretamente
        assertThat(result.id()).isEqualTo(fakeId);

        // Verifica se o campo description foi mapeado corretamente
        assertThat(result.description()).isEqualTo(fakeDescription);
    }

    /**
     * Testa o método map da classe AgencyBoardTypeViewMapper com dados nulos.
     * Verifica se o método mapeia corretamente um AgencyBoardType com valores nulos para um AgencyBoardTypeView.
     */
    @Test
    @DisplayName("Should map AgencyBoardType with null values to AgencyBoardTypeView correctly")
    void MapWithNulls() {
        // Criando um AgencyBoardType com valores nulos
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setId(null);
        agencyBoardType.setDescription(null);

        // Mapeia o AgencyBoardType para AgencyBoardTypeView
        AgencyBoardTypeView result = mapper.map(agencyBoardType);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente com valores nulos
        assertThat(result.id()).isNull();
        assertThat(result.description()).isNull();
    }
    
    /**
     * Testa o método map da classe AgencyBoardTypeViewMapper com valores padrão.
     * Verifica se o método mapeia corretamente um AgencyBoardType com valores padrão para um AgencyBoardTypeView.
     */
    @Test
    @DisplayName("Should map AgencyBoardType with default values to AgencyBoardTypeView correctly")
    void MapWithDefaults() {
        // Criando um AgencyBoardType com valores padrão
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setId(1L); // Supondo um valor padrão
        agencyBoardType.setDescription("Descrição padrão"); // Supondo um valor padrão

        // Mapeia o AgencyBoardType para AgencyBoardTypeView
        AgencyBoardTypeView result = mapper.map(agencyBoardType);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se o campo id foi mapeado corretamente
        assertThat(result.id()).isEqualTo(1L);

        // Verifica se o campo description foi mapeado corretamente
        assertThat(result.description()).isEqualTo("Descrição padrão");
    }

    /**
     * Testa o método map da classe AgencyBoardTypeViewMapper com campos em branco.
     * Verifica se o método lida corretamente com campos em branco no AgencyBoardType.
     */
    @Test
    @DisplayName("Should handle blank fields in AgencyBoardType")
    void shouldHandleBlanks() {
        // Criando um AgencyBoardType com campos em branco
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setId(0L); // id em branco (ou 0 como placeholder)
        agencyBoardType.setDescription(""); // descrição em branco

        // Mapeia o AgencyBoardType para AgencyBoardTypeView
        AgencyBoardTypeView result = mapper.map(agencyBoardType);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente
        assertThat(result.id()).isEqualTo(0L);
        assertThat(result.description()).isEqualTo("");
    }

    /**
     * Testa o método map da classe AgencyBoardTypeViewMapper com caracteres especiais.
     * Verifica se o método lida corretamente com caracteres especiais nas strings.
     */
    @Test
    @DisplayName("Should handle special characters")
    void HandleSpecialChars() {
        String specialDescription = "Desc&%$#@!";

        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setId(1L); // id válido
        agencyBoardType.setDescription(specialDescription); // descrição com caracteres especiais

        AgencyBoardTypeView result = mapper.map(agencyBoardType);

        assertThat(result).isNotNull();
        assertThat(result.description()).isEqualTo(specialDescription);
    }

    /**
     * Testa o método map da classe AgencyBoardTypeViewMapper com valores máximos de caracteres.
     * Verifica se o método lida corretamente com strings de comprimento máximo.
     */
    @Test
    @DisplayName("Should handle maximum character values")
    void HandleMaxChars() {
        String maxDescription = faker.lorem().characters(255); 

        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setId(1L); 
        agencyBoardType.setDescription(maxDescription); 

        AgencyBoardTypeView result = mapper.map(agencyBoardType);

        assertThat(result).isNotNull();
        assertThat(result.description()).isEqualTo(maxDescription);
    }
}
