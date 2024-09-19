package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompaniesBriefingTest {

    /**
     * Testa o construtor padrão da classe CompaniesBriefing.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        CompaniesBriefing companiesBriefing = new CompaniesBriefing();
        assertThat(companiesBriefing).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe CompaniesBriefing.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, company e briefing.
     */
    @Test
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
    void testSettersAndGetters() {
        CompaniesBriefing companiesBriefing = new CompaniesBriefing();
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
}
