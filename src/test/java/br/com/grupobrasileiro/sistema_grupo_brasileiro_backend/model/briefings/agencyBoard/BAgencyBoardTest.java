package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;

public class BAgencyBoardTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe BAgencyBoard.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        assertThat(bAgencyBoard).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe BAgencyBoard.
     * Verifica se o construtor com parâmetros define corretamente os atributos.
     */
    @Test
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, faker.lorem().word());
        BoardType boardType = new BoardType(id, faker.lorem().word());
        Briefing briefing = new Briefing(); // Assumindo que Briefing possui um construtor padrão
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
    void testSettersAndGetters() {
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Long id = faker.number().randomNumber();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, faker.lorem().word());
        BoardType boardType = new BoardType(id, faker.lorem().word());
        Briefing briefing = new Briefing(); // Assumindo que Briefing possui um construtor padrão
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
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, faker.lorem().word());
        BoardType boardType = new BoardType(id, faker.lorem().word());
        Briefing briefing = new Briefing(); // Assumindo que Briefing possui um construtor padrão
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
    void testToString() {
        Long id = faker.number().randomNumber();
        AgencyBoardType agencyBoardType = new AgencyBoardType(id, faker.lorem().word());
        BoardType boardType = new BoardType(id, faker.lorem().word());
        Briefing briefing = new Briefing(); // Assumindo que Briefing possui um construtor padrão
        String boardLocation = faker.address().city();
        String observations = faker.lorem().paragraph();

        BAgencyBoard bAgencyBoard = new BAgencyBoard(id, agencyBoardType, boardType, briefing, boardLocation, observations, new HashSet<>(), new HashSet<>());
        String expectedToString = "BAgencyBoard(id=" + id + ")";
        assertThat(bAgencyBoard.toString()).contains(expectedToString);
    }
}
