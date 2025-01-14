package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import jakarta.transaction.Transactional;

    @SpringBootTest
    @ActiveProfiles("test")
    @Transactional
    public class BAgencyBoardRepositoryTest {

        @Autowired
        private BAgencyBoardRepository bAgencyBoardRepository;

        @Autowired
        private AgencyBoardTypeRepository agencyBoardTypeRepository;

        @Autowired
        private BoardTypeRepository boardTypeRepository;

        @Autowired
        private BriefingRepository briefingRepository;

        @Autowired
        private BriefingTypeRepository briefingTypeRepository;

        @Autowired
        private EmployeeRepository employeeRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ProfileRepository profileRepository;

        @Autowired
        private ProjectRepository projectRepository;

        // Objetos mock
        private AgencyBoardType agencyBoardType;
        private BoardType boardType;
        private Briefing briefing;
        private BriefingType briefingType;

        @BeforeEach
        void setUp() {
            try {
                Profile profile = createAndSaveProfile("Perfil de Exemplo");
                
                User collaboratorUser = createUser(profile, "colaborador@example.com");
                Employee collaborator = createAndSaveEmployee("Colaborador de Teste", "Sobrenome do Colaborador", "123456789", "Setor do Colaborador", "Ocupação do Colaborador", "Agência do Colaborador", 1L, collaboratorUser);
                
                User clientUser = createUser(profile, "cliente@example.com");
                Employee client = createAndSaveEmployee("Cliente de Teste", "Sobrenome do Cliente", "987654321", "Setor do Cliente", "Ocupação do Cliente", "Agência do Cliente", 2L, clientUser);

                agencyBoardType = createAndSaveAgencyBoardType("Tipo de Quadro de Agência");
                boardType = createAndSaveBoardType("Tipo de Quadro");
                briefingType = createAndSaveBriefingType("Tipo de Briefing");

                Project project1 = createAndSaveProject("Projeto de Teste 1", client, collaborator);
                briefing = createAndSaveBriefing(project1, briefingType, "Descrição detalhada do briefing 1");

                Project project2 = createAndSaveProject("Projeto de Teste 2", client, collaborator);
                createAndSaveBriefing(project2, briefingType, "Descrição detalhada do briefing 2");

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Falha na configuração do teste: " + e.getMessage());
            }
        }

        private Profile createAndSaveProfile(String description) {
            Profile profile = new Profile();
            profile.setDescription(description);
            return profileRepository.save(profile);
        }

        private User createUser(Profile profile, String email) {
            User user = new User();
            user.setProfile(profile);
            user.setEmail(email);
            user.setDisabled(false);
            user.setPassword("senha123");
            return userRepository.save(user);
        }

        private Employee createAndSaveEmployee(String name, String lastName, String phoneNumber, String sector, String occupation, String agency, Long avatar, User user) {
            Employee employee = new Employee();
            employee.setName(name);
            employee.setLastName(lastName);
            employee.setPhoneNumber(phoneNumber);
            employee.setSector(sector);
            employee.setOccupation(occupation);
            employee.setAgency(agency);
            employee.setAvatar(avatar);
            employee.setUser(user);
            return employeeRepository.save(employee);
        }

        private AgencyBoardType createAndSaveAgencyBoardType(String description) {
            AgencyBoardType type = new AgencyBoardType();
            type.setDescription(description);
            return agencyBoardTypeRepository.save(type);
        }

        private BoardType createAndSaveBoardType(String description) {
            BoardType type = new BoardType();
            type.setDescription(description);
            return boardTypeRepository.save(type);
        }

        private BriefingType createAndSaveBriefingType(String description) {
            BriefingType type = new BriefingType();
            type.setDescription(description);
            return briefingTypeRepository.save(type);
        }

        private Project createAndSaveProject(String title, Employee client, Employee collaborator) {
            Project project = new Project();
            project.setTitle(title);
            project.setDisabled(false);
            project.setClient(client);
            project.setCollaborator(collaborator);
            return projectRepository.save(project);
        }

        private Briefing createAndSaveBriefing(Project project, BriefingType briefingType, String description) {
            Briefing briefing = new Briefing();
            briefing.setProject(project);
            briefing.setBriefingType(briefingType);
            briefing.setStartTime(LocalDate.now());
            briefing.setExpectedTime(LocalDate.now().plusDays(7));
            briefing.setDetailedDescription(description);
            return briefingRepository.save(briefing);
        }
}