package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyCityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.CompanyCityViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.CompanyCity;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyCityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.CompanyCityViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.CompanyCity;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;

/**
 * Testa a classe CompanyCityViewMapper.
 * Verifica se o mapeamento lida com CompanyCity nulo e campos nulos corretamente.
 */
public class CompanyCityViewMapperTest {

    @InjectMocks
    private CompanyCityViewMapper companyCityViewMapper;

    @Mock
    private CityViewMapper cityViewMapper;

    @Mock
    private CompanyViewMapper companyViewMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de CompanyCity para CompanyCityView.
     * Verifica se um CompanyCity é corretamente mapeado para um CompanyCityView.
     */
    @Test
    @DisplayName("Should map CompanyCity to CompanyCityView correctly")
    void deveMapearCompanyCityParaCompanyCityView() {
        // Dados de teste usando Faker
        City city = new City();
        city.setId(faker.number().randomNumber());
        city.setName(faker.address().city());

        Company company = new Company();
        company.setId(faker.number().randomNumber());
        company.setName(faker.company().name());

        CompanyCity companyCity = new CompanyCity();
        companyCity.setId(faker.number().randomNumber());
        companyCity.setCity(city);
        companyCity.setCompany(company);

        CityView cityView = new CityView(city.getId(), city.getName());
        CompanyView companyView = new CompanyView(company.getId(), company.getName());

        // Configurando o comportamento dos mocks
        when(cityViewMapper.map(any(City.class))).thenReturn(cityView);
        when(companyViewMapper.map(any(Company.class))).thenReturn(companyView);

        // Mapeamento
        CompanyCityView result = companyCityViewMapper.map(companyCity);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(companyCity.getId());
        assertThat(result.city()).isEqualTo(cityView);
        assertThat(result.company()).isEqualTo(companyView);
    }

    /**
     * Testa que o método map retorna null ao receber CompanyCity nulo.
     * Verifica se o método lida corretamente com entradas nulas.
     */
    @Test
    @DisplayName("Should return null when mapping null CompanyCity")
    void deveRetornarNullParaCompanyCityNulo() {
        CompanyCityView result = companyCityViewMapper.map(null);
        assertThat(result).isNull();
    }

    /**
     * Testa o mapeamento de CompanyCity com campos nulos para CompanyCityView.
     * Verifica se o método lida corretamente com campos nulos no CompanyCity.
     */
    @Test
    @DisplayName("Should map CompanyCity with null fields to CompanyCityView with null fields")
    void deveMapearCompanyCityComCamposNulosParaCompanyCityView() {
        CompanyCity companyCity = new CompanyCity();
        companyCity.setId(faker.number().randomNumber());
        companyCity.setCity(null);
        companyCity.setCompany(null);

        // Mapeamento
        CompanyCityView result = companyCityViewMapper.map(companyCity);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(companyCity.getId());
        assertThat(result.city()).isNull();
        assertThat(result.company()).isNull();
    }
}

