package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;

public class DialogBoxTest {

    /**
     * Testa o construtor padrão da classe DialogBox.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        DialogBox dialogBox = new DialogBox();
        assertThat(dialogBox).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe DialogBox.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, employee, briefing, time e dialog.
     */
    @Test
    void testParameterizedConstructor() {
        Long id = 1L;
        
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setPhoneNumber("123456789");
        employee.setSector("IT");
        employee.setOccupation("Developer");
        employee.setAgency("Main");
        employee.setAvatar(100L);

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
    void testSettersAndGetters() {
        DialogBox dialogBox = new DialogBox();
        Long id = 1L;

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setPhoneNumber("123456789");
        employee.setSector("IT");
        employee.setOccupation("Developer");
        employee.setAgency("Main");
        employee.setAvatar(100L);

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
    void testEqualsAndHashCode() {
        Long id = 1L;

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setPhoneNumber("123456789");
        employee.setSector("IT");
        employee.setOccupation("Developer");
        employee.setAgency("Main");
        employee.setAvatar(100L);

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
    void testToString() {
        Long id = 1L;

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setPhoneNumber("123456789");
        employee.setSector("IT");
        employee.setOccupation("Developer");
        employee.setAgency("Main");
        employee.setAvatar(100L);

        Briefing briefing = new Briefing();
        briefing.setId(1L);

        LocalDateTime time = LocalDateTime.now();
        String dialogContent = "This is a test dialog";

        DialogBox dialogBox = new DialogBox(id, employee, briefing, time, dialogContent);

        String expectedToString = "DialogBox(id=" + id +
                                  ", employee=" + employee +
                                  ", briefing=" + briefing +
                                  ", time=" + time +
                                  ", dialog=" + dialogContent + ")";
        assertThat(dialogBox.toString()).contains(expectedToString);
    }
}
