package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.CalendarType;

@DataJpaTest
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
}
