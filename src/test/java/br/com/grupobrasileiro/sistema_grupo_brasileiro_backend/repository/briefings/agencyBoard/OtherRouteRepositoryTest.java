package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OtherRouteRepositoryTest {

    @Autowired
    private OtherRouteRepository otherRouteRepository;

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    private BAgencyBoard bAgencyBoard;
    private OtherRoute otherRoute;

    @BeforeEach
    public void setUp() {
        // Criação de um novo objeto BAgencyBoard antes de cada teste
        bAgencyBoard = new BAgencyBoard();
        // Defina os campos necessários para BAgencyBoard
        bAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Criação de um novo objeto OtherRoute
        otherRoute = new OtherRoute();
        otherRoute.setBAgencyBoard(bAgencyBoard);
        otherRoute.setCompany("Empresa B");
        otherRoute.setCity("Cidade B");
        otherRoute.setType("Tipo B");
    }

    /**
     * Testa a persistência e recuperação de uma OtherRoute.
     */
    @Test
    @DisplayName("Should save and find an OtherRoute")
    public void testSaveAndFindOtherRoute() {
        // Salva o objeto no repositório
        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);

        // Recupera o objeto pelo ID
        Optional<OtherRoute> foundOtherRoute = otherRouteRepository.findById(savedOtherRoute.getId());

        // Verifica se o objeto encontrado é o mesmo que o salvo
        assertThat(foundOtherRoute).isPresent();
        assertThat(foundOtherRoute.get()).isEqualTo(savedOtherRoute);
        assertThat(foundOtherRoute.get().getCompany()).isEqualTo("Empresa B");
        assertThat(foundOtherRoute.get().getCity()).isEqualTo("Cidade B");
        assertThat(foundOtherRoute.get().getType()).isEqualTo("Tipo B");
    }

    /**
     * Testa a exclusão de uma OtherRoute.
     */
    @Test
    @DisplayName("Should delete an OtherRoute")
    public void testDeleteOtherRoute() {
        // Salva o objeto no repositório
        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);

        // Exclui o objeto
        otherRouteRepository.delete(savedOtherRoute);

        // Verifica se o objeto foi excluído
        Optional<OtherRoute> foundOtherRoute = otherRouteRepository.findById(savedOtherRoute.getId());
        assertThat(foundOtherRoute).isNotPresent();
    }

    /**
     * Testa a atualização de uma OtherRoute.
     */
    @Test
    @DisplayName("Should update an OtherRoute")
    public void testUpdateOtherRoute() {
        // Salva o objeto no repositório
        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);

        // Atualiza a cidade e a empresa da OtherRoute
        savedOtherRoute.setCity("Nova Cidade B");
        savedOtherRoute.setCompany("Nova Empresa B");
        OtherRoute updatedOtherRoute = otherRouteRepository.save(savedOtherRoute);

        // Verifica se os dados foram atualizados corretamente
        assertThat(updatedOtherRoute.getCity()).isEqualTo("Nova Cidade B");
        assertThat(updatedOtherRoute.getCompany()).isEqualTo("Nova Empresa B");
    }

    /**
     * Testa a recuperação de todas as OtherRoutes.
     */
    @Test
    @DisplayName("Should find all OtherRoutes")
    public void testFindAllOtherRoutes() {
        // Salva múltiplas OtherRoutes
        otherRouteRepository.save(otherRoute);
        OtherRoute anotherRoute = new OtherRoute();
        anotherRoute.setBAgencyBoard(bAgencyBoard);
        anotherRoute.setCompany("Outra Empresa B");
        anotherRoute.setCity("Outra Cidade B");
        anotherRoute.setType("Outro Tipo B");
        otherRouteRepository.save(anotherRoute);

        // Recupera todas as OtherRoutes
        Iterable<OtherRoute> otherRoutes = otherRouteRepository.findAll();

        // Verifica se a lista não está vazia
        assertThat(otherRoutes).isNotEmpty(); // Deve conter elementos
        assertThat(otherRoutes).hasSize(2); // Deve ter dois elementos
    }
}
