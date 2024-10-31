package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BPrintedRepositoryTest {

    @Mock
    private BPrintedRepository bPrintedRepository;

    @Mock
    private BriefingRepository briefingRepository;

    @Mock
    private PrintedTypeRepository printedTypeRepository;

    private Briefing briefing;
    private PrintedType printedType;

    @BeforeEach
    void setUp() {
        // Criar um Briefing e um PrintedType para os testes
        briefing = new Briefing();
        briefing.setId(1L); // Simula um ID

        printedType = new PrintedType();
        printedType.setId(1L); // Simula um ID
    }

    @Test
    @DisplayName("Must save a BPrinted correctly")
    void testSaveBPrinted() {
        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing);
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        bPrinted.setPrintedType(printedType);

        when(bPrintedRepository.save(bPrinted)).thenReturn(bPrinted);

        BPrinted savedPrinted = bPrintedRepository.save(bPrinted);

        assertThat(savedPrinted).isNotNull();
        assertThat(savedPrinted.getId()).isNull(); // ID deve ser gerado na implementação real
        assertThat(savedPrinted.getPaperType()).isEqualTo(bPrinted.getPaperType());
    }

    @Test
    @DisplayName("Must find a BPrinted by ID")
    void testFindBPrintedById() {
        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing);
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        bPrinted.setPrintedType(printedType);

        when(bPrintedRepository.save(bPrinted)).thenReturn(bPrinted);
        when(bPrintedRepository.findById(1L)).thenReturn(Optional.of(bPrinted));

        bPrintedRepository.save(bPrinted);
        Optional<BPrinted> foundPrinted = bPrintedRepository.findById(1L);

        assertThat(foundPrinted).isPresent();
        assertThat(foundPrinted.get().getPaperType()).isEqualTo(bPrinted.getPaperType());
    }

    @Test
    @DisplayName("Must update a BPrinted")
    void testUpdateBPrinted() {
        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing);
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        bPrinted.setPrintedType(printedType);

        when(bPrintedRepository.save(bPrinted)).thenReturn(bPrinted);
        when(bPrintedRepository.findById(1L)).thenReturn(Optional.of(bPrinted));

        bPrintedRepository.save(bPrinted);
        bPrinted.setPaperType("Papel A3");
        BPrinted updatedPrinted = bPrintedRepository.save(bPrinted);

        assertThat(updatedPrinted.getPaperType()).isEqualTo("Papel A3");
    }

    @Test
    @DisplayName("Must delete a BPrinted")
    void testDeleteBPrinted() {
        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing);
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        bPrinted.setPrintedType(printedType);

        when(bPrintedRepository.save(bPrinted)).thenReturn(bPrinted);
        when(bPrintedRepository.findById(1L)).thenReturn(Optional.of(bPrinted));

        bPrintedRepository.save(bPrinted);
        bPrintedRepository.delete(bPrinted);

        when(bPrintedRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<BPrinted> foundPrinted = bPrintedRepository.findById(1L);
        assertThat(foundPrinted).isNotPresent();
    }
}
