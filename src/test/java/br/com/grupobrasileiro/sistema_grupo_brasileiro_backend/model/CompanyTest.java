package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    void testCompanyCreation() {
        // Arrange
        Long id = 1L;
        String name = "Test Company";

        // Act
        Company company = new Company(id, name);

        // Assert
        assertThat(company.getId()).isEqualTo(id);
        assertThat(company.getName()).isEqualTo(name);
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Company company1 = new Company(1L, "Test Company");
        Company company2 = new Company(1L, "Test Company");
        Company company3 = new Company(2L, "Different Company");

        // Act & Assert
        assertThat(company1).isEqualTo(company2);  // should be equal
        assertThat(company1).isNotEqualTo(company3);  // should not be equal

        // Check if hash codes are consistent
        assertThat(company1.hashCode()).isEqualTo(company2.hashCode());
        assertThat(company1.hashCode()).isNotEqualTo(company3.hashCode());
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        Company company = new Company(1L, "Test Company");

        // Act & Assert
        assertThat(company).isNotEqualTo(null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        // Arrange
        Company company = new Company(1L, "Test Company");
        String otherObject = "Not a Company";

        // Act & Assert
        assertThat(company).isNotEqualTo(otherObject);
    }
}
