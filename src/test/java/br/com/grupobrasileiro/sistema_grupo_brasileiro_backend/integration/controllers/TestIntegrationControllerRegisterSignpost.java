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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import static io.restassured.RestAssured.given;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test-integration")
@TestPropertySource(locations = "classpath:application-test-integration.properties")
public class TestIntegrationControllerRegisterSignpost extends AbstractIntegrationTest {

    private static RequestSpecification specificationRegisterSignpost;
    private static ObjectMapper objectMapper;
    private static final Faker faker = new Faker();

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specificationRegisterSignpost = new RequestSpecBuilder()
                .setBasePath("/api/v1/signposts")
                .setPort(TestConfig.SERVE_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    //TODO: PRIMEIRO O USUARIO DEVE LOGAR COMO ADMIN, O QUE AINDA NÃO TEM A CONTA DO SUPERVISOR

    @Test
    @DisplayName("Test register signpost")
    @Order(2)
    void registerSignpost() {
        ProjectForm projectForm = new ProjectForm(
                1L,
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
                new HashSet<CompaniesBriefingsForm>(),
                null,
                1L, 
                measurementsForm
        );


        BSignpostForm signpostForm = new BSignpostForm(
                1L,
                faker.address().fullAddress(),
                "Sector A"
        );

        RegisterSignpostForm registerSignpostForm = new RegisterSignpostForm(projectForm, briefingForm, signpostForm);


        Response response = given().spec(specificationRegisterSignpost)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(registerSignpostForm)
                .when()
                .post()
                .then()
                .log().all()  // Isso irá logar todos os detalhes da resposta
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        System.out.println("Status Code: " + statusCode);

        if (statusCode == 201) {
            BSignpostDetailedView signpostResponse = response.as(BSignpostDetailedView.class);
            assertNotNull(signpostResponse);
            assertEquals(signpostForm.boardLocation(), signpostResponse.bSignpostView().boardLocation());
        } else if (statusCode == 403) {
            System.out.println("Acesso negado. Verifique as permissões do usuário.");
        } else {
            System.out.println("Resposta inesperada. Corpo da resposta: " + response.getBody().asString());
        }
    }

}