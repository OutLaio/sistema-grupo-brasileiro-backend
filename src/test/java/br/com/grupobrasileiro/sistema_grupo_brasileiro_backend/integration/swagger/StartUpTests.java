package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.integration.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.integration.containers.AbstractIntegrationTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-integration")
class SwaggerIntegrationTest extends AbstractIntegrationTest {

 @LocalServerPort
 private int port;

 @Test
 @DisplayName("JUnit test for Should Display Swagger UI Page")
 void testShouldDisplaySwaggerUiPage() {
     var content = given()
             .basePath("/swagger-ui/index.html")
             .port(port)
             .when()
             .get()
             .then()
             .statusCode(200)
             .extract()
             .body()
             .asString();

     assertTrue(content.contains("Swagger UI"));
 }
}