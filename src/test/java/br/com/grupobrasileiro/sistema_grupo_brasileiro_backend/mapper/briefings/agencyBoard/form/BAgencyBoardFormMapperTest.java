package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;

/**
 * Testa a classe BAgencyBoardFormMapper.
 * Verifica se a classe mapeia corretamente instâncias de BAgencyBoardsForm para BAgencyBoard.
 */
public class BAgencyBoardFormMapperTest {

    private BAgencyBoardFormMapper mapper;
    private Faker faker;

    /**
     * Configura o ambiente de teste antes de cada teste.
     * Inicializa o mapeador e o Faker.
     */
    @BeforeEach
    void setUp() {
        mapper = new BAgencyBoardFormMapper();
        faker = new Faker();
    }

    /**
     * Testa o método map da classe BAgencyBoardFormMapper.
     * Verifica se o método mapeia corretamente um BAgencyBoardsForm para um BAgencyBoard.
     */
    @Test
    @DisplayName("Deve mapear BAgencyBoardsForm para BAgencyBoard corretamente")
    void deveMapearBAgencyBoardsFormParaBAgencyBoard() {
        // Gera dados fictícios para os campos do formulário usando o Java Faker
        String localizacaoFalsa = faker.address().streetAddress();
        String observacaoFalsa = faker.lorem().sentence();

        // Cria uma instância de BAgencyBoardsForm com os dados gerados
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            null, // idAgencyBoardType, pode ser null se não for utilizado
            null, // idBoardType, pode ser null se não for utilizado
            localizacaoFalsa, // Localização do quadro
            observacaoFalsa, // Observações
            new ArrayList<>(), // Lista de outras rotas, pode ser uma lista vazia se não for utilizado
            new ArrayList<>()  // Lista de rotas, pode ser uma lista vazia se não for utilizado
        );

        // Mapeia o BAgencyBoardsForm para BAgencyBoard
        BAgencyBoard result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se a localização do quadro foi mapeada corretamente
        assertThat(result.getBoardLocation()).isEqualTo(localizacaoFalsa);

        // Verifica se as observações foram mapeadas corretamente
        assertThat(result.getObservations()).isEqualTo(observacaoFalsa);
    }
    
    /**
     * Testa o método map da classe BAgencyBoardFormMapper com valores nulos.
     * Verifica se o método mapeia corretamente um BAgencyBoardsForm com valores nulos para um BAgencyBoard.
     */
    @Test
    @DisplayName("Deve mapear BAgencyBoardsForm com valores nulos para BAgencyBoard corretamente")
    void deveMapearBAgencyBoardsFormComValoresNulosParaBAgencyBoard() {
        // Cria uma instância de BAgencyBoardsForm com valores nulos para campos não obrigatórios
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            null, // idAgencyBoardType
            null, // idBoardType
            null, // Localização do quadro
            null, // Observações
            new ArrayList<>(), // Lista de outras rotas
            new ArrayList<>()  // Lista de rotas
        );

        // Mapeia o BAgencyBoardsForm para BAgencyBoard
        BAgencyBoard result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente, incluindo valores nulos
        assertThat(result.getBoardLocation()).isNull();
        assertThat(result.getObservations()).isNull();
    }
}
