package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.CalendarType;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CalendarTypeRepositoryTest {

    @Autowired
    private CalendarTypeRepository calendarTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a persistência e recuperação de um CalendarType.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find CalendarType correctly")
    void testSaveAndFindCalendarType() {
        // Arrange
        CalendarType calendarType = new CalendarType();
        calendarType.setDescription(faker.lorem().sentence());

        // Act
        CalendarType savedType = calendarTypeRepository.save(calendarType);

        // Assert
        Optional<CalendarType> foundType = calendarTypeRepository.findById(savedType.getId());
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(calendarType.getDescription());
    }

    /**
     * Testa a atualização de um CalendarType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update a CalendarType")
    void testUpdateCalendarType() {
        // Arrange
        CalendarType calendarType = new CalendarType();
        calendarType.setDescription(faker.lorem().sentence());
        CalendarType savedType = calendarTypeRepository.save(calendarType);

        // Act - Atualiza a descrição do tipo de calendário
        savedType.setDescription("Descrição Atualizada");
        CalendarType updatedType = calendarTypeRepository.save(savedType);

        // Assert
        assertThat(updatedType.getDescription()).isEqualTo("Descrição Atualizada");
    }

    /**
     * Testa a exclusão de um CalendarType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a CalendarType")
    void testDeleteCalendarType() {
        // Arrange
        CalendarType calendarType = new CalendarType();
        calendarType.setDescription(faker.lorem().sentence());
        CalendarType savedType = calendarTypeRepository.save(calendarType);

        // Act
        calendarTypeRepository.delete(savedType);
        Optional<CalendarType> foundType = calendarTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(foundType).isNotPresent();
    }

    /**
     * Testa a recuperação de todos os CalendarTypes.
     */
    @Test
    @DisplayName("Should retrieve all CalendarTypes")
    void testFindAllCalendarTypes() {
        // Arrange
        CalendarType calendarType1 = new CalendarType();
        calendarType1.setDescription(faker.lorem().sentence());
        CalendarType calendarType2 = new CalendarType();
        calendarType2.setDescription(faker.lorem().sentence());
        calendarTypeRepository.save(calendarType1);
        calendarTypeRepository.save(calendarType2);

        // Act
        Iterable<CalendarType> allCalendarTypes = calendarTypeRepository.findAll();

        // Assert
        assertThat(allCalendarTypes).hasSize(2);
    }
}
