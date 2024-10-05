package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.integration.controllers;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.config.TestConfig;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserDetailsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.integration.containers.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import io.restassured.RestAssured;
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

import static io.restassured.RestAssured.given;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test-integration")
@TestPropertySource(locations = "classpath:application-test-integration.properties")
public class TestIntegrationControllerRegisterUser extends AbstractIntegrationTest {

    private static RequestSpecification specificationRegister;
    private static RequestSpecification specificationLogin;
    private static RequestSpecification specificationGetAllBrands;

    private static ObjectMapper objectMapper;
    private static final Faker faker = new Faker();

    

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specificationRegister = new RequestSpecBuilder()
                .setBasePath("/api/v1/auth/register")
                .setPort(TestConfig.SERVE_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        specificationLogin = new RequestSpecBuilder()
                .setBasePath("/api/v1/auth/login")
                .setPort(TestConfig.SERVE_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        specificationGetAllBrands = new RequestSpecBuilder()
                .setBasePath("/api/brands")
                .setPort(TestConfig.SERVE_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

    }

    @Test
    @DisplayName("Test create user")
    @Order(1)
    void registerUser() {
        EmployeeForm employeeForm = new EmployeeForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().cellPhone(),
                "Sales",
                "Manager",
                "SP-Agency",
                1L
        );

        UserForm userForm = new UserForm(
                "email8@email.com",
                "SecurePassword123*",
                2L
        );

        UserDetailsForm userDetailsForm = new UserDetailsForm(employeeForm, userForm);
        
        int port = TestConfig.SERVE_PORT;

        System.out.println("Tentando registrar usuário na porta: " + port);
       
        Response response = given().spec(specificationRegister)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(userDetailsForm)
                .log().all()  
                .when()
                .post()
                .then()
                .log().all()  
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        // Verificar o status code e agir de acordo
        if (statusCode == 201) {
            assertNotNull(responseBody, "O corpo da resposta não deve ser nulo");
            // Adicione mais asserções aqui se necessário
        } else {
            String errorMessage = switch (statusCode) {
                case 400 -> "Bad Request. Verifique os dados enviados.";
                case 403 -> "Acesso negado. Verifique as permissões do usuário.";
                case 404 -> "Endpoint não encontrado. Verifique a URL e o mapeamento do controlador.";
                default -> "Resposta inesperada.";
            };
            fail(errorMessage + " Status code: " + statusCode + ". Corpo da resposta: " + responseBody);
        }

        assertEquals(201, statusCode, "O status da resposta deve ser 201 Created");
    }
    
    @Test
    @DisplayName("Test login with registered user")
    @Order(2)
    void loginUser() {
        // Use o mesmo email gerado no registro
        LoginForm loginForm = new LoginForm("email8@email.com", "SecurePassword123*");

        String response = given().spec(specificationLogin)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(loginForm)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertNotNull(response);
    }

}
