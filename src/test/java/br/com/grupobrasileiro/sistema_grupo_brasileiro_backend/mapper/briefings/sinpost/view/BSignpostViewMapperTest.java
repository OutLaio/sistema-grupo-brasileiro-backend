package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sinpost.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.MaterialViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Testa a classe BSignpostViewMapper.
 * Verifica o mapeamento de BSignpost para BSignpostView.
 */
public class BSignpostViewMapperTest {

    @Mock
    private MaterialViewMapper materialViewMapper;

    @InjectMocks
    private BSignpostViewMapper bSignpostViewMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de BSignpost para BSignpostView.
     * Verifica se os valores são corretamente mapeados.
     */
    @Test
    @DisplayName("Deve mapear corretamente BSignpost para BSignpostView")
    void shouldMapBSignpostToBSignpostView() {
        // Cria dados fictícios usando o Faker
        Long id = faker.number().randomNumber();
        String boardLocation = faker.address().streetAddress();
        String sector = faker.company().industry();

        // Mock da entidade Material
        Material material = mock(Material.class);
        MaterialView materialView = mock(MaterialView.class);
        when(materialViewMapper.map(material)).thenReturn(materialView);

        // Cria um objeto BSignpost fictício
        BSignpost bSignpost = new BSignpost();
        bSignpost.setId(id);
        bSignpost.setMaterial(material);
        bSignpost.setBoardLocation(boardLocation);
        bSignpost.setSector(sector);

        // Executa o método a ser testado
        BSignpostView result = bSignpostViewMapper.map(bSignpost);

        // Verifica se o resultado foi mapeado corretamente
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.material()).isEqualTo(materialView);
        assertThat(result.boardLocation()).isEqualTo(boardLocation);
        assertThat(result.sector()).isEqualTo(sector);

        // Verifica se o método map() de materialViewMapper foi chamado
        verify(materialViewMapper).map(material);
    }
}
