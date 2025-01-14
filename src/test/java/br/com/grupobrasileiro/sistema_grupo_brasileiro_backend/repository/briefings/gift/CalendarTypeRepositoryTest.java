package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.CalendarType;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class CalendarTypeRepositoryTest {

    @Mock
    private CalendarTypeRepository calendarTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should save and find CalendarType correctly")
    void testSaveAndFindCalendarType() {
        CalendarType calendarType = new CalendarType();
        calendarType.setId(1L);
        calendarType.setDescription(faker.lorem().sentence());

        when(calendarTypeRepository.save(any(CalendarType.class))).thenReturn(calendarType);
        when(calendarTypeRepository.findById(1L)).thenReturn(Optional.of(calendarType));

        CalendarType savedType = calendarTypeRepository.save(calendarType);
        Optional<CalendarType> foundType = calendarTypeRepository.findById(1L);

        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(calendarType.getDescription());
    }

    @Test
    @DisplayName("Should update a CalendarType")
    void testUpdateCalendarType() {
        CalendarType calendarType = new CalendarType();
        calendarType.setDescription(faker.lorem().sentence());

        when(calendarTypeRepository.save(any(CalendarType.class))).thenReturn(calendarType);

        calendarType.setDescription("Descrição Atualizada");
        CalendarType updatedType = calendarTypeRepository.save(calendarType);

        assertThat(updatedType.getDescription()).isEqualTo("Descrição Atualizada");
    }

    @Test
    @DisplayName("Should delete a CalendarType")
    void testDeleteCalendarType() {
        CalendarType calendarType = new CalendarType();
        calendarType.setDescription(faker.lorem().sentence());

        doNothing().when(calendarTypeRepository).delete(calendarType);
        when(calendarTypeRepository.findById(calendarType.getId())).thenReturn(Optional.empty());

        calendarTypeRepository.delete(calendarType);
        Optional<CalendarType> foundType = calendarTypeRepository.findById(calendarType.getId());

        assertThat(foundType).isNotPresent();
    }

    @Test
    @DisplayName("Should retrieve all CalendarTypes")
    void testFindAllCalendarTypes() {
        CalendarType calendarType1 = new CalendarType();
        calendarType1.setDescription(faker.lorem().sentence());
        CalendarType calendarType2 = new CalendarType();
        calendarType2.setDescription(faker.lorem().sentence());

        when(calendarTypeRepository.findAll()).thenReturn(List.of(calendarType1, calendarType2));

        List<CalendarType> allCalendarTypes = (List<CalendarType>) calendarTypeRepository.findAll();

        assertThat(allCalendarTypes).hasSize(2);
        assertThat(allCalendarTypes).extracting(CalendarType::getDescription)
                                   .containsExactlyInAnyOrder(calendarType1.getDescription(), calendarType2.getDescription());
    }

    @Test
    @DisplayName("Should return empty when CalendarType not found")
    void testFindCalendarTypeNotFound() {
        when(calendarTypeRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<CalendarType> foundType = calendarTypeRepository.findById(999L);

        assertThat(foundType).isNotPresent();
    }
}
