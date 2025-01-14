package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.*;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
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
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BGiftRepositoryTest {

    @Mock
    private BriefingRepository briefingRepository;

    @Mock
    private BriefingTypeRepository briefingTypeRepository;

    @Mock
    private GiftTypeRepository giftTypeRepository;

    @Mock
    private PrintingShirtTypeRepository printingShirtTypeRepository;

    @Mock
    private StampRepository stampRepository;

    @Mock
    private CalendarTypeRepository calendarTypeRepository;

    @Mock
    private BGiftRepository bGiftRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    private BGift createSampleBGiftWithAssociations() {
        Profile profile = new Profile();
        profile.setDescription("Perfil Teste");
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        User user = new User();
        user.setEmail("cliente.teste" + System.nanoTime() + "@example.com");
        user.setPassword("senha123");
        user.setDisabled(false);
        user.setProfile(profile);
        when(userRepository.save(any(User.class))).thenReturn(user);

        Employee client = new Employee();
        client.setName("Cliente");
        client.setLastName("Teste");
        client.setAgency("Agência Teste");
        client.setOccupation("Ocupação Teste");
        client.setPhoneNumber("(11) 99999-9999");
        client.setSector("Setor Teste");
        client.setUser(user);
        when(employeeRepository.save(any(Employee.class))).thenReturn(client);

        Project project = new Project();
        project.setTitle("Projeto Teste");
        project.setClient(client);
        project.setDisabled(false);
        project.setStatus("Em andamento");
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing Teste");
        when(briefingTypeRepository.save(any(BriefingType.class))).thenReturn(briefingType);

        Briefing briefing = new Briefing();
        briefing.setDetailedDescription(faker.lorem().sentence());
        briefing.setStartTime(LocalDateTime.now().toLocalDate());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(7).toLocalDate());
        briefing.setOtherCompany(faker.company().name());
        briefing.setBriefingType(briefingType);
        briefing.setProject(project);
        when(briefingRepository.save(any(Briefing.class))).thenReturn(briefing);

        GiftType giftType = new GiftType();
        giftType.setDescription(faker.commerce().productName());
        when(giftTypeRepository.save(any(GiftType.class))).thenReturn(giftType);

        PrintingType printingType = new PrintingType();
        printingType.setDescription("Tipo de Impressão Teste");

        PrintingShirtType printingShirtType = new PrintingShirtType();
        printingShirtType.setDescription(faker.lorem().word());
        when(printingShirtTypeRepository.save(any(PrintingShirtType.class))).thenReturn(printingShirtType);

        Stamp stamp = new Stamp();
        stamp.setDescription(faker.lorem().word());
        when(stampRepository.save(any(Stamp.class))).thenReturn(stamp);

        CalendarType calendarType = new CalendarType();
        calendarType.setDescription(faker.lorem().word());
        when(calendarTypeRepository.save(any(CalendarType.class))).thenReturn(calendarType);

        BGift bGift = new BGift();
        bGift.setBriefing(briefing);
        bGift.setGiftType(giftType);
        bGift.setPrintingType(printingType);
        bGift.setPrintingShirtType(printingShirtType);
        bGift.setStamp(stamp);
        bGift.setCalendarType(calendarType);
        bGift.setGiftModel(faker.commerce().productName());
        bGift.setLinkModel(faker.internet().url());

        when(bGiftRepository.save(any(BGift.class))).thenReturn(bGift);
        return bGift;
    }

    @Test
    @DisplayName("Should update a BGift")
    void testUpdateBGift() {
        BGift bGift = createSampleBGiftWithAssociations();
        when(bGiftRepository.findById(anyLong())).thenReturn(Optional.of(bGift));

        bGift.setGiftModel("Modelo Atualizado");
        when(bGiftRepository.save(any(BGift.class))).thenReturn(bGift);

        BGift updatedBGift = bGiftRepository.save(bGift);

        assertThat(updatedBGift.getGiftModel()).isEqualTo("Modelo Atualizado");
    }

    @Test
    @DisplayName("Should delete a BGift")
    void testDeleteBGift() {
        BGift bGift = createSampleBGiftWithAssociations();
        when(bGiftRepository.findById(anyLong())).thenReturn(Optional.of(bGift));

        bGiftRepository.delete(bGift);
        when(bGiftRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<BGift> foundBGift = bGiftRepository.findById(bGift.getId());
        assertThat(foundBGift).isNotPresent();
    }
}
