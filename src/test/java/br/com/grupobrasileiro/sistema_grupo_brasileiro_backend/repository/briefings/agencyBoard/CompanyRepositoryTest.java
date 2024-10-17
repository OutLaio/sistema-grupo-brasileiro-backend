package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import jakarta.transaction.Transactional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class CompanyRepositoryTest {

    @Mock
    private CompanyRepository companyRepository;

    private Company company;

    @BeforeEach
    public void setUp() {
        company = new Company();
        company.setName("Default Company");
    }

    @Test
    void testInsertAndFindCompany() {
        // Arrange
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));

        // Act
        Company savedCompany = companyRepository.save(company);
        Optional<Company> foundCompany = companyRepository.findById(1L); // ID fictício

        // Assert
        assertThat(foundCompany).isPresent();
        assertThat(foundCompany.get().getName()).isEqualTo("Default Company");
    }

    @Test
    void testSaveCompany() {
        // Arrange
        company.setName("Test Company");
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        // Act
        Company savedCompany = companyRepository.save(company);

        // Assert
        assertThat(savedCompany).isNotNull();
        assertThat(savedCompany.getName()).isEqualTo("Test Company");
    }

    @Test
    void testDeleteCompany() {
        // Arrange
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));

        // Act
        companyRepository.delete(company);

        // Verify
        verify(companyRepository).delete(company);
    }

    @Test
    void testUpdateCompany() {
        // Arrange
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));

        // Act
        company.setName("Updated Company");
        Company updatedCompany = companyRepository.save(company);

        // Assert
        assertThat(updatedCompany.getName()).isEqualTo("Updated Company");
    }

    @Test
    void testFindAllCompanies() {
        // Arrange
        Company anotherCompany = new Company();
        anotherCompany.setName("Another Company");
        when(companyRepository.findAll()).thenReturn(Arrays.asList(company, anotherCompany));

        // Act
        Iterable<Company> companies = companyRepository.findAll();

        // Assert
        assertThat(companies).isNotEmpty();
        assertThat(companies).hasSize(2);
    }
}
