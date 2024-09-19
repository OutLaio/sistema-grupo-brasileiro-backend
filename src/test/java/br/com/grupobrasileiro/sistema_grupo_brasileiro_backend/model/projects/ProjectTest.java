package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

public class ProjectTest {

    /**
     * Testa o construtor padrão da classe Project.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        Project project = new Project();
        assertThat(project).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Project.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, collaborator, client, title, status, disabled e briefing.
     */
    @Test
    void testParameterizedConstructor() {
        Long id = 1L;

        Employee collaborator = new Employee();
        collaborator.setId(1L);
        collaborator.setName("Jane");
        collaborator.setLastName("Smith");
        collaborator.setPhoneNumber("987654321");
        collaborator.setSector("HR");
        collaborator.setOccupation("Manager");
        collaborator.setAgency("Branch");
        collaborator.setAvatar(200L);

        Employee client = new Employee();
        client.setId(2L);
        client.setName("John");
        client.setLastName("Doe");
        client.setPhoneNumber("123456789");
        client.setSector("Finance");
        client.setOccupation("Analyst");
        client.setAgency("Main");
        client.setAvatar(300L);

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
    void testSettersAndGetters() {
        Project project = new Project();
        Long id = 1L;

        Employee collaborator = new Employee();
        collaborator.setId(1L);
        collaborator.setName("Jane");
        collaborator.setLastName("Smith");
        collaborator.setPhoneNumber("987654321");
        collaborator.setSector("HR");
        collaborator.setOccupation("Manager");
        collaborator.setAgency("Branch");
        collaborator.setAvatar(200L);

        Employee client = new Employee();
        client.setId(2L);
        client.setName("John");
        client.setLastName("Doe");
        client.setPhoneNumber("123456789");
        client.setSector("Finance");
        client.setOccupation("Analyst");
        client.setAgency("Main");
        client.setAvatar(300L);

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
    void testEqualsAndHashCode() {
        Long id = 1L;

        Employee collaborator = new Employee();
        collaborator.setId(1L);
        collaborator.setName("Jane");
        collaborator.setLastName("Smith");
        collaborator.setPhoneNumber("987654321");
        collaborator.setSector("HR");
        collaborator.setOccupation("Manager");
        collaborator.setAgency("Branch");
        collaborator.setAvatar(200L);

        Employee client = new Employee();
        client.setId(2L);
        client.setName("John");
        client.setLastName("Doe");
        client.setPhoneNumber("123456789");
        client.setSector("Finance");
        client.setOccupation("Analyst");
        client.setAgency("Main");
        client.setAvatar(300L);

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
    void testToString() {
        Long id = 1L;

        Employee collaborator = new Employee();
        collaborator.setId(1L);
        collaborator.setName("Jane");
        collaborator.setLastName("Smith");
        collaborator.setPhoneNumber("987654321");
        collaborator.setSector("HR");
        collaborator.setOccupation("Manager");
        collaborator.setAgency("Branch");
        collaborator.setAvatar(200L);

        Employee client = new Employee();
        client.setId(2L);
        client.setName("John");
        client.setLastName("Doe");
        client.setPhoneNumber("123456789");
        client.setSector("Finance");
        client.setOccupation("Analyst");
        client.setAgency("Main");
        client.setAvatar(300L);

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
}
