package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;

@DataJpaTest
public class SignpostRepositoryTest {

    @Autowired
    private SignpostRepository signpostRepository;

    private BSignpost signpost;

    @BeforeEach
    void setUp() {
        // Configurando um signpost fictício para teste
        signpost = new BSignpost();
        signpost.setLocation("Main St & 1st Ave");
        signpost.setDescription("Directional signpost indicating various locations.");
    }

    /**
     * Testa a persistência e recuperação de um Signpost.
     */
    @Test
    @Rollback(false) // Coloque false se você não quer que o banco de dados reverta após o teste
    void testSaveAndFindSignpost() {
        // Act
        BSignpost savedSignpost = signpostRepository.save(signpost);
        
        // Assert
        Optional<BSignpost> foundSignpost = signpostRepository.findById(savedSignpost.getId());
        assertThat(foundSignpost).isPresent();
        assertThat(foundSignpost.get().getLocation()).isEqualTo("Main St & 1st Ave");
    }

    /**
     * Testa a exclusão de um Signpost.
     */
    @Test
    @Rollback(false)
    void testDeleteSignpost() {
        // Act
        BSignpost savedSignpost = signpostRepository.save(signpost);
        signpostRepository.delete(savedSignpost);

        // Assert
        Optional<BSignpost> foundSignpost = signpostRepository.findById(savedSignpost.getId());
        assertThat(foundSignpost).isNotPresent();
    }
}
