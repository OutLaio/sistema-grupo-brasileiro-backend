package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
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

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BInternalCampaignRepositoryTest {

    @Autowired
    private BInternalCampaignRepository bInternalCampaignRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StationeryTypeRepository stationeryTypeRepository;

    @Autowired
    private OtherItemRepository otherItemRepository;

    private Briefing briefing;
    private StationeryType stationeryType;
    private OtherItem otherItem;

    @BeforeEach
    void setUp() {
        Profile profile = new Profile();
        profile.setDescription("Perfil de Teste");
        profileRepository.save(profile);

        User userClient = new User();
        userClient.setEmail("cliente@teste.com");
        userClient.setPassword("senha123");
        userClient.setProfile(profile);
        userClient.setDisabled(false);  
        userRepository.save(userClient);

        User userCollaborator = new User();
        userCollaborator.setEmail("colaborador@teste.com");
        userCollaborator.setPassword("senha456");
        userCollaborator.setProfile(profile);
        userCollaborator.setDisabled(false);  
        userRepository.save(userCollaborator);

        Employee client = new Employee();
        client.setName("Cliente Teste");
        client.setLastName("Sobrenome Teste");
        client.setPhoneNumber("123456789");
        client.setSector("Setor Teste");
        client.setOccupation("Ocupação Teste");
        client.setAgency("Agência Teste");
        client.setAvatar(1L);
        client.setUser(userClient);
        employeeRepository.save(client);

        Employee collaborator = new Employee();
        collaborator.setName("Colaborador Teste");
        collaborator.setLastName("Sobrenome Colaborador");
        collaborator.setPhoneNumber("987654321");
        collaborator.setSector("Setor Colaborador");
        collaborator.setOccupation("Ocupação Colaborador");
        collaborator.setAgency("Agência Colaborador");
        collaborator.setAvatar(2L);
        collaborator.setUser(userCollaborator); 
        employeeRepository.save(collaborator);

        // O resto do método permanece o mesmo
        Project project = new Project();
        project.setTitle("Projeto Teste");
        project.setClient(client);
        project.setCollaborator(collaborator);
        project.setStatus("Em andamento");
        project.setDisabled(false);
        projectRepository.save(project);

        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing Teste");
        briefingTypeRepository.save(briefingType);

        briefing = new Briefing();
        briefing.setDetailedDescription("Descrição detalhada do briefing");
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(7));
        briefingRepository.save(briefing);

        stationeryType = new StationeryType();
        stationeryType.setDescription("Tipo de Papelaria Teste");
        stationeryTypeRepository.save(stationeryType);

        otherItem = new OtherItem();
        otherItem.setDescription("Outro Item Teste");
        otherItemRepository.save(otherItem);
    }

    private BInternalCampaign createBInternalCampaign(String motto) {
        BInternalCampaign campaign = new BInternalCampaign();
        campaign.setStationeryType(stationeryType);
        campaign.setOtherItem(otherItem);
        campaign.setBriefing(briefing);
        campaign.setCampaignMotto(motto);
        return campaign;
    }

    
    @Test
    @DisplayName("Must update a BInternalCampaign")
    void testUpdateBInternalCampaign() {
        BInternalCampaign campaign = createBInternalCampaign("Motto da campanha interna");
        BInternalCampaign savedCampaign = bInternalCampaignRepository.save(campaign);

        savedCampaign.setCampaignMotto("Motto atualizado");
        BInternalCampaign updatedCampaign = bInternalCampaignRepository.save(savedCampaign);

        assertThat(updatedCampaign.getCampaignMotto()).isEqualTo("Motto atualizado");
    }

    @Test
    @DisplayName("Must delete a BInternalCampaign")
    void testDeleteBInternalCampaign() {
        BInternalCampaign campaign = createBInternalCampaign("Motto da campanha interna");
        BInternalCampaign savedCampaign = bInternalCampaignRepository.save(campaign);

        bInternalCampaignRepository.delete(savedCampaign);
        Optional<BInternalCampaign> retrievedCampaign = bInternalCampaignRepository.findById(savedCampaign.getId());

        assertThat(retrievedCampaign).isNotPresent();
    }

  
}