package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;

import java.time.LocalDateTime;

public class DialogBoxTest {

    private DialogBox dialogBox;

    @BeforeEach
    void setUp() {
        // Inicializa uma nova instância de DialogBox antes de cada teste
        dialogBox = new DialogBox();
    }

    /**
     * Testa o construtor padrão da classe DialogBox.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create an instance with the default constructor")
    void testDefaultConstructor() {
        assertThat(dialogBox).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe DialogBox.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, employee, briefing, time e dialog.
     */
    @Test
    @DisplayName("Should set properties correctly with the parameterized constructor")
    void testParameterizedConstructor() {
        Long id = 1L;

        Employee employee = createSampleEmployee();
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        LocalDateTime time = LocalDateTime.now();
        String dialogContent = "This is a test dialog";

        DialogBox dialogBox = new DialogBox(id, employee, briefing, time, dialogContent);

        assertThat(dialogBox.getId()).isEqualTo(id);
        assertThat(dialogBox.getEmployee()).isEqualTo(employee);
        assertThat(dialogBox.getBriefing()).isEqualTo(briefing);
        assertThat(dialogBox.getTime()).isEqualTo(time);
        assertThat(dialogBox.getDialog()).isEqualTo(dialogContent);
    }

    /**
     * Testa os métodos setters e getters da classe DialogBox.
     * Verifica se os métodos setId, setEmployee, setBriefing, setTime e setDialog definem corretamente os atributos
     * e se os métodos getId, getEmployee, getBriefing, getTime e getDialog retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
    void testSettersAndGetters() {
        Long id = 1L;

        Employee employee = createSampleEmployee();
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        LocalDateTime time = LocalDateTime.now();
        String dialogContent = "This is a test dialog";

        dialogBox.setId(id);
        dialogBox.setEmployee(employee);
        dialogBox.setBriefing(briefing);
        dialogBox.setTime(time);
        dialogBox.setDialog(dialogContent);

        assertThat(dialogBox.getId()).isEqualTo(id);
        assertThat(dialogBox.getEmployee()).isEqualTo(employee);
        assertThat(dialogBox.getBriefing()).isEqualTo(briefing);
        assertThat(dialogBox.getTime()).isEqualTo(time);
        assertThat(dialogBox.getDialog()).isEqualTo(dialogContent);
    }

    /**
     * Testa os métodos equals e hashCode da classe DialogBox.
     * Verifica se duas instâncias com os mesmos valores de id, employee, briefing, time e dialog são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should consider equal instances with the same values")
    void testEqualsAndHashCode() {
        Long id = 1L;

        Employee employee = createSampleEmployee();
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        LocalDateTime time = LocalDateTime.now();
        String dialogContent = "This is a test dialog";

        DialogBox dialogBox1 = new DialogBox(id, employee, briefing, time, dialogContent);
        DialogBox dialogBox2 = new DialogBox(id, employee, briefing, time, dialogContent);
        DialogBox dialogBox3 = new DialogBox(id + 1, employee, briefing, time, dialogContent); // Instância com id diferente

        assertThat(dialogBox1).isEqualTo(dialogBox2);
        assertThat(dialogBox1.hashCode()).isEqualTo(dialogBox2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(dialogBox1).isNotEqualTo(dialogBox3);
        assertThat(dialogBox1.hashCode()).isNotEqualTo(dialogBox3.hashCode());
    }

    /**
     * Testa o método toString da classe DialogBox.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id, employee, briefing, time e dialog.
     */
    @Test
    @DisplayName("Should return the correct string representation")
    void testToString() {
        Long id = 1L;

        Employee employee = createSampleEmployee();
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        LocalDateTime time = LocalDateTime.now();
        String dialogContent = "This is a test dialog";

        DialogBox dialogBox = new DialogBox(id, employee, briefing, time, dialogContent);

        String expectedToString = String.format("DialogBox{id=%d, employee=%d, briefing=%d, time=%s, dialog='%s'}",
                                                id, employee.getId(), briefing.getId(), time, dialogContent);
        assertThat(dialogBox.toString()).isEqualTo(expectedToString);
    }

    /**
     * Testa a igualdade de DialogBox com atributos nulos.
     * Verifica se a classe trata corretamente casos em que valores nulos são atribuídos.
     */
    @Test
    @DisplayName("Should handle null attributes correctly")
    void testNullAttributes() {
        dialogBox.setId(null);
        dialogBox.setEmployee(null);
        dialogBox.setBriefing(null);
        dialogBox.setTime(null);
        dialogBox.setDialog(null);

        assertThat(dialogBox.getId()).isNull();
        assertThat(dialogBox.getEmployee()).isNull();
        assertThat(dialogBox.getBriefing()).isNull();
        assertThat(dialogBox.getTime()).isNull();
        assertThat(dialogBox.getDialog()).isNull();
    }

    /**
     * Testa a não igualdade de DialogBox com atributos diferentes.
     * Verifica se dois objetos diferentes não são considerados iguais.
     */
    @Test
    @DisplayName("Should not consider different DialogBox instances as equal")
    void testNotEqual() {
        Long id1 = 1L;
        Long id2 = 2L;

        Employee employee1 = createSampleEmployee();
        Employee employee2 = createSampleEmployee();
        employee2.setId(id2);

        Briefing briefing = new Briefing();
        briefing.setId(1L);

        LocalDateTime time = LocalDateTime.now();
        String dialogContent1 = "Dialog 1";
        String dialogContent2 = "Dialog 2";

        DialogBox dialogBox1 = new DialogBox(id1, employee1, briefing, time, dialogContent1);
        DialogBox dialogBox2 = new DialogBox(id2, employee2, briefing, time, dialogContent2);

        // Verifica que objetos diferentes não são iguais
        assertThat(dialogBox1).isNotEqualTo(dialogBox2);
    }

    private Employee createSampleEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setPhoneNumber("123456789");
        employee.setSector("IT");
        employee.setOccupation("Developer");
        employee.setAgency("Main");
        employee.setAvatar(100L);
        return employee;
    }
}
