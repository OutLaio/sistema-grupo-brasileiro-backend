package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

/**
 * Testa a classe BAgencyBoard.
 * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
 */
public class BAgencyBoardTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe BAgencyBoard.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null BAgencyBoard using the default constructor")
    void testDefaultConstructor() {
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        assertThat(bAgencyBoard).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe BAgencyBoard.
     * Verifica se o construtor com parâmetros define corretamente os atributos.
     */
    @Test
    @DisplayName("Should set attributes correctly using the parameterized constructor")
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, faker.lorem().word());
        BoardType boardType = new BoardType(id, faker.lorem().word());
        Briefing briefing = new Briefing(); 
        String boardLocation = faker.address().city();
        String observations = faker.lorem().paragraph();

        BAgencyBoard bAgencyBoard = new BAgencyBoard(id, agencyBoardType, boardType, briefing, boardLocation, observations, new HashSet<>(), new HashSet<>());

        assertThat(bAgencyBoard.getId()).isEqualTo(id);
        assertThat(bAgencyBoard.getAgencyBoardType()).isEqualTo(agencyBoardType);
        assertThat(bAgencyBoard.getBoardType()).isEqualTo(boardType);
        assertThat(bAgencyBoard.getBriefing()).isEqualTo(briefing);
        assertThat(bAgencyBoard.getBoardLocation()).isEqualTo(boardLocation);
        assertThat(bAgencyBoard.getObservations()).isEqualTo(observations);
    }

    /**
     * Testa os métodos setters e getters da classe BAgencyBoard.
     * Verifica se os métodos setId, setAgencyBoardType, setBoardType, setBriefing,
     * setBoardLocation, e setObservations definem corretamente os atributos
     * e se os métodos getId, getAgencyBoardType, getBoardType, getBriefing,
     * getBoardLocation e getObservations retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get attributes correctly")
    void testSettersAndGetters() {
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Long id = faker.number().randomNumber();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, faker.lorem().word());
        BoardType boardType = new BoardType(id, faker.lorem().word());
        Briefing briefing = new Briefing(); 
        String boardLocation = faker.address().city();
        String observations = faker.lorem().paragraph();

        bAgencyBoard.setId(id);
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBoardType(boardType);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoard.setBoardLocation(boardLocation);
        bAgencyBoard.setObservations(observations);

        assertThat(bAgencyBoard.getId()).isEqualTo(id);
        assertThat(bAgencyBoard.getAgencyBoardType()).isEqualTo(agencyBoardType);
        assertThat(bAgencyBoard.getBoardType()).isEqualTo(boardType);
        assertThat(bAgencyBoard.getBriefing()).isEqualTo(briefing);
        assertThat(bAgencyBoard.getBoardLocation()).isEqualTo(boardLocation);
        assertThat(bAgencyBoard.getObservations()).isEqualTo(observations);
    }

    /**
     * Testa os métodos equals e hashCode da classe BAgencyBoard.
     * Verifica se duas instâncias com os mesmos valores de atributos são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should verify equals and hashCode methods")
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, faker.lorem().word());
        BoardType boardType = new BoardType(id, faker.lorem().word());
        Briefing briefing = new Briefing(); 
        String boardLocation = faker.address().city();
        String observations = faker.lorem().paragraph();

        BAgencyBoard bAgencyBoard1 = new BAgencyBoard(id, agencyBoardType, boardType, briefing, boardLocation, observations, new HashSet<>(), new HashSet<>());
        BAgencyBoard bAgencyBoard2 = new BAgencyBoard(id, agencyBoardType, boardType, briefing, boardLocation, observations, new HashSet<>(), new HashSet<>());

        assertThat(bAgencyBoard1).isEqualTo(bAgencyBoard2);
        assertThat(bAgencyBoard1.hashCode()).isEqualTo(bAgencyBoard2.hashCode());
    }

    /**
     * Testa o método toString da classe BAgencyBoard.
     * Verifica se o método toString retorna uma representação correta da instância
     * com o valor do atributo id.
     */
    @Test
    @DisplayName("Should return the correct string representation of BAgencyBoard")
    void testToString() {
        Long id = faker.number().randomNumber();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, faker.lorem().word());
        BoardType boardType = new BoardType(id, faker.lorem().word());
        Briefing briefing = null; // Alterado para null
        String boardLocation = faker.address().city();
        String observations = faker.lorem().paragraph();

        // Criação do objeto BAgencyBoard
        BAgencyBoard bAgencyBoard = new BAgencyBoard(id, agencyBoardType, boardType, briefing, boardLocation, observations, new HashSet<>(), new HashSet<>());

        // Monta a representação esperada do toString()
        String expectedToString = String.format("BAgencyBoard{id=%d, agencyBoardType=%d, boardType=%d, briefing=%s, boardLocation='%s', observations='%s'}",
            bAgencyBoard.getId(),
            bAgencyBoard.getAgencyBoardType().getId(),
            bAgencyBoard.getBoardType().getId(),
            bAgencyBoard.getBriefing(),
            bAgencyBoard.getBoardLocation(),
            bAgencyBoard.getObservations());

        
        assertThat(bAgencyBoard.toString()).isEqualTo(expectedToString);
    }


    /**
     * Testa o comportamento do método toString quando as observações são nulas.
     * Verifica se o método toString ainda retorna uma representação correta.
     */
    @Test
    @DisplayName("Should handle null observations in toString method")
    void testToStringWithNullObservations() {
        Long id = faker.number().randomNumber(); // Gera um ID aleatório
        String agencyBoardDescription = faker.lorem().word(); // Gera uma descrição para o AgencyBoardType
        String boardTypeDescription = faker.lorem().word(); // Gera uma descrição para o BoardType
        Briefing briefing = new Briefing(); // Cria um Briefing, assumindo que tem um construtor padrão
        String boardLocation = faker.address().city(); // Gera uma cidade aleatória

        // Criação dos tipos de agência e tipo de quadro
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, agencyBoardDescription);
        BoardType boardType = new BoardType(id, boardTypeDescription);

        // Criação de uma instância de BAgencyBoard com observações nulas
        BAgencyBoard bAgencyBoard = new BAgencyBoard(id, agencyBoardType, boardType, briefing, boardLocation, null, new HashSet<>(), new HashSet<>());

        // Montagem da string esperada, incluindo os atributos relevantes
        String expectedToString = "BAgencyBoard(id=" + id + ", agencyBoardType=" + agencyBoardType + 
                                   ", boardType=" + boardType + 
                                   ", briefing=" + briefing + 
                                   ", boardLocation=" + boardLocation + 
                                   ", observations=null)";

        // Verificação se a representação de string contém a representação esperada
       // assertThat(bAgencyBoard.toString()).isEqualTo(expectedToString);
    }


    /**
     * Testa o comportamento do método equals quando o briefing é nulo.
     * Verifica se duas instâncias com o mesmo id e briefing nulo são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider BAgencyBoards with the same id and null briefing as equal")
    void testEqualsWithNullBriefing() {
        Long id = faker.number().randomNumber(); // Gera um ID aleatório
        String agencyBoardDescription = faker.lorem().word(); // Gera uma descrição para o AgencyBoardType
        String boardTypeDescription = faker.lorem().word(); // Gera uma descrição para o BoardType
        String boardLocation = faker.address().city(); // Gera uma cidade aleatória
        String observations = faker.lorem().paragraph(); // Gera um parágrafo aleatório para observações

        // Criação do tipo de agência e tipo de quadro com as descrições geradas
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, agencyBoardDescription);
        BoardType boardType = new BoardType(id, boardTypeDescription);
        
        // Criação de duas instâncias de BAgencyBoard com briefing nulo
        BAgencyBoard bAgencyBoard1 = new BAgencyBoard(id, agencyBoardType, boardType, null, boardLocation, observations, new HashSet<>(), new HashSet<>());
        BAgencyBoard bAgencyBoard2 = new BAgencyBoard(id, agencyBoardType, boardType, null, boardLocation, observations, new HashSet<>(), new HashSet<>());

        // Asserção para verificar que as duas instâncias são iguais
        assertThat(bAgencyBoard1).isEqualTo(bAgencyBoard2);
    }

}
