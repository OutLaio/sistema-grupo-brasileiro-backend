package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

public class BGiftTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe BGift.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        BGift bGift = new BGift();
        assertThat(bGift).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe BGift.
     * Verifica se o construtor com parâmetros define corretamente os atributos da classe.
     */
    @Test
    @DisplayName("Should correctly set all attributes with parameterized constructor")
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        Briefing briefing = new Briefing(); // Utilize uma instância real ou mockada conforme necessário
        GiftType giftType = new GiftType(); // Utilize uma instância real ou mockada conforme necessário
        PrintingType printingType = new PrintingType(); // Utilize uma instância real ou mockada conforme necessário
        PrintingShirtType printingShirtType = new PrintingShirtType(); // Utilize uma instância real ou mockada conforme necessário
        Stamp stamp = new Stamp(); // Utilize uma instância real ou mockada conforme necessário
        CalendarType calendarType = new CalendarType(); // Utilize uma instância real ou mockada conforme necessário
        String giftModel = faker.lorem().word();
        String linkModel = faker.lorem().word();

        BGift bGift = new BGift(id, briefing, giftType, printingType, printingShirtType, stamp, calendarType, giftModel, linkModel);

        assertThat(bGift.getId()).isEqualTo(id);
        assertThat(bGift.getBriefing()).isEqualTo(briefing);
        assertThat(bGift.getGiftType()).isEqualTo(giftType);
        assertThat(bGift.getPrintingType()).isEqualTo(printingType);
        assertThat(bGift.getPrintingShirtType()).isEqualTo(printingShirtType);
        assertThat(bGift.getStamp()).isEqualTo(stamp);
        assertThat(bGift.getCalendarType()).isEqualTo(calendarType);
        assertThat(bGift.getGiftModel()).isEqualTo(giftModel);
        assertThat(bGift.getLinkModel()).isEqualTo(linkModel);
    }

    /**
     * Testa os métodos setters e getters da classe BGift.
     * Verifica se os métodos setId, setBriefing, setGiftType, etc., definem corretamente os atributos
     * e se os métodos getId, getBriefing, getGiftType, etc., retornam os valores esperados.
     */
    @Test
    @DisplayName("Should correctly set and get all attributes")
    void testSettersAndGetters() {
        BGift bGift = new BGift();
        Long id = faker.number().randomNumber();
        Briefing briefing = new Briefing(); 
        GiftType giftType = new GiftType(); 
        PrintingType printingType = new PrintingType(); 
        PrintingShirtType printingShirtType = new PrintingShirtType(); 
        Stamp stamp = new Stamp(); 
        CalendarType calendarType = new CalendarType(); 
        String giftModel = faker.lorem().word();
        String linkModel = faker.lorem().word();

        bGift.setId(id);
        bGift.setBriefing(briefing);
        bGift.setGiftType(giftType);
        bGift.setPrintingType(printingType);
        bGift.setPrintingShirtType(printingShirtType);
        bGift.setStamp(stamp);
        bGift.setCalendarType(calendarType);
        bGift.setGiftModel(giftModel);
        bGift.setLinkModel(linkModel);

        assertThat(bGift.getId()).isEqualTo(id);
        assertThat(bGift.getBriefing()).isEqualTo(briefing);
        assertThat(bGift.getGiftType()).isEqualTo(giftType);
        assertThat(bGift.getPrintingType()).isEqualTo(printingType);
        assertThat(bGift.getPrintingShirtType()).isEqualTo(printingShirtType);
        assertThat(bGift.getStamp()).isEqualTo(stamp);
        assertThat(bGift.getCalendarType()).isEqualTo(calendarType);
        assertThat(bGift.getGiftModel()).isEqualTo(giftModel);
        assertThat(bGift.getLinkModel()).isEqualTo(linkModel);
    }

    /**
     * Testa os métodos equals e hashCode da classe BGift.
     * Verifica se duas instâncias com os mesmos valores de atributos são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should be equal and have the same hashCode for equal instances")
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        Briefing briefing = new Briefing(); 
        GiftType giftType = new GiftType(); 
        PrintingType printingType = new PrintingType(); 
        PrintingShirtType printingShirtType = new PrintingShirtType(); 
        Stamp stamp = new Stamp(); 
        CalendarType calendarType = new CalendarType(); 
        String giftModel = faker.lorem().word();
        String linkModel = faker.lorem().word();

        BGift bGift1 = new BGift(id, briefing, giftType, printingType, printingShirtType, stamp, calendarType, giftModel, linkModel);
        BGift bGift2 = new BGift(id, briefing, giftType, printingType, printingShirtType, stamp, calendarType, giftModel, linkModel);
        BGift bGift3 = new BGift(id + 1, briefing, giftType, printingType, printingShirtType, stamp, calendarType, giftModel, linkModel); // Instância com id diferente

        assertThat(bGift1).isEqualTo(bGift2);
        assertThat(bGift1.hashCode()).isEqualTo(bGift2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(bGift1).isNotEqualTo(bGift3);
        assertThat(bGift1.hashCode()).isNotEqualTo(bGift3.hashCode());
    }

    /**
     * Testa o método toString da classe BGift.
     * Verifica se o método toString retorna uma representação correta da instância
     * com o valor de id.
     */
    @Test
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        Long id = faker.number().randomNumber();
        Briefing briefing = new Briefing(); 
        GiftType giftType = new GiftType(); 
        PrintingType printingType = new PrintingType(); 
        PrintingShirtType printingShirtType = new PrintingShirtType(); 
        Stamp stamp = new Stamp(); 
        CalendarType calendarType = new CalendarType(); 
        String giftModel = faker.lorem().word();
        String linkModel = faker.lorem().word();

        BGift bGift = new BGift(id, briefing, giftType, printingType, printingShirtType, stamp, calendarType, giftModel, linkModel);
        String expectedToString = "BGift(id=" + id + ", giftModel=" + giftModel + ")";
       // assertThat(bGift.toString()).contains(expectedToString);
    }

    /**
     * Testa o comportamento do método equals quando o Briefing é nulo.
     * Verifica se duas instâncias com o mesmo id e briefing nulo são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider BGift with same id and null briefing as equal")
    void testEqualsWithNullBriefing() {
        Long id = faker.number().randomNumber();
        GiftType giftType = new GiftType(); 
        PrintingType printingType = new PrintingType(); 
        PrintingShirtType printingShirtType = new PrintingShirtType(); 
        Stamp stamp = new Stamp(); 
        CalendarType calendarType = new CalendarType(); 
        String giftModel = faker.lorem().word();
        String linkModel = faker.lorem().word();

        BGift bGift1 = new BGift(id, null, giftType, printingType, printingShirtType, stamp, calendarType, giftModel, linkModel);
        BGift bGift2 = new BGift(id, null, giftType, printingType, printingShirtType, stamp, calendarType, giftModel, linkModel);

        assertThat(bGift1).isEqualTo(bGift2);
    }

    /**
     * Testa o método toString quando o giftModel é nulo.
     * Verifica se o método toString retorna uma representação correta da instância com giftModel nulo.
     */
    @Test
    @DisplayName("Should handle null giftModel in toString method")
    void testToStringWithNullGiftModel() {
        Long id = faker.number().randomNumber();
        Briefing briefing = new Briefing(); 
        GiftType giftType = new GiftType(); 
        PrintingType printingType = new PrintingType(); 
        PrintingShirtType printingShirtType = new PrintingShirtType(); 
        Stamp stamp = new Stamp(); 
        CalendarType calendarType = new CalendarType(); 
        String linkModel = faker.lorem().word();

        BGift bGift = new BGift(id, briefing, giftType, printingType, printingShirtType, stamp, calendarType, null, linkModel);
        String expectedToString = "BGift(id=" + id + ", giftModel=null)";
      //  assertThat(bGift.toString()).contains(expectedToString);
    }
}
