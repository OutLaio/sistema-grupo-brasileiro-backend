package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OtherRouteRepositoryTest {

    @Autowired
    private OtherRouteRepository otherRouteRepository;

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    /**
     * Testa a persistência e recuperação de uma OtherRoute.
     */
    @Test
    public void testSaveAndFindOtherRoute() {
        // Criação de um novo objeto BAgencyBoard
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        // Defina os campos necessários para BAgencyBoard
        bAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Criação de um novo objeto OtherRoute
        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setBAgencyBoard(bAgencyBoard);
        otherRoute.setCompany("Empresa B");
        otherRoute.setCity("Cidade B");
        otherRoute.setType("Tipo B");

        // Salva o objeto no repositório
        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);

        // Recupera o objeto pelo ID
        Optional<OtherRoute> foundOtherRoute = otherRouteRepository.findById(savedOtherRoute.getId());

        // Verifica se o objeto encontrado é o mesmo que o salvo
        assertThat(foundOtherRoute).isPresent();
        assertThat(foundOtherRoute.get()).isEqualTo(savedOtherRoute);
    }

    /**
     * Testa a exclusão de uma OtherRoute.
     */
    @Test
    public void testDeleteOtherRoute() {
        // Criação de um novo objeto BAgencyBoard
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Criação de um novo objeto OtherRoute
        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setBAgencyBoard(bAgencyBoard);
        otherRoute.setCompany("Empresa B");
        otherRoute.setCity("Cidade B");
        otherRoute.setType("Tipo B");

        // Salva o objeto no repositório
        OtherRoute savedOtherRoute = otherRouteRepository.save(otherRoute);

        // Exclui o objeto
        otherRouteRepository.delete(savedOtherRoute);

        // Verifica se o objeto foi excluído
        Optional<OtherRoute> foundOtherRoute = otherRouteRepository.findById(savedOtherRoute.getId());
        assertThat(foundOtherRoute).isNotPresent();
    }
}
