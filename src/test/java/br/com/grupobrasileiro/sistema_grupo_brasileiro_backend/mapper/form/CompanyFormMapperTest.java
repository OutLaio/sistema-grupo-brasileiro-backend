//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CompanyForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Company;
//
//
//@SpringBootTest
//public class CompanyFormMapperTest {
//
//    private CompanyFormMapper companyFormMapper;
//
//    @BeforeEach
//    void setUp() {
//        companyFormMapper = new CompanyFormMapper();
//    }
//
//    @Test
//    void testMap() {
//        // Arrange
//        String companyName = "example company";
//        CompanyForm companyForm = new CompanyForm(companyName);
//
//        // Act
//        Company company = companyFormMapper.map(companyForm);
//
//        // Assert
//        assertEquals(null, company.getId(), "The company ID should be null");
//        assertEquals(companyName.toUpperCase(), company.getName(), "The company name should be converted to uppercase");
//    }
//}
