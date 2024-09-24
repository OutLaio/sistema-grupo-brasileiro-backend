package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

public class CalendarTypeTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe CalendarType.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        CalendarType calendarType = new CalendarType();
        assertThat(calendarType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe CalendarType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        CalendarType calendarType = new CalendarType(id, description);
        assertThat(calendarType.getId()).isEqualTo(id);
        assertThat(calendarType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos setters e getters da classe CalendarType.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    void testSettersAndGetters() {
        CalendarType calendarType = new CalendarType();
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        calendarType.setId(id);
        calendarType.setDescription(description);

        assertThat(calendarType.getId()).isEqualTo(id);
        assertThat(calendarType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos equals e hashCode da classe CalendarType.
     * Verifica se duas instâncias com os mesmos valores de id e description são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        CalendarType calendarType1 = new CalendarType(id, description);
        CalendarType calendarType2 = new CalendarType(id, description);
        CalendarType calendarType3 = new CalendarType(id + 1, description); // Instância com id diferente

        assertThat(calendarType1).isEqualTo(calendarType2);
        assertThat(calendarType1.hashCode()).isEqualTo(calendarType2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(calendarType1).isNotEqualTo(calendarType3);
        assertThat(calendarType1.hashCode()).isNotEqualTo(calendarType3.hashCode());
    }

    /**
     * Testa o método toString da classe CalendarType.
     * Verifica se o método toString retorna uma representação correta da instância
     * com o valor do id.
     */
    @Test
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        CalendarType calendarType = new CalendarType(id, description);
        String expectedToString = "CalendarType(id=" + id + ")";
        assertThat(calendarType.toString()).contains(expectedToString);
    }
}
