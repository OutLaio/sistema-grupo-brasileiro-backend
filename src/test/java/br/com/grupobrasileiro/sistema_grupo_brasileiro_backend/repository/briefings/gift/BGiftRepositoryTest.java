package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.CalendarType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.GiftType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.PrintingShirtType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.Stamp;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.CalendarType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.GiftType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.PrintingShirtType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.Stamp;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BGiftRepositoryTest {

    @Autowired
    private BGiftRepository bGiftRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private GiftTypeRepository giftTypeRepository;

    @Autowired
    private PrintingTypeRepository printingTypeRepository;

    @Autowired
    private PrintingShirtTypeRepository printingShirtTypeRepository;

    @Autowired
    private StampRepository stampRepository;

    @Autowired
    private CalendarTypeRepository calendarTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    private BGift createSampleBGiftWithAssociations() {
        // Criação do briefing
        Briefing briefing = new Briefing();
        briefing.setDetailedDescription(faker.lorem().sentence());
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(10));
        briefing.setOtherCompany(faker.company().name());

        Briefing savedBriefing = briefingRepository.save(briefing);

        // Criação e salvamento do GiftType
        GiftType giftType = new GiftType();
        giftType.setDescription(faker.commerce().productName());
        GiftType savedGiftType = giftTypeRepository.save(giftType);

        // Criação e salvamento do PrintingType
        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());
        PrintingType savedPrintingType = printingTypeRepository.save(printingType);

        // Criação e salvamento do PrintingShirtType
        PrintingShirtType printingShirtType = new PrintingShirtType();
        printingShirtType.setDescription(faker.lorem().word());
        PrintingShirtType savedPrintingShirtType = printingShirtTypeRepository.save(printingShirtType);

        // Criação e salvamento do Stamp
        Stamp stamp = new Stamp();
        stamp.setDescription(faker.lorem().word());
        Stamp savedStamp = stampRepository.save(stamp);

        // Criação e salvamento do CalendarType
        CalendarType calendarType = new CalendarType();
        calendarType.setDescription(faker.lorem().word());
        CalendarType savedCalendarType = calendarTypeRepository.save(calendarType);

        // Criação do BGift com as entidades salvas
        BGift bGift = new BGift();
        bGift.setBriefing(savedBriefing);
        bGift.setGiftType(savedGiftType);
        bGift.setPrintingType(savedPrintingType);
        bGift.setPrintingShirtType(savedPrintingShirtType);
        bGift.setStamp(savedStamp);
        bGift.setCalendarType(savedCalendarType);
        bGift.setGiftModel(faker.commerce().productName());
        bGift.setLinkModel(faker.internet().url());

        return bGift;
    }

    @Test
    @Rollback(false) // Remova essa anotação se quiser que o teste não faça rollback
    @DisplayName("Should save and find BGift with all associations correctly")
    void testSaveAndFindBGift() {
        // Arrange
        BGift bGift = createSampleBGiftWithAssociations();

        // Act
        BGift savedBGift = bGiftRepository.save(bGift);

        // Assert
        Optional<BGift> foundBGift = bGiftRepository.findById(savedBGift.getId());
        assertThat(foundBGift).isPresent();
        assertThat(foundBGift.get()).usingRecursiveComparison().isEqualTo(savedBGift);
    }

    @Test
    @Rollback(false)
    @DisplayName("Should update a BGift")
    void testUpdateBGift() {
        // Arrange
        BGift bGift = createSampleBGiftWithAssociations();
        BGift savedBGift = bGiftRepository.save(bGift);

        // Act
        savedBGift.setGiftModel("Modelo Atualizado");
        BGift updatedBGift = bGiftRepository.save(savedBGift);

        // Assert
        assertThat(updatedBGift.getGiftModel()).isEqualTo("Modelo Atualizado");
    }

    @Test
    @Rollback(false)
    @DisplayName("Should delete a BGift")
    void testDeleteBGift() {
        // Arrange
        BGift bGift = createSampleBGiftWithAssociations();
        BGift savedBGift = bGiftRepository.save(bGift);

        // Act
        bGiftRepository.delete(savedBGift);
        Optional<BGift> foundBGift = bGiftRepository.findById(savedBGift.getId());

        // Assert
        assertThat(foundBGift).isNotPresent();
    }

    @Test
    @DisplayName("Should retrieve all BGifts")
    void testFindAllBGifts() {
        // Arrange
        BGift bGift1 = createSampleBGiftWithAssociations();
        BGift bGift2 = createSampleBGiftWithAssociations();
        bGiftRepository.save(bGift1);
        bGiftRepository.save(bGift2);

        // Act
        Iterable<BGift> allBGifts = bGiftRepository.findAll();

        // Assert
        assertThat(allBGifts).hasSize(2);
    }
}
