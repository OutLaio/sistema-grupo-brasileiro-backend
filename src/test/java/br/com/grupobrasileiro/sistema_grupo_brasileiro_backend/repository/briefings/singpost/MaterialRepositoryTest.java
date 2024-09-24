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
        // Inicializando um objeto Material para os testes
        material = new Material();
        material.setDescription("Material usado para construções.");
    }

    /**
     * Testa a persistência e recuperação de um Material.
     */
    @Test
    @Rollback(false) 
    void testSaveAndFindMaterial() {
        // Act: Salva o material no banco de dados
        Material savedMaterial = materialRepository.save(material);
        
        // Assert: Verifica se o material foi salvo e pode ser recuperado corretamente
        Optional<Material> foundMaterial = materialRepository.findById(savedMaterial.getId());
        assertThat(foundMaterial).isPresent();
        assertThat(foundMaterial.get().getDescription()).isEqualTo("Material usado para construções.");
    }

    /**
     * Testa a exclusão de um Material.
     */
    @Test
    @Rollback(false)
    void testDeleteMaterial() {
        // Act: Salva e depois deleta o material
        Material savedMaterial = materialRepository.save(material);
        materialRepository.delete(savedMaterial);

        // Assert: Verifica se o material foi deletado
        Optional<Material> foundMaterial = materialRepository.findById(savedMaterial.getId());
        assertThat(foundMaterial).isNotPresent();
    }
}
