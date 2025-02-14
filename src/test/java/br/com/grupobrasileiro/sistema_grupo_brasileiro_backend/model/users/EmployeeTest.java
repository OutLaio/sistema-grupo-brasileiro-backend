package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

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
        employee.setRegistrationNumber("123456");
        employee.setAvatar(faker.number().randomNumber());
        employee.setUser(user);

        return employee;
    }

    /**
     * Testa o construtor padrão da classe Employee.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create an Employee instance using the default constructor")
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
    @DisplayName("Should correctly set and get Employee attributes")
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
    @DisplayName("Should consider equal instances with the same attributes")
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
        employee1.setRegistrationNumber("123456");
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
        employee2.setRegistrationNumber("123456");
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
        employee3.setRegistrationNumber("123456");
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
    @DisplayName("Should return a correct string representation of the Employee instance")
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
        employee.setRegistrationNumber("123456");
        employee.setAvatar(883762062L);
        employee.setUser(new User());

        String expectedToString = "Employee{id=123, name='Marquis', lastName='Willms', phoneNumber='876.979.4516 x852', sector='Entertainment', occupation='Hospitality Orchestrator', agency='Littel, Bartolletti and Macejkovic', registrationNumber='123456', avatar=883762062}";
        
        assertThat(employee.toString()).isEqualTo(expectedToString);
    }

    /**
     * Testa a criação de um Employee com valores nulos.
     * Verifica se a instância pode ser criada e não é nula.
     */
    @Test
    @DisplayName("Should create an Employee instance with null values and not be null")
    void testCreateEmployeeWithNullValues() {
        Employee employee = createSampleEmployee(null);
        assertThat(employee).isNotNull();
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
     * Testa a alteração do setor de um Employee.
     * Verifica se o método setSector altera corretamente o valor.
     */
    @Test
    @DisplayName("Should correctly set the sector attribute")
    void testSetSector() {
        Employee employee = createSampleEmployee(null);
        String newSector = "Human Resources";
        employee.setSector(newSector);
        assertThat(employee.getSector()).isEqualTo(newSector);
    }
}
