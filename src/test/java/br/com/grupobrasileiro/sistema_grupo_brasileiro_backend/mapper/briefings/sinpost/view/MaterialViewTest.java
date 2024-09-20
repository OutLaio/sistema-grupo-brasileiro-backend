package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sinpost.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.MaterialViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testa a classe MaterialViewMapper.
 * Verifica o mapeamento de Material para MaterialView.
 */
public class MaterialViewTest {

    private MaterialViewMapper materialViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        materialViewMapper = new MaterialViewMapper();
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de Material para MaterialView.
     * Verifica se os valores são corretamente mapeados.
     */
    @Test
    @DisplayName("Deve mapear corretamente Material para MaterialView")
    void shouldMapMaterialToMaterialView() {
        // Cria dados fictícios usando o Faker
        Long id = faker.number().randomNumber();
        String description = faker.lorem().sentence();

        // Cria um objeto Material fictício
        Material material = new Material();
        material.setId(id);
        material.setDescription(description);

        // Executa o método a ser testado
        MaterialView result = materialViewMapper.map(material);

        // Verifica se o resultado foi mapeado corretamente
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.description()).isEqualTo(description);
    }
}
