package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;

public class ProjectTest {

    /**
     * Testa o construtor padrão da classe Project.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance using the default constructor")
    void testDefaultConstructor() {
        Project project = new Project();
        assertThat(project).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Project.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, collaborator, client, title, status, disabled e briefing.
     */
    @Test
    @DisplayName("Should correctly initialize attributes using the parameterized constructor")
    void testParameterizedConstructor() {
        Long id = 1L;

        Employee collaborator = createEmployee(1L, "Jane", "Smith", "987654321", "HR", "Manager", "Branch", 200L);
        Employee client = createEmployee(2L, "John", "Doe", "123456789", "Finance", "Analyst", "Main", 300L);

        String title = "New Project";
        String status = "In Progress";
        Boolean disabled = false;
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        Project project = new Project(id, collaborator, client, title, status, disabled, briefing);

        assertThat(project.getId()).isEqualTo(id);
        assertThat(project.getCollaborator()).isEqualTo(collaborator);
        assertThat(project.getClient()).isEqualTo(client);
        assertThat(project.getTitle()).isEqualTo(title);
        assertThat(project.getStatus()).isEqualTo(status);
        assertThat(project.getDisabled()).isEqualTo(disabled);
        assertThat(project.getBriefing()).isEqualTo(briefing);
    }

    /**
     * Testa os métodos setters e getters da classe Project.
     * Verifica se os métodos setId, setCollaborator, setClient, setTitle, setStatus, setDisabled e setBriefing definem corretamente os atributos
     * e se os métodos getId, getCollaborator, getClient, getTitle, getStatus, getDisabled e getBriefing retornam os valores esperados.
     */
    @Test
    @DisplayName("Should correctly set and get all attributes using setter and getter methods")
    void testSettersAndGetters() {
        Project project = new Project();
        Long id = 1L;

        Employee collaborator = createEmployee(1L, "Jane", "Smith", "987654321", "HR", "Manager", "Branch", 200L);
        Employee client = createEmployee(2L, "John", "Doe", "123456789", "Finance", "Analyst", "Main", 300L);

        String title = "New Project";
        String status = "In Progress";
        Boolean disabled = false;
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        project.setId(id);
        project.setCollaborator(collaborator);
        project.setClient(client);
        project.setTitle(title);
        project.setStatus(status);
        project.setDisabled(disabled);
        project.setBriefing(briefing);

        assertThat(project.getId()).isEqualTo(id);
        assertThat(project.getCollaborator()).isEqualTo(collaborator);
        assertThat(project.getClient()).isEqualTo(client);
        assertThat(project.getTitle()).isEqualTo(title);
        assertThat(project.getStatus()).isEqualTo(status);
        assertThat(project.getDisabled()).isEqualTo(disabled);
        assertThat(project.getBriefing()).isEqualTo(briefing);
    }

    /**
     * Testa os métodos equals e hashCode da classe Project.
     * Verifica se duas instâncias com os mesmos valores de id, collaborator, client, title, status, disabled e briefing são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should consider equal instances with the same attribute values")
    void testEqualsAndHashCode() {
        Long id = 1L;

        Employee collaborator = createEmployee(1L, "Jane", "Smith", "987654321", "HR", "Manager", "Branch", 200L);
        Employee client = createEmployee(2L, "John", "Doe", "123456789", "Finance", "Analyst", "Main", 300L);

        String title = "New Project";
        String status = "In Progress";
        Boolean disabled = false;
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        Project project1 = new Project(id, collaborator, client, title, status, disabled, briefing);
        Project project2 = new Project(id, collaborator, client, title, status, disabled, briefing);
        Project project3 = new Project(id + 1, collaborator, client, title, status, disabled, briefing); // Instância com id diferente

        assertThat(project1).isEqualTo(project2);
        assertThat(project1.hashCode()).isEqualTo(project2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(project1).isNotEqualTo(project3);
        assertThat(project1.hashCode()).isNotEqualTo(project3.hashCode());
    }

    /**
     * Testa o método toString da classe Project.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id, collaborator, client, title, status, disabled e briefing.
     */
    @Test
    @DisplayName("Should return a correct string representation of the Project instance")
    void testToString() {
        Long id = 1L;

        Employee collaborator = createEmployee(1L, "Jane", "Smith", "987654321", "HR", "Manager", "Branch", 200L);
        Employee client = createEmployee(2L, "John", "Doe", "123456789", "Finance", "Analyst", "Main", 300L);

        String title = "New Project";
        String status = "In Progress";
        Boolean disabled = false;
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        Project project = new Project(id, collaborator, client, title, status, disabled, briefing);

        String expectedToString = "Project(id=" + id +
                                  ", collaborator=" + collaborator +
                                  ", client=" + client +
                                  ", title=" + title +
                                  ", status=" + status +
                                  ", disabled=" + disabled +
                                  ", briefing=" + briefing + ")";
        assertThat(project.toString()).contains(expectedToString);
    }

    /**
     * Testa o método toString da classe Project com valores nulos.
     * Verifica se o método toString lida corretamente com atributos nulos.
     */
    @Test
    @DisplayName("Should return correct string representation when attributes are null")
    void testToStringWithNullValues() {
        Project project = new Project();

        String expectedToString = "Project(id=null, collaborator=null, client=null, title=null, status=null, disabled=null, briefing=null)";
        assertThat(project.toString()).isEqualTo(expectedToString);
    }

    /**
     * Testa o método equals da classe Project com valores nulos.
     * Verifica se a comparação com uma instância nula é tratada corretamente.
     */
    @Test
    @DisplayName("Should not be equal to null")
    void testEqualsWithNull() {
        Project project = new Project();
        assertThat(project).isNotEqualTo(null);
    }

    /**
     * Testa a criação de dois projetos com atributos diferentes.
     * Verifica se os projetos com atributos diferentes são tratados como diferentes.
     */
    @Test
    @DisplayName("Should not consider different Project instances as equal")
    void testDifferentProjectsAreNotEqual() {
        Long id = 1L;
        Employee collaborator = createEmployee(1L, "Jane", "Smith", "987654321", "HR", "Manager", "Branch", 200L);
        Employee client = createEmployee(2L, "John", "Doe", "123456789", "Finance", "Analyst", "Main", 300L);

        String title = "New Project";
        String status = "In Progress";
        Boolean disabled = false;
        Briefing briefing = new Briefing();
        briefing.setId(1L);

        Project project1 = new Project(id, collaborator, client, title, status, disabled, briefing);
        Project project2 = new Project(id + 1, collaborator, client, title, status, disabled, briefing); // Diferente id

        assertThat(project1).isNotEqualTo(project2);
    }

    // Método auxiliar para criar um Employee
    private Employee createEmployee(Long id, String name, String lastName, String phoneNumber, String sector, String occupation, String agency, Long avatar) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setPhoneNumber(phoneNumber);
        employee.setSector(sector);
        employee.setOccupation(occupation);
        employee.setAgency(agency);
        employee.setAvatar(avatar);
        return employee;
    }
}
