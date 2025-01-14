package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.profile.register;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.profile.register.ProfileForm;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileFormTest {

    @Test
    public void testCreateProfileForm() {
        // Arrange
        BigInteger role = BigInteger.valueOf(1);
        String description = "Descrição do perfil";

        // Act
        ProfileForm profileForm = new ProfileForm(role, description);

        // Assert
        assertEquals(role, profileForm.role());
        assertEquals(description, profileForm.description());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        ProfileForm profileForm1 = new ProfileForm(BigInteger.valueOf(1), "Descrição do perfil");
        ProfileForm profileForm2 = new ProfileForm(BigInteger.valueOf(1), "Descrição do perfil");
        ProfileForm profileForm3 = new ProfileForm(BigInteger.valueOf(2), "Outra descrição");

        // Act & Assert
        assertEquals(profileForm1, profileForm2); // devem ser iguais
        assertNotEquals(profileForm1, profileForm3); // não devem ser iguais
        assertEquals(profileForm1.hashCode(), profileForm2.hashCode()); // mesmos hash codes para objetos iguais
        assertNotEquals(profileForm1.hashCode(), profileForm3.hashCode()); // hash codes diferentes para objetos diferentes
    }
}
