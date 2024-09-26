package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.profile.view.ProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;

import static org.junit.jupiter.api.Assertions.assertNull;


public class ProfileViewMapperTest {

 private ProfileViewMapper profileViewMapper;
 private Faker faker;

 @BeforeEach
 void setUp() {
     profileViewMapper = new ProfileViewMapper();
     faker = new Faker();
 }

 // Teste existente
 @Test
 @DisplayName("Should map Profile to ProfileView correctly")
 void mapProfileToProfileViewCorrectly() {
     // Arrange
     Long id = faker.number().randomNumber();
     String description = faker.company().industry();
     Profile profile = new Profile(id, description, new HashSet<>());

     // Act
     ProfileView profileView = profileViewMapper.map(profile);

     // Assert
     assertEquals(profile.getId(), profileView.id(), "ProfileView ID should match");
     assertEquals(profile.getDescription(), profileView.description(), "ProfileView description should match");
 }


 /**
  * Testa o mapeamento quando a descrição do perfil é nula.
  * Verifica se o resultado mapeia corretamente o ID e a descrição nula.
  */
 @Test
 @DisplayName("Should map Profile with null description correctly")
 void mapProfileWithNullDescription() {
     // Arrange
     Long id = faker.number().randomNumber();
     Profile profile = new Profile(id, null, new HashSet<>());

     // Act
     ProfileView profileView = profileViewMapper.map(profile);

     // Assert
     assertEquals(profile.getId(), profileView.id(), "ProfileView ID should match");
     assertNull(profileView.description(), "ProfileView description should be null");
 }

 /**
  * Testa o mapeamento quando o perfil está vazio (sem descrição e sem permissões).
  * Verifica se o resultado é mapeado corretamente.
  */
 @Test
 @DisplayName("Should map empty Profile correctly")
 void mapEmptyProfile() {
     // Arrange
     Profile profile = new Profile(1L, "", new HashSet<>());

     // Act
     ProfileView profileView = profileViewMapper.map(profile);

     // Assert
     assertEquals(profile.getId(), profileView.id(), "ProfileView ID should match");
     assertEquals("", profileView.description(), "ProfileView description should be empty");
 }
 
 /**
  * Testa o mapeamento quando o perfil tem ID nulo.
  * Verifica se o resultado mantém o ID nulo.
  */
 @Test
 @DisplayName("Should map Profile with null ID correctly")
 void mapProfileWithNullId() {
     // Arrange
     Profile profile = new Profile(null, "Some description", new HashSet<>());

     // Act
     ProfileView profileView = profileViewMapper.map(profile);

     // Assert
     assertNull(profileView.id(), "ProfileView ID should be null for Profile with null ID");
     assertEquals("Some description", profileView.description(), "ProfileView description should match");
 }
}
