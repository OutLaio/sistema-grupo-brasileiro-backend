package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testa a classe CompaniesBriefing.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
public class CompaniesBriefingTest {

    private CompaniesBriefing companiesBriefing;

    @BeforeEach
    void setUp() {
        // Inicializa uma nova instância de CompaniesBriefing antes de cada teste
        companiesBriefing = new CompaniesBriefing();
    }

    /**
     * Testa o construtor padrão da classe CompaniesBriefing.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create an instance with the default constructor")
    void testDefaultConstructor() {
        assertThat(companiesBriefing).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe CompaniesBriefing.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, company e briefing.
     */
    @Test
    @DisplayName("Should set properties correctly with the parameterized constructor")
    void testParameterizedConstructor() {
        Long id = 1L;
        Company company = new Company();
        company.setId(1L);
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        CompaniesBriefing companiesBriefing = new CompaniesBriefing(id, company, briefing);

        assertThat(companiesBriefing.getId()).isEqualTo(id);
        assertThat(companiesBriefing.getCompany()).isEqualTo(company);
        assertThat(companiesBriefing.getBriefing()).isEqualTo(briefing);
    }

    /**
     * Testa os métodos setters e getters da classe CompaniesBriefing.
     * Verifica se os métodos setId, setCompany e setBriefing definem corretamente os atributos
     * e se os métodos getId, getCompany e getBriefing retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
    void testSettersAndGetters() {
        Long id = 1L;
        Company company = new Company();
        company.setId(1L);
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        companiesBriefing.setId(id);
        companiesBriefing.setCompany(company);
        companiesBriefing.setBriefing(briefing);

        assertThat(companiesBriefing.getId()).isEqualTo(id);
        assertThat(companiesBriefing.getCompany()).isEqualTo(company);
        assertThat(companiesBriefing.getBriefing()).isEqualTo(briefing);
    }

    /**
     * Testa os métodos equals e hashCode da classe CompaniesBriefing.
     * Verifica se duas instâncias com os mesmos valores de id, company e briefing são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should consider equal instances with the same values")
    void testEqualsAndHashCode() {
        Long id = 1L;
        Company company = new Company();
        company.setId(1L);
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        CompaniesBriefing companiesBriefing1 = new CompaniesBriefing(id, company, briefing);
        CompaniesBriefing companiesBriefing2 = new CompaniesBriefing(id, company, briefing);
        CompaniesBriefing companiesBriefing3 = new CompaniesBriefing(id + 1, company, briefing); // Instância com id diferente

        assertThat(companiesBriefing1).isEqualTo(companiesBriefing2);
        assertThat(companiesBriefing1.hashCode()).isEqualTo(companiesBriefing2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(companiesBriefing1).isNotEqualTo(companiesBriefing3);
        assertThat(companiesBriefing1.hashCode()).isNotEqualTo(companiesBriefing3.hashCode());
    }

    /**
     * Testa o método toString da classe CompaniesBriefing.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id, company e briefing.
     */
    @Test
    @DisplayName("Should return the correct string representation")
    void testToString() {
        Long id = 1L;
        Company company = new Company();
        company.setId(1L);
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        CompaniesBriefing companiesBriefing = new CompaniesBriefing(id, company, briefing);

        String expectedToString = "CompaniesBriefing(id=" + id +
                                  ", company=" + company +
                                  ", briefing=" + briefing + ")";
        assertThat(companiesBriefing.toString()).contains(expectedToString);
    }

    /**
     * Testa o comportamento da classe quando atributos nulos são definidos.
     * Verifica se a classe trata corretamente casos em que valores nulos são atribuídos.
     */
    @Test
    @DisplayName("Should handle null attributes correctly")
    void testNullAttributes() {
        companiesBriefing.setId(null);
        companiesBriefing.setCompany(null);
        companiesBriefing.setBriefing(null);

        assertThat(companiesBriefing.getId()).isNull();
        assertThat(companiesBriefing.getCompany()).isNull();
        assertThat(companiesBriefing.getBriefing()).isNull();
    }

    /**
     * Testa a igualdade de CompaniesBriefing com atributos diferentes.
     * Verifica se dois objetos diferentes não são considerados iguais.
     */
    @Test
    @DisplayName("Should not consider different CompaniesBriefing instances as equal")
    void testNotEqual() {
        Long id = 1L;
        Company company1 = new Company();
        company1.setId(1L);
        Company company2 = new Company();
        company2.setId(2L);
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        CompaniesBriefing companiesBriefing1 = new CompaniesBriefing(id, company1, briefing);
        CompaniesBriefing companiesBriefing2 = new CompaniesBriefing(id, company2, briefing);

        
    }
}
