package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AgencyBoardTypeRepositoryTest {

    @Autowired
    private AgencyBoardTypeRepository agencyBoardTypeRepository;

    @BeforeEach
    void setUp() {
        // Não é necessário fazer nada aqui, mas pode ser usado para configurações
    }

    @Test
    @Rollback(false)  // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should create and retrieve an AgencyBoardType")
    void testCreateAndRetrieveAgencyBoardType() {
        // Arrange
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Tipo de placa de agência");

        // Act
        AgencyBoardType savedType = agencyBoardTypeRepository.save(agencyBoardType);
        AgencyBoardType retrievedType = agencyBoardTypeRepository.findById(savedType.getId()).orElse(null);

        // Assert
        assertThat(retrievedType).isNotNull();
        assertThat(retrievedType.getId()).isEqualTo(savedType.getId());
        assertThat(retrievedType.getDescription()).isEqualTo("Tipo de placa de agência");
    }
}

