package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.SecurityFilter;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityFilter securityFilter;

    @Test
    void testPublicEndpointsAreAccessible() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/auth/register"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }

    @Test
    void testProtectedEndpointRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/protected-endpoint"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testPasswordEncoderBean() {
        assertThat(passwordEncoder).isNotNull();
    }

    @Test
    void testSecurityFilterBean() {
        assertThat(securityFilter).isNotNull();
    }

    @Test
    @WithMockUser
    void testAuthenticatedUserCanAccessProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/protected-endpoint"))
                .andExpect(status().isOk());
    }

    @Test
    void testCsrfDisabled() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login"))
                .andExpect(status().isOk());
    }

    @Test
    void testSessionManagementStateless() throws Exception {
        mockMvc.perform(get("/api/v1/auth/login"))
                .andExpect(status().isOk())
                .andExpect(header().doesNotExist("Set-Cookie"));
    }

    @Test
    void testSwaggerEndpointsAreAccessible() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }

    @Test
    void testCustomSecurityFilterApplied() throws Exception {
        mockMvc.perform(get("/filtered-endpoint"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testUnauthorizedAccessToProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/another-protected-endpoint"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdminAccessToRestrictedEndpoint() throws Exception {
        mockMvc.perform(get("/admin-endpoint"))
                .andExpect(status().isOk());
    }
}
