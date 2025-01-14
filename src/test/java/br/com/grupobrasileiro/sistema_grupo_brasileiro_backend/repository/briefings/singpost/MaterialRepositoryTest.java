package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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
    @DisplayName("Should save and find Material correctly")
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
    @DisplayName("Should delete Material correctly")
    void testDeleteMaterial() {
        // Act: Salva e depois deleta o material
        Material savedMaterial = materialRepository.save(material);
        materialRepository.delete(savedMaterial);

        // Assert: Verifica se o material foi deletado
        Optional<Material> foundMaterial = materialRepository.findById(savedMaterial.getId());
        assertThat(foundMaterial).isNotPresent();
    }

    /**
     * Testa a atualização de um Material existente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update Material description correctly")
    void testUpdateMaterial() {
        // Act: Salva o material e atualiza a descrição
        Material savedMaterial = materialRepository.save(material);
        savedMaterial.setDescription("Descrição atualizada.");
        Material updatedMaterial = materialRepository.save(savedMaterial);

        // Assert: Verifica se a descrição foi atualizada corretamente
        Optional<Material> foundMaterial = materialRepository.findById(updatedMaterial.getId());
        assertThat(foundMaterial).isPresent();
        assertThat(foundMaterial.get().getDescription()).isEqualTo("Descrição atualizada.");
    }

    /**
     * Testa a tentativa de salvar um Material com descrição nula.
     */
    @Test
    @DisplayName("Should not save Material with null description")
    void testSaveMaterialWithNullDescription() {
        // Arrange: Cria um Material com descrição nula
        Material nullDescriptionMaterial = new Material();
        nullDescriptionMaterial.setDescription(null);

        // Act & Assert: Tenta salvar e espera uma exceção
        assertThrows(Exception.class, () -> {
            materialRepository.save(nullDescriptionMaterial);
        });
    }
}
