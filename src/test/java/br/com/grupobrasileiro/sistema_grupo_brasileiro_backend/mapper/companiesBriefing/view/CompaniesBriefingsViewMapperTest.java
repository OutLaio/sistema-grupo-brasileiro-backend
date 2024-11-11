package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBriefing.view;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.CompanyViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Set;

public class CompaniesBriefingsViewMapperTest {

    @InjectMocks
    private CompaniesBriefingsViewMapper companiesBriefingsViewMapper;

    @Mock
    private CompanyViewMapper companyViewMapper;

    private CompaniesBriefing companiesBriefing;
    private Company company;

    @BeforeEach
    void setUp() {
        // Inicializar os mocks
        company = mock(Company.class); // Mock da empresa
        companiesBriefing = mock(CompaniesBriefing.class); // Mock de CompaniesBriefing

        // Configurar o comportamento dos mocks
        when(companiesBriefing.getCompany()).thenReturn(company);
    }

    @Test
    void shouldMapSuccessfullyWhenSetIsNotEmpty() {
        // Arrange: Criando um conjunto de CompaniesBriefing
        Set<CompaniesBriefing> companiesBriefingSet = new HashSet<>();
        companiesBriefingSet.add(companiesBriefing);

        // Mock do mapeamento de Company para CompanyView
        CompanyView companyView = mock(CompanyView.class);
        when(companyViewMapper.map(any(Company.class))).thenReturn(companyView);

        // Act: Chamar o método map
        CompaniesBriefingsView result = companiesBriefingsViewMapper.map(companiesBriefingSet);

        // Assert: Verificar se o resultado contém um CompanyView
        assertNotNull(result, "Resultado não pode ser nulo");
        assertEquals(1, result.companies().size(), "Deve conter exatamente 1 CompanyView");
        assertTrue(result.companies().contains(companyView), "A lista de companies deve conter o CompanyView mapeado");

        // Verificar se o método map de CompanyViewMapper foi chamado corretamente
        verify(companyViewMapper, times(1)).map(any(Company.class));
    }

    @Test
    void shouldReturnEmptyWhenSetIsEmpty() {
        // Arrange: Criando um conjunto vazio de CompaniesBriefing
        Set<CompaniesBriefing> companiesBriefingSet = new HashSet<>();

        // Act: Chamar o método map
        CompaniesBriefingsView result = companiesBriefingsViewMapper.map(companiesBriefingSet);

        // Assert: Verificar se o resultado está vazio
        assertNotNull(result, "Resultado não pode ser nulo");
        assertTrue(result.companies().isEmpty(), "A lista de companies deve estar vazia");

        // Verificar que o método map de CompanyViewMapper não foi chamado
        verify(companyViewMapper, never()).map(any(Company.class));
    }
}
