package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

// Teste para a interface Mapper
class MapperTest {

    private Mapper<String, Integer> stringToIntegerMapper; // Interface a ser testada
    private Faker faker; 

    @BeforeEach
    void setUp() {
        // Usando um mock para a implementação do Mapper
        stringToIntegerMapper = mock(Mapper.class); 
        faker = new Faker(); 
    }

    /**
     * Testa a conversão de uma String numérica válida para Integer.
     * Verifica se o valor de entrada gerado pelo Faker é mapeado corretamente.
     */
    @Test
    @DisplayName("Should map String to Integer correctly using Faker")
    void shouldMapStringToIntegerWithFaker() {
        String input = faker.number().digits(3); // Gera uma string numérica de 3 dígitos, ex: "123"
        Integer expectedOutput = Integer.parseInt(input); // Converte a string para Integer

        // Define o comportamento esperado do mock
        when(stringToIntegerMapper.map(input)).thenReturn(expectedOutput);
        
        // Chama o método map e verifica se o resultado é o esperado
        Integer result = stringToIntegerMapper.map(input);
        assertEquals(expectedOutput, result, 
            "O valor retornado deve ser o número inteiro correspondente à string numérica.");
    }

    /**
     * Testa a conversão de uma String inválida (não numérica) para Integer.
     * Verifica se a exceção NumberFormatException é lançada.
     */
    @Test
    @DisplayName("Should throw NumberFormatException for invalid input")
    void throwExceptionForInvalidInput() {
        String input = faker.lorem().word(); // Gera uma string não numérica, ex: "abc"

        // Verifica se o método map lança a exceção NumberFormatException para uma entrada inválida
        assertThrows(NumberFormatException.class, () -> {
            // Simula o comportamento do mock para lançar a exceção
            when(stringToIntegerMapper.map(input)).thenThrow(new NumberFormatException());
            stringToIntegerMapper.map(input);
        }, "Deve lançar NumberFormatException para uma string inválida.");
    }

    /**
     * Testa a conversão de uma String nula para Integer.
     * Verifica se o método retorna null quando a entrada é nula.
     */
    @Test
    @DisplayName("Should return null for null input")
    void returnNullForNullInput() {
        String input = null; // Entrada nula

        // Define o comportamento esperado do mock
        when(stringToIntegerMapper.map(input)).thenReturn(null);
        
        // Verifica se o resultado é null
        assertNull(stringToIntegerMapper.map(input), 
            "O valor retornado deve ser null quando a entrada é null.");
    }

    /**
     * Testa a conversão de uma String vazia para Integer.
     * Verifica se a exceção NumberFormatException é lançada para uma string vazia.
     */
    @Test
    @DisplayName("Should throw NumberFormatException for empty string")
    void throwExceptionForEmptyString() {
        String input = ""; // Entrada de string vazia

        // Verifica se o método map lança a exceção NumberFormatException para uma string vazia
        assertThrows(NumberFormatException.class, () -> {
            // Simula o comportamento do mock para lançar a exceção
            when(stringToIntegerMapper.map(input)).thenThrow(new NumberFormatException());
            stringToIntegerMapper.map(input);
        }, "Deve lançar NumberFormatException para uma string vazia.");
    }
}
