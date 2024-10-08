package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mapping.AccessOptions.SetOptions.Propagation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.*;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.*;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.*;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.*;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BGiftRepositoryTest {
   
    @Autowired private BriefingRepository briefingRepository;
    @Autowired private BriefingTypeRepository briefingTypeRepository;
    @Autowired private GiftTypeRepository giftTypeRepository;
    @Autowired private PrintingShirtTypeRepository printingShirtTypeRepository;
    @Autowired private StampRepository stampRepository;
    @Autowired private CalendarTypeRepository calendarTypeRepository;
    @Autowired private BGiftRepository bGiftRepository;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private ProfileRepository profileRepository;
    @Autowired private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
           }
    
  
   
    private BGift createSampleBGiftWithAssociations() {
        Profile profile = new Profile();
        profile.setDescription("Perfil Teste");  
        Profile savedProfile = profileRepository.save(profile);

        User user = new User();
        user.setEmail("cliente.teste" + System.nanoTime() + "@example.com");
        user.setPassword("senha123");
        user.setDisabled(false);
        user.setProfile(savedProfile);
        User savedUser = userRepository.save(user);

        Employee client = new Employee();
        client.setName("Cliente");
        client.setLastName("Teste");  
        client.setAgency("Agência Teste");
        client.setOccupation("Ocupação Teste");
        client.setPhoneNumber("(11) 99999-9999");
        client.setSector("Setor Teste");
        client.setUser(savedUser);
        client.setAvatar(1L);
        Employee savedClient = employeeRepository.save(client);
        
        Project project = new Project();
        project.setTitle("Projeto Teste");
        project.setClient(savedClient);
        project.setDisabled(false);
        project.setStatus("Em andamento");
        Project savedProject = projectRepository.save(project);

        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing Teste");
        BriefingType savedBriefingType = briefingTypeRepository.save(briefingType);

        Briefing briefing = new Briefing();
        briefing.setDetailedDescription(faker.lorem().sentence());
        briefing.setStartTime(LocalDateTime.now().toLocalDate());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(7).toLocalDate());
        briefing.setOtherCompany(faker.company().name());
        briefing.setBriefingType(savedBriefingType);
        briefing.setProject(savedProject);
        Briefing savedBriefing = briefingRepository.save(briefing);

        GiftType giftType = new GiftType();
        giftType.setDescription(faker.commerce().productName());
        GiftType savedGiftType = giftTypeRepository.save(giftType);

        PrintingType printingType = new PrintingType();
        printingType.setDescription("Tipo de Impressão Teste");
        entityManager.persist(printingType);

        PrintingShirtType printingShirtType = new PrintingShirtType();
        printingShirtType.setDescription(faker.lorem().word());
        PrintingShirtType savedPrintingShirtType = printingShirtTypeRepository.save(printingShirtType);

        Stamp stamp = new Stamp();
        stamp.setDescription(faker.lorem().word());
        Stamp savedStamp = stampRepository.save(stamp);

        CalendarType calendarType = new CalendarType();
        calendarType.setDescription(faker.lorem().word());
        CalendarType savedCalendarType = calendarTypeRepository.save(calendarType);

        BGift bGift = new BGift();
        bGift.setBriefing(savedBriefing);
        bGift.setGiftType(savedGiftType);
        bGift.setPrintingType(printingType);
        bGift.setPrintingShirtType(savedPrintingShirtType);
        bGift.setStamp(savedStamp);
        bGift.setCalendarType(savedCalendarType);
        bGift.setGiftModel(faker.commerce().productName());
        bGift.setLinkModel(faker.internet().url());

        return bGiftRepository.save(bGift);
    }

    @Test
    @DisplayName("Should create a BGift")
    public void testCreateBGift() {
        BGift createdBGift = createSampleBGiftWithAssociations();
        assertThat(createdBGift.getId()).isNotNull();
        assertThat(bGiftRepository.count()).isEqualTo(1);
    }

  
    @Test
    @DisplayName("Should update a BGift")
    void testUpdateBGift() {
        BGift bGift = createSampleBGiftWithAssociations();
        BGift savedBGift = bGiftRepository.save(bGift);

        savedBGift.setGiftModel("Modelo Atualizado");
        BGift updatedBGift = bGiftRepository.save(savedBGift);

        assertThat(updatedBGift.getGiftModel()).isEqualTo("Modelo Atualizado");
    }

    @Test
    @DisplayName("Should delete a BGift")
    void testDeleteBGift() {
        BGift bGift = createSampleBGiftWithAssociations();

        bGiftRepository.delete(bGift);
        Optional<BGift> foundBGift = bGiftRepository.findById(bGift.getId());

        assertThat(foundBGift).isNotPresent();
    }

   
}