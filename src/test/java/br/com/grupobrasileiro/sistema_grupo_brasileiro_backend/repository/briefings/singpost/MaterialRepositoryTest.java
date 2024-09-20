package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;

@DataJpaTest
public class MaterialRepositoryTest {

    @Autowired
    private MaterialRepository materialRepository;

    private Material material;

    @BeforeEach
    void setUp() {
        // Configurando um material fictício para teste
        material = new Material();
        material.setName("Madeira");
        material.setDescription("Material usado para construções.");
        material.setPrice(100.50);
    }

    /**
     * Testa a persistência e recuperação de um Material.
     */
    @Test
    @Rollback(false) // Coloque false se você não quer que o banco de dados reverta após o teste
    void testSaveAndFindMaterial() {
        // Act
        Material savedMaterial = materialRepository.save(material);
        
        // Assert
        Optional<Material> foundMaterial = materialRepository.findById(savedMaterial.getId());
        assertThat(foundMaterial).isPresent();
        assertThat(foundMaterial.get().getName()).isEqualTo("Madeira");
        assertThat(foundMaterial.get().getPrice()).isEqualTo(100.50);
    }

    /**
     * Testa a exclusão de um Material.
     */
    @Test
    @Rollback(false)
    void testDeleteMaterial() {
        // Act
        Material savedMaterial = materialRepository.save(material);
        materialRepository.delete(savedMaterial);

        // Assert
        Optional<Material> foundMaterial = materialRepository.findById(savedMaterial.getId());
        assertThat(foundMaterial).isNotPresent();
    }
}
