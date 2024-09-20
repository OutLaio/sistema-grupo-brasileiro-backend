package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperTest {

    @InjectMocks
    private StringToIntegerMapper stringToIntegerMapper;

    @BeforeEach
    void setUp() {
        // Inicializa o Mockito para a implementação de teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should map String to Integer correctly")
    void shouldMapStringToInteger() {
        // Valor de entrada
        String input = "123";
        
        // Chama o método map para converter a string para inteiro
        Integer result = stringToIntegerMapper.map(input);

        // Verifica se o resultado é o esperado
        assertEquals(123, result);
    }

    // Implementação fictícia para teste, convertendo String para Integer
    static class StringToIntegerMapper implements Mapper<String, Integer> {
        @Override
        public Integer map(String input) {
            return Integer.parseInt(input);
        }
    }
}
