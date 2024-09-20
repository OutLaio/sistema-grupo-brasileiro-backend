//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.github.javafaker.Faker;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.CompanyView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Company;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class CompanyViewMapperTest {
//
//    private CompanyViewMapper companyViewMapper;
//    private Faker faker;
//
//    @BeforeEach
//    void setUp() {
//        companyViewMapper = new CompanyViewMapper();
//        faker = new Faker();
//    }
//
//    @Test
//    void shouldMapCompanyToCompanyView() {
//        // Arrange
//        Company company = new Company();
//        company.setId(faker.number().randomNumber());
//        company.setName(faker.company().name());
//
//        // Act
//        CompanyView companyView = companyViewMapper.map(company);
//
//        // Assert
//        assertNotNull(companyView, "CompanyView should not be null");
//        assertEquals(company.getId(), companyView.id(), "Company ID should match");
//        assertEquals(company.getName(), companyView.name(), "Company Name should match");
//    }
//}
