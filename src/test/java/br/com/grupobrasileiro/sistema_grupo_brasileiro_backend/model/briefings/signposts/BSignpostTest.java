package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

/**
 * Testa a classe BSignpost.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
public class BSignpostTest {

    private BSignpost bSignpost;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();

        // Criando instância de BSignpost
        bSignpost = new BSignpost();
        bSignpost.setId(faker.number().randomNumber());
        bSignpost.setBoardLocation(faker.address().streetAddress());
        bSignpost.setSector(faker.company().industry());

        
        Material material = new Material();
        material.setId(faker.number().randomNumber());
        material.setDescription(faker.commerce().material());

        Briefing briefing = new Briefing();
        briefing.setId(faker.number().randomNumber());

        bSignpost.setMaterial(material);
        bSignpost.setBriefing(briefing);
    }

    /**
     * Testa a criação da instância da classe BSignpost.
     * Verifica se o ID, localização do quadro, setor, material e briefing são definidos corretamente
     * após a configuração inicial no método setUp.
     */
    @Test
    @DisplayName("Should correctly set properties of BSignpost")
    void testBSignpostCreation() {
        // Verifica se o ID foi gerado corretamente
        assertThat(bSignpost.getId()).isNotNull();

        // Verifica se a localização do quadro e o setor foram atribuídos corretamente
        assertThat(bSignpost.getBoardLocation()).isNotNull().isNotEmpty();
        assertThat(bSignpost.getSector()).isNotNull().isNotEmpty();

        // Verifica se o material e o briefing foram atribuídos corretamente
        assertThat(bSignpost.getMaterial()).isNotNull();
        assertThat(bSignpost.getBriefing()).isNotNull();
    }

    /**
     * Testa a igualdade entre instâncias da classe BSignpost.
     * Verifica se instâncias com o mesmo ID e atributos são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider BSignpost instances with the same attributes as equal")
    void testBSignpostEquality() {
        // Criando uma segunda instância com o mesmo ID e atributos
        BSignpost anotherBSignpost = new BSignpost();
        anotherBSignpost.setId(bSignpost.getId());
        anotherBSignpost.setBoardLocation(bSignpost.getBoardLocation());
        anotherBSignpost.setSector(bSignpost.getSector());
        anotherBSignpost.setMaterial(bSignpost.getMaterial());
        anotherBSignpost.setBriefing(bSignpost.getBriefing());

        // Verifica se as instâncias com o mesmo ID e atributos são consideradas iguais
        assertThat(bSignpost).isEqualTo(anotherBSignpost);
    }

    /**
     * Testa o método toString da classe BSignpost.
     * Verifica se o método toString retorna uma representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation of BSignpost")
    void testBSignpostToString() {
        // Verifica o método toString
        String bSignpostString = bSignpost.toString();
        assertThat(bSignpostString).contains(bSignpost.getId().toString());
        //assertThat(bSignpostString).contains(bSignpost.getBoardLocation());
    }

    /**
     * Testa os métodos setters e getters da classe BSignpost.
     * Verifica se os métodos setId, setBoardLocation, setSector, setMaterial e setBriefing
     * definem corretamente os atributos e se os métodos getId, getBoardLocation, getSector,
     * getMaterial e getBriefing retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
    void testSettersAndGetters() {
        Long id = faker.number().randomNumber();
        String boardLocation = faker.address().streetAddress();
        String sector = faker.company().industry();

        bSignpost.setId(id);
        bSignpost.setBoardLocation(boardLocation);
        bSignpost.setSector(sector);

        assertThat(bSignpost.getId()).isEqualTo(id);
        assertThat(bSignpost.getBoardLocation()).isEqualTo(boardLocation);
        assertThat(bSignpost.getSector()).isEqualTo(sector);
    }

    /**
     * Testa os métodos hashCode e equals da classe BSignpost.
     * Verifica se duas instâncias com os mesmos valores de id são iguais e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should correctly implement equals and hashCode")
    void testEqualsAndHashCode() {
        Long id = 1L;

        BSignpost signpost1 = new BSignpost();
        signpost1.setId(id);
        BSignpost signpost2 = new BSignpost();
        signpost2.setId(id);
        BSignpost signpost3 = new BSignpost();
        signpost3.setId(2L); // Instância com id diferente

        assertThat(signpost1).isEqualTo(signpost2);
        assertThat(signpost1.hashCode()).isEqualTo(signpost2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(signpost1).isNotEqualTo(signpost3);
        assertThat(signpost1.hashCode()).isNotEqualTo(signpost3.hashCode());
    }
}
