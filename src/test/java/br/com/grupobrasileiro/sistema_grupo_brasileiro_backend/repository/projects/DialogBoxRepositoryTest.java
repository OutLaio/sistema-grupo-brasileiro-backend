package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
public class DialogBoxRepositoryTest {

    @Autowired
    private DialogBoxRepository dialogBoxRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a criação e recuperação de uma caixa de diálogo.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a dialog box")
    void testCreateAndRetrieveDialogBox() {
        // Arrange
        Employee employee = new Employee();
        employee.setName(faker.name().firstName());
        employee.setLastName(faker.name().lastName());
        // Salvar o empregado
        employeeRepository.save(employee);

        Briefing briefing = new Briefing();
        briefing.setDetailedDescription(faker.lorem().sentence());
        // Salvar o briefing
        briefingRepository.save(briefing);

        DialogBox dialogBox = new DialogBox();
        dialogBox.setEmployee(employee);
        dialogBox.setBriefing(briefing);
        dialogBox.setTime(LocalDateTime.now());
        dialogBox.setDialog(faker.lorem().paragraph());

        // Act
        dialogBoxRepository.save(dialogBox);
        Optional<DialogBox> retrievedDialogBox = dialogBoxRepository.findById(dialogBox.getId());

        // Assert
        assertThat(retrievedDialogBox).isPresent();
        assertThat(retrievedDialogBox.get().getDialog()).isEqualTo(dialogBox.getDialog());
        assertThat(retrievedDialogBox.get().getEmployee()).isEqualTo(employee);
        assertThat(retrievedDialogBox.get().getBriefing()).isEqualTo(briefing);
    }

    /**
     * Testa a recuperação de uma caixa de diálogo que não existe.
     */
    @Test
    @DisplayName("Should return empty when retrieving non-existing dialog box")
    void testRetrieveNonExistingDialogBox() {
        // Act
        Optional<DialogBox> retrievedDialogBox = dialogBoxRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(retrievedDialogBox).isNotPresent();
    }
}
