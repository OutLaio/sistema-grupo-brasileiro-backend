package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.MaterialViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;

public class BSignpostViewMapperTest {

    @InjectMocks
    private BSignpostViewMapper bSignpostViewMapper;

    @Mock
    private MaterialViewMapper materialViewMapper;

    @Mock
    private BSignpost bSignpost;

    @Mock
    private Material material; 

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker(); 
    }

    /**
     * Testa o mapeamento de BSignpost para BSignpostView.
     * Verifica se todos os campos são mapeados corretamente quando um BSignpost válido é fornecido.
     */
    @Test
    @DisplayName("Should map BSignpost to BSignpostView correctly")
    void mapBSignpostToBSignpostView() {
        // Configuração dos mocks
        Long id = faker.number().randomNumber();
        String boardLocation = faker.address().streetAddress();
        String sector = faker.company().industry();
        Long materialId = faker.number().randomNumber();
        String materialDescription = faker.lorem().sentence();

        // Criação do MaterialView
        MaterialView materialView = new MaterialView(materialId, materialDescription);

        // Configuração do mock
        when(bSignpost.getId()).thenReturn(id);
        when(bSignpost.getMaterial()).thenReturn(material);
        when(bSignpost.getBoardLocation()).thenReturn(boardLocation);
        when(bSignpost.getSector()).thenReturn(sector);
        when(materialViewMapper.map(material)).thenReturn(materialView); // Mapeia material

        // Mapeamento
        BSignpostView result = bSignpostViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id); // Acesso ao ID do BSignpostView
        assertThat(result.material().id()).isEqualTo(materialId); // Acesso ao ID do MaterialView
        assertThat(result.material().description()).isEqualTo(materialDescription); // Acesso à descrição do MaterialView
        assertThat(result.boardLocation()).isEqualTo(boardLocation); // Acesso à localização da placa
        assertThat(result.sector()).isEqualTo(sector); // Acesso ao setor da placa
    }

    /**
     * Testa o mapeamento de BSignpost para BSignpostView.
     * Verifica se o campo MaterialView é nulo quando o material no BSignpost é nulo.
     */
    @Test
    @DisplayName("Should handle null Material when mapping BSignpost")
    void mapBSignpostWithNullMaterial() {
        // Configuração dos mocks
        Long id = faker.number().randomNumber();
        String boardLocation = faker.address().streetAddress();
        String sector = faker.company().industry();

        // Configuração do mock para retorno nulo do material
        when(bSignpost.getId()).thenReturn(id);
        when(bSignpost.getMaterial()).thenReturn(null); // Material é nulo
        when(bSignpost.getBoardLocation()).thenReturn(boardLocation);
        when(bSignpost.getSector()).thenReturn(sector);

        // Mapeamento
        BSignpostView result = bSignpostViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id); // Acesso ao ID do BSignpostView
        assertThat(result.material()).isNull(); // Verifica se o MaterialView é nulo
        assertThat(result.boardLocation()).isEqualTo(boardLocation); // Acesso à localização da placa
        assertThat(result.sector()).isEqualTo(sector); // Acesso ao setor da placa
    }

    /**
     * Testa o mapeamento de BSignpost para BSignpostView.
     * Verifica se a localização da placa é mapeada corretamente quando fornecida.
     */
    @Test
    @DisplayName("Should map board location correctly")
    void mapBoardLocationCorrectly() {
        // Configuração dos mocks
        Long id = faker.number().randomNumber();
        String boardLocation = "Main Street";
        String sector = faker.company().industry();

        // Configuração do mock
        when(bSignpost.getId()).thenReturn(id);
        when(bSignpost.getMaterial()).thenReturn(material);
        when(bSignpost.getBoardLocation()).thenReturn(boardLocation);
        when(bSignpost.getSector()).thenReturn(sector);
        when(materialViewMapper.map(material)).thenReturn(new MaterialView(1L, "Sample Material"));

        // Mapeamento
        BSignpostView result = bSignpostViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.boardLocation()).isEqualTo(boardLocation); // Acesso à localização da placa
    }

    /**
     * Testa o mapeamento de BSignpost para BSignpostView.
     * Verifica se o setor da placa é mapeado corretamente quando fornecido.
     */
    @Test
    @DisplayName("Should map sector correctly")
    void mapSectorCorrectly() {
        // Configuração dos mocks
        Long id = faker.number().randomNumber();
        String boardLocation = faker.address().streetAddress();
        String sector = "Marketing";

        // Configuração do mock
        when(bSignpost.getId()).thenReturn(id);
        when(bSignpost.getMaterial()).thenReturn(material);
        when(bSignpost.getBoardLocation()).thenReturn(boardLocation);
        when(bSignpost.getSector()).thenReturn(sector);
        when(materialViewMapper.map(material)).thenReturn(new MaterialView(1L, "Sample Material"));

        // Mapeamento
        BSignpostView result = bSignpostViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.sector()).isEqualTo(sector); 
    }
}

