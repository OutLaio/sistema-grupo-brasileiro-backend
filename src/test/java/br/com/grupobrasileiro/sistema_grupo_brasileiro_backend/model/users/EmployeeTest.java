package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testa a classe Employee.
 * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
 */
public class EmployeeTest {

    private final Faker faker = new Faker();

    /**
     * Cria uma instância de Employee com dados simulados usando o Faker.
     * Utiliza valores gerados aleatoriamente para preencher os campos.
     * 
     * @param id O ID opcional para definir no Employee. Se null, um ID aleatório será gerado.
     * @return Uma instância de Employee preenchida com dados simulados.
     */
    private Employee createSampleEmployee(Long id) {
        User user = new User(); // Cria um User de exemplo

        Employee employee = new Employee();
        employee.setId(id != null ? id : faker.number().randomNumber());
        employee.setName(faker.name().firstName());
        employee.setLastName(faker.name().lastName());
        employee.setPhoneNumber(faker.phoneNumber().phoneNumber());
        employee.setSector(faker.company().industry());
        employee.setOccupation(faker.job().title());
        employee.setAgency(faker.company().name());
        employee.setAvatar(faker.number().randomNumber());
        employee.setUser(user);

        return employee;
    }

    /**
     * Testa o construtor padrão da classe Employee.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        Employee employee = new Employee();
        assertThat(employee).isNotNull();
    }

    /**
     * Testa os métodos setters e getters da classe Employee.
     * Verifica se os métodos set e get definem e retornam corretamente os atributos
     * da instância de Employee.
     */
    @Test
    void testSettersAndGetters() {
        Employee employee = createSampleEmployee(null);

        assertThat(employee.getId()).isNotNull();
        assertThat(employee.getName()).isNotNull();
        assertThat(employee.getLastName()).isNotNull();
        assertThat(employee.getPhoneNumber()).isNotNull();
        assertThat(employee.getSector()).isNotNull();
        assertThat(employee.getOccupation()).isNotNull();
        assertThat(employee.getAgency()).isNotNull();
        assertThat(employee.getAvatar()).isNotNull();
        assertThat(employee.getUser()).isNotNull();
    }

    /**
     * Testa os métodos equals e hashCode da classe Employee.
     * Verifica se duas instâncias com os mesmos valores de id e outros atributos são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    void testEqualsAndHashCode() {
        // Cria um ID fixo
        Long id = 123L;

        // Cria duas instâncias idênticas
        Employee employee1 = new Employee();
        employee1.setId(id);
        employee1.setName("Dorsey");
        employee1.setLastName("Hoppe");
        employee1.setPhoneNumber("848-366-8553 x00094");
        employee1.setSector("Legislative Office");
        employee1.setOccupation("Advertising Orchestrator");
        employee1.setAgency("Stroman, Koch and Terry");
        employee1.setAvatar(628586L);
        employee1.setUser(new User());

        Employee employee2 = new Employee();
        employee2.setId(id);
        employee2.setName("Dorsey");
        employee2.setLastName("Hoppe");
        employee2.setPhoneNumber("848-366-8553 x00094");
        employee2.setSector("Legislative Office");
        employee2.setOccupation("Advertising Orchestrator");
        employee2.setAgency("Stroman, Koch and Terry");
        employee2.setAvatar(628586L);
        employee2.setUser(new User());

        // Verifica se os objetos são iguais e têm o mesmo hashCode
        assertThat(employee1).isEqualTo(employee2);
        assertThat(employee1.hashCode()).isEqualTo(employee2.hashCode());

        // Cria uma terceira instância com um ID diferente
        Employee employee3 = new Employee();
        employee3.setId(id + 1);
        employee3.setName("Dorsey");
        employee3.setLastName("Hoppe");
        employee3.setPhoneNumber("848-366-8553 x00094");
        employee3.setSector("Legislative Office");
        employee3.setOccupation("Advertising Orchestrator");
        employee3.setAgency("Stroman, Koch and Terry");
        employee3.setAvatar(628586L);
        employee3.setUser(new User());

        // Verifica que objetos com IDs diferentes não são iguais
        assertThat(employee1).isNotEqualTo(employee3);
        assertThat(employee1.hashCode()).isNotEqualTo(employee3.hashCode());
    }


    /**
     * Testa o método toString da classe Employee.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id, name, lastName, phoneNumber, sector, occupation, agency, avatar e user.
     */
    @Test
    void testToString() {
        // Crie um Employee com valores fixos para garantir que o teste seja consistente
        Employee employee = new Employee();
        employee.setId(123L);
        employee.setName("Marquis");
        employee.setLastName("Willms");
        employee.setPhoneNumber("876.979.4516 x852");
        employee.setSector("Entertainment");
        employee.setOccupation("Hospitality Orchestrator");
        employee.setAgency("Littel, Bartolletti and Macejkovic");
        employee.setAvatar(883762062L); // Corrigido para Long
        employee.setUser(new User()); // Supondo que User tem uma implementação básica ou mockada

        // A representação esperada deve corresponder aos valores configurados
        String expectedToString = "Employee(id=123, name=Marquis, lastName=Willms, phoneNumber=876.979.4516 x852, sector=Entertainment, occupation=Hospitality Orchestrator, agency=Littel, Bartolletti and Macejkovic, avatar=883762062, user=User(id=null, profile=null, email=null, password=null, disabled=null, employee=null), ownedProjects=[], assignedProjects=[], dialogs=[])";
        
        assertThat(employee.toString()).isEqualTo(expectedToString);
    }

}

