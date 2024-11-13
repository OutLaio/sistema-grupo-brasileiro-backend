package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.RoutesForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CompanyRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

/**
 * Testa a classe RouteFormMapper.
 * Verifica o funcionamento do mapeamento de RoutesForm para Route.
 */
public class RouteFormMapperTest {

    private final Faker faker = new Faker();

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private RouteFormMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o método map da classe RouteFormMapper.
     * Verifica se o método mapeia corretamente um RoutesForm para um Route.
     */
    @Test
    @DisplayName("Should map RoutesForm to Route correctly")
    void shouldMap() {
        Long fakeIdCompany = faker.number().randomNumber(); 
        String fakeType = faker.lorem().word();
        Set<Long> fakeIdCities = new HashSet<>();
        fakeIdCities.add(faker.number().randomNumber()); 

        // Mock do CompanyRepository para retornar uma empresa quando findById for chamado
        Company mockCompany = new Company(); 
        when(companyRepository.findById(fakeIdCompany)).thenReturn(java.util.Optional.of(mockCompany));

        // Criando o RoutesForm com o construtor padrão do record
        RoutesForm form = new RoutesForm(
                fakeIdCompany, 
                fakeIdCities,  
                fakeType
        );

        // Mapeia o RoutesForm para Route
        Route result = mapper.map(form);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se o campo type foi mapeado corretamente
        assertThat(result.getType()).isEqualTo(fakeType);
    }

    /**
     * Testa o método map da classe RouteFormMapper com dados nulos.
     * Verifica se o método lida corretamente com dados nulos para idCompany.
     */
    @Test
    @DisplayName("Should throw EntityNotFoundException when idCompany is invalid")
    void shouldThrowExceptionWhenIdCompanyIsInvalid() {
        // Criando o RoutesForm com um idCompany inválido
        RoutesForm form = new RoutesForm(
                0L, // idCompany inválido
                new HashSet<>(), // idCities válidos
                ""  // type em branco
        );

        // Verifica se a exceção é lançada ao mapear o RoutesForm
        assertThrows(EntityNotFoundException.class, () -> {
            mapper.map(form);
        });
    }
    
}