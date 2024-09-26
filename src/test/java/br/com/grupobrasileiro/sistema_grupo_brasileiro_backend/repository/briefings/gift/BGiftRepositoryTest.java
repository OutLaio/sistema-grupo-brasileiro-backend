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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
public class BGiftRepositoryTest {

    @Autowired
    private BGiftRepository bGiftRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a persistência e recuperação de um BGift com todos os relacionamentos.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find BGift with all associations correctly")
    void testSaveAndFindBGift() {
        // Arrange
        BGift bGift = createSampleBGift();

        // Act
        BGift savedBGift = bGiftRepository.save(bGift);

        // Assert
        Optional<BGift> foundBGift = bGiftRepository.findById(savedBGift.getId());
        assertThat(foundBGift).isPresent();
        assertThat(foundBGift.get()).usingRecursiveComparison().isEqualTo(savedBGift);
    }

    /**
     * Testa a atualização de um BGift.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update a BGift")
    void testUpdateBGift() {
        // Arrange
        BGift bGift = createSampleBGift();
        BGift savedBGift = bGiftRepository.save(bGift);

        // Act - Atualiza o modelo do presente
        savedBGift.setGiftModel("Modelo Atualizado");
        BGift updatedBGift = bGiftRepository.save(savedBGift);

        // Assert
        assertThat(updatedBGift.getGiftModel()).isEqualTo("Modelo Atualizado");
    }

    /**
     * Testa a exclusão de um BGift.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a BGift")
    void testDeleteBGift() {
        // Arrange
        BGift bGift = createSampleBGift();
        BGift savedBGift = bGiftRepository.save(bGift);

        // Act
        bGiftRepository.delete(savedBGift);
        Optional<BGift> foundBGift = bGiftRepository.findById(savedBGift.getId());

        // Assert
        assertThat(foundBGift).isNotPresent();
    }

    /**
     * Testa a recuperação de todos os BGifts.
     */
    @Test
    @DisplayName("Should retrieve all BGifts")
    void testFindAllBGifts() {
        // Arrange
        BGift bGift1 = createSampleBGift();
        BGift bGift2 = createSampleBGift();
        bGiftRepository.save(bGift1);
        bGiftRepository.save(bGift2);

        // Act
        Iterable<BGift> allBGifts = bGiftRepository.findAll();

        // Assert
        assertThat(allBGifts).hasSize(2);
    }

    private BGift createSampleBGift() {
        // Criação do briefing
        Briefing briefing = new Briefing();
        briefing.setDetailedDescription(faker.lorem().sentence());
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(10));
        briefing.setOtherCompany(faker.company().name());

        GiftType giftType = new GiftType();
        giftType.setDescription(faker.commerce().productName());

        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());

        PrintingShirtType printingShirtType = new PrintingShirtType();
        printingShirtType.setDescription(faker.lorem().word());

        Stamp stamp = new Stamp();
        stamp.setDescription(faker.lorem().word());

        CalendarType calendarType = new CalendarType();
        calendarType.setDescription(faker.lorem().word());

        BGift bGift = new BGift();
        bGift.setBriefing(briefing);
        bGift.setGiftType(giftType);
        bGift.setPrintingType(printingType);
        bGift.setPrintingShirtType(printingShirtType);
        bGift.setStamp(stamp);
        bGift.setCalendarType(calendarType);
        bGift.setGiftModel(faker.commerce().productName());
        bGift.setLinkModel(faker.internet().url());

        return bGift;
    }
}
