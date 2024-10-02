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


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class BPrintedRepositoryTest {

    @Autowired
    private BPrintedRepository bPrintedRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private PrintedTypeRepository printedTypeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private Briefing briefing;
    private PrintedType printedType;
    private Project project;

    @BeforeEach
    void setUp() {
        // Limpar todos os repositórios
        bPrintedRepository.deleteAll();
        briefingRepository.deleteAll();
        printedTypeRepository.deleteAll();
        projectRepository.deleteAll();
        employeeRepository.deleteAll();
        userRepository.deleteAll();
        profileRepository.deleteAll();

        // Criar e salvar um Profile
        Profile profile = new Profile();
        profile.setDescription("TEST_PROFILE");
        profile = profileRepository.save(profile);

        // Criar e salvar um User
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setDisabled(false);
        user.setProfile(profile);
        user = userRepository.save(user);

        // Criar e salvar um Employee
        Employee employee = new Employee();
        employee.setName("Colaborador");
        employee.setLastName("Teste"); // Note que é setLastName, não setLastname
        employee.setAvatar(1L);
        employee.setUser(user);
        employee.setAgency("Agência Teste");
        employee.setOccupation("Cargo Teste");
        employee.setPhoneNumber("(11) 99999-9999");
        employee.setSector("Setor Teste");
        
        System.out.println("Employee antes de salvar: " + employee);
        
        employee = employeeRepository.save(employee);
        
        System.out.println("Employee após salvar: " + employee);

        // Criar e salvar um Project
        project = new Project();
        project.setTitle("Projeto 1");
        project.setDisabled(false);
        project.setClient(employee);
        project = projectRepository.save(project);

        // Criar e salvar um PrintedType
        printedType = new PrintedType();
        printedType.setDescription("Tipo de Impressão 1");
        printedType = printedTypeRepository.save(printedType);

        // Criar e salvar um BriefingType
        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing Teste");
        briefingType = briefingTypeRepository.save(briefingType);

        // Criar e salvar um Briefing
        briefing = new Briefing();
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(7));
        briefing.setDetailedDescription("Descrição do briefing");
        briefing = briefingRepository.save(briefing);
    }
    
    @Test
    @DisplayName("Must save a BPrinted correctly")
    void testSaveBPrinted() {
        assertThat(briefing).isNotNull();
        assertThat(briefing.getId()).isNotNull();
        System.out.println("Briefing: " + briefing);

        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing);
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        bPrinted.setPrintedType(printedType); // Certifique-se de que este campo está sendo preenchido

        // Verifique se há outros campos obrigatórios e preencha-os aqui

        System.out.println("BPrinted antes de salvar: " + bPrinted);

        try {
            BPrinted savedPrinted = bPrintedRepository.save(bPrinted);
            System.out.println("BPrinted após salvar: " + savedPrinted);

            assertThat(savedPrinted).isNotNull();
            assertThat(savedPrinted.getId()).isNotNull();
            assertThat(savedPrinted.getPaperType()).isEqualTo(bPrinted.getPaperType());
            assertThat(savedPrinted.getFolds()).isEqualTo(bPrinted.getFolds());
            assertThat(savedPrinted.getPages()).isEqualTo(bPrinted.getPages());
        } catch (Exception e) {
            System.err.println("Erro ao salvar BPrinted: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    @Test
    @DisplayName("Must find a BPrinted by ID")
    void testFindBPrintedById() {
        // Verificar se o printedType foi criado corretamente no setUp
        assertThat(printedType).isNotNull();
        assertThat(printedType.getId()).isNotNull();
        System.out.println("PrintedType: " + printedType);

        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing);
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        bPrinted.setPrintedType(printedType); 

        System.out.println("BPrinted antes de salvar: " + bPrinted);

        try {
            BPrinted savedPrinted = bPrintedRepository.save(bPrinted);
            System.out.println("BPrinted após salvar: " + savedPrinted);

            Optional<BPrinted> foundPrinted = bPrintedRepository.findById(savedPrinted.getId());

            assertThat(foundPrinted).isPresent();
            assertThat(foundPrinted.get().getId()).isEqualTo(savedPrinted.getId());
            assertThat(foundPrinted.get().getPrintedType()).isEqualTo(printedType);
            
            System.out.println("BPrinted encontrado: " + foundPrinted.get());
        } catch (Exception e) {
            System.err.println("Erro ao salvar ou encontrar BPrinted: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    @DisplayName("Must update a BPrinted")
    void testUpdateBPrinted() {
        // Verificar se o printedType foi criado corretamente no setUp
        assertThat(printedType).isNotNull();
        assertThat(printedType.getId()).isNotNull();
        System.out.println("PrintedType: " + printedType);

        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing);
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        bPrinted.setPrintedType(printedType); // Adicione esta linha

        System.out.println("BPrinted antes de salvar: " + bPrinted);

        try {
            BPrinted savedPrinted = bPrintedRepository.save(bPrinted);
            System.out.println("BPrinted após salvar: " + savedPrinted);

            savedPrinted.setPaperType("Papel A3");
            BPrinted updatedPrinted = bPrintedRepository.save(savedPrinted);
            System.out.println("BPrinted após atualizar: " + updatedPrinted);

            assertThat(updatedPrinted.getPaperType()).isEqualTo("Papel A3");
        } catch (Exception e) {
            System.err.println("Erro ao salvar ou atualizar BPrinted: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    @Test
    @DisplayName("Must delete a BPrinted")
    void testDeleteBPrinted() {
        // Verificar se o printedType foi criado corretamente no setUp
        assertThat(printedType).isNotNull();
        assertThat(printedType.getId()).isNotNull();
        System.out.println("PrintedType: " + printedType);

        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing);
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        bPrinted.setPrintedType(printedType); // Adicione esta linha

        System.out.println("BPrinted antes de salvar: " + bPrinted);

        try {
            BPrinted savedPrinted = bPrintedRepository.save(bPrinted);
            System.out.println("BPrinted após salvar: " + savedPrinted);

            bPrintedRepository.delete(savedPrinted);
            System.out.println("BPrinted deletado");

            Optional<BPrinted> foundPrinted = bPrintedRepository.findById(savedPrinted.getId());

            assertThat(foundPrinted).isNotPresent();
            System.out.println("BPrinted não encontrado após deleção");
        } catch (Exception e) {
            System.err.println("Erro ao salvar, deletar ou buscar BPrinted: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


}