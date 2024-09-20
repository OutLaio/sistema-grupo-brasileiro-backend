package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SistemaGrupoBrasileiroBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    //@Test
   // void testGetAuthEndpoint() throws Exception {
  //      mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth"))
  //             .andExpect(MockMvcResultMatchers.status().isOk())
  //             .andExpect(MockMvcResultMatchers.content().string("Autenticação bem-sucedida"));
  //  }
}
