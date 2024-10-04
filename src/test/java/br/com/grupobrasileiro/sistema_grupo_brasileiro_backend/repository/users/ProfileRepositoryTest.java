package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private EntityManager entityManager;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        entityManager.createNativeQuery("TRUNCATE TABLE \"Tb_Profiles\" RESTART IDENTITY CASCADE").executeUpdate();
    }

    @Test
    @DisplayName("Should update an existing profile")
    @Transactional
    void testUpdateProfile() {
        // Criar e salvar um perfil
        Profile profile = new Profile();
        profile.setDescription(faker.job().title());
        profile = profileRepository.save(profile);
        entityManager.flush();
        entityManager.clear();
        
        Long profileId = profile.getId();
        String originalDescription = profile.getDescription();

        // Recuperar o perfil, atualizar e salvar
        Profile retrievedProfile = profileRepository.findById(profileId).orElseThrow();
        String newDescription = faker.job().field();
        retrievedProfile.setDescription(newDescription);
        profileRepository.save(retrievedProfile);
        entityManager.flush();
        entityManager.clear();

        // Verificar se a atualização foi bem-sucedida
        Profile updatedProfile = profileRepository.findById(profileId).orElseThrow();
        assertThat(updatedProfile.getDescription())
            .isNotEqualTo(originalDescription)
            .isEqualTo(newDescription);
    }


    @Test
    @DisplayName("Should return empty when retrieving non-existing profile")
    void testRetrieveNonExistingProfile() {
        Optional<Profile> retrievedProfile = profileRepository.findById(999L);
        assertThat(retrievedProfile).isNotPresent();
    }

}