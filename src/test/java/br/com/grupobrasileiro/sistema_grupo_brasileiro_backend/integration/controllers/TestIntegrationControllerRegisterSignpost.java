package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.integration.controllers;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.config.TestConfig;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.RegisterSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.form.CompaniesBriefingsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form.MeasurementsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.integration.containers.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.thymeleaf.TemplateEngine;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class})
@ActiveProfiles("test-integration")
@TestPropertySource(locations = "classpath:application-test-integration.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestIntegrationControllerRegisterSignpost extends AbstractIntegrationTest {

    private RequestSpecification specificationRegisterSignpost;
    private ObjectMapper objectMapper;
    private static final Faker faker = new Faker();
    
    @MockBean
    private TemplateEngine templateEngine;
    
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // Use the injected port
        specificationRegisterSignpost = new RequestSpecBuilder()
                .setBasePath("/api/v1/signposts")
                .setPort(port) // Use the injected port
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }
    //TODO: PRIMEIRO O USUARIO DEVE LOGAR COMO ADMIN, O QUE AINDA NÃO TEM A CONTA DO SUPERVISOR

    @Test
    @DisplayName("Test register signpost")
    @Order(2)
    void registerSignpost() {
        try {
            Thread.sleep(2000); // Considere remover ou substituir por uma espera mais adequada.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        }
        
        ProjectForm projectForm = new ProjectForm(
                1L, // Verifique se esse ID existe
                faker.company().name(),
                null
        );

        MeasurementsForm measurementsForm = new MeasurementsForm(
                BigDecimal.valueOf(1.75),
                BigDecimal.valueOf(2.50)
        );

        BriefingForm briefingForm = new BriefingForm(
                LocalDate.now(), 
                faker.lorem().sentence(),
                new HashSet<>(),
                null,
                1L, // Verifique se esse ID existe
                measurementsForm
        );

        BSignpostForm signpostForm = new BSignpostForm(
                1L, // Verifique se esse ID existe
                faker.address().fullAddress(),
                "Sector A"
        );

        RegisterSignpostForm registerSignpostForm = new RegisterSignpostForm(projectForm, briefingForm, signpostForm);

        Response response = given()
                .spec(specificationRegisterSignpost)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(registerSignpostForm)
                .when()
                .post()
                .then()
                .log().all()  
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        System.out.println("Status Code: " + statusCode);

        switch (statusCode) {
            case 201:
                assertNotNull(response.getBody());
                
                BSignpostDetailedView signpostResponse = response.getBody().as(BSignpostDetailedView.class);
                
                assertNotNull(signpostResponse);
                assertEquals(signpostForm.boardLocation(), signpostResponse.signpost().boardLocation());
                break;

            case 403:
                System.out.println("Acesso negado. Verifique as permissões do usuário.");
                break;

            default:
                System.out.println("Resposta inesperada. Corpo da resposta: " + response.getBody().asString());
                fail("Resposta inesperada: " + statusCode);
        }
    }
}
