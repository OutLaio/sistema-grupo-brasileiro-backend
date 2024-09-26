package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;

@DataJpaTest
public class AgencyBoardTypeRepositoryTest {

    @Autowired
    private AgencyBoardTypeRepository agencyBoardTypeRepository;

    /**
     * Testa a criação e recuperação de um AgencyBoardType.
     */
    @Test
    @DisplayName("Test creation and retrieval of an AgencyBoardType")
    public void testSaveAndFindAgencyBoardType() {
        // Criação de um novo AgencyBoardType
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Tipo de Placa 1");

        // Salvando no repositório
        agencyBoardType = agencyBoardTypeRepository.save(agencyBoardType);

        // Buscando pelo ID
        Optional<AgencyBoardType> found = agencyBoardTypeRepository.findById(agencyBoardType.getId());

        // Verificando se foi salvo corretamente
        assertThat(found).isPresent();
        assertThat(found.get().getDescription()).isEqualTo("Tipo de Placa 1");
    }

    /**
     * Testa a deleção de um AgencyBoardType.
     */
    @Test
    @DisplayName("Test deletion of an AgencyBoardType")
    public void testDeleteAgencyBoardType() {
        // Criação de um novo AgencyBoardType
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Tipo de Placa 2");

        // Salvando no repositório
        agencyBoardType = agencyBoardTypeRepository.save(agencyBoardType);

        // Deletando o AgencyBoardType
        agencyBoardTypeRepository.deleteById(agencyBoardType.getId());

        // Verificando se foi deletado
        Optional<AgencyBoardType> found = agencyBoardTypeRepository.findById(agencyBoardType.getId());
        assertThat(found).isNotPresent();
    }

    /**
     * Testa a recuperação de todos os AgencyBoardTypes.
     */
    @Test
    @DisplayName("Test retrieval of all AgencyBoardTypes")
    public void testFindAllAgencyBoardTypes() {
        // Criação e salvamento de múltiplos AgencyBoardType
        agencyBoardTypeRepository.save(new AgencyBoardType(null, "Tipo de Placa 1"));
        agencyBoardTypeRepository.save(new AgencyBoardType(null, "Tipo de Placa 2"));

        // Buscando todos os AgencyBoardTypes
        List<AgencyBoardType> types = agencyBoardTypeRepository.findAll();

        // Verificando se os dados foram inseridos corretamente
        assertThat(types).hasSize(2); // Deve encontrar 2 tipos
    }

    /**
     * Testa a atualização de um AgencyBoardType.
     */
    @Test
    @DisplayName("Test update of an AgencyBoardType")
    public void testUpdateAgencyBoardType() {
        // Criação de um novo AgencyBoardType
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Tipo de Placa 3");
        agencyBoardType = agencyBoardTypeRepository.save(agencyBoardType);

        // Atualizando a descrição
        agencyBoardType.setDescription("Tipo de Placa Atualizado");
        agencyBoardTypeRepository.save(agencyBoardType);

        // Buscando o AgencyBoardType atualizado
        Optional<AgencyBoardType> updated = agencyBoardTypeRepository.findById(agencyBoardType.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getDescription()).isEqualTo("Tipo de Placa Atualizado");
    }

    /**
     * Testa a recuperação de um AgencyBoardType inexistente.
     */
    @Test
    @DisplayName("Test retrieval of a non-existent AgencyBoardType")
    public void testFindNonExistentAgencyBoardType() {
        // Buscando um AgencyBoardType com um ID inexistente
        Optional<AgencyBoardType> found = agencyBoardTypeRepository.findById(999L); // ID que não existe
        assertThat(found).isNotPresent();
    }
}

