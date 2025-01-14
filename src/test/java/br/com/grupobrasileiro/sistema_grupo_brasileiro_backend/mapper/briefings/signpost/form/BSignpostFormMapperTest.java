package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.form;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.form.BSignpostFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.MaterialRepository;

public class BSignpostFormMapperTest {

    @InjectMocks
    private BSignpostFormMapper bSignpostFormMapper;

    @Mock
    private MaterialRepository materialRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configura um Material mockado com o id 1L, necessário para o teste
        Material mockMaterial = new Material();
        mockMaterial.setId(1L);
        when(materialRepository.findById(1L)).thenReturn(Optional.of(mockMaterial));
    }

    @Test
    @DisplayName("Should map valid BSignpostForm to BSignpost")
    void mapValidForm() {
        // Dados de teste
        BSignpostForm signpostForm = new BSignpostForm(
                1L, // idMaterial esperado
                "Localização da Placa",
                "Setor"
        );

        // Mapeamento
        BSignpost result = bSignpostFormMapper.map(signpostForm);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull(); // Verifica se o ID é null
        assertThat(result.getMaterial()).isNotNull(); // Material foi mapeado corretamente
        assertThat(result.getMaterial().getId()).isEqualTo(1L); // Verifica o ID do material
        assertThat(result.getBoardLocation()).isEqualTo(signpostForm.boardLocation()); // Verifica boardLocation
        assertThat(result.getSector()).isEqualTo(signpostForm.sector()); // Verifica o sector
    }

    @Test
    @DisplayName("Should throw exception for null BSignpostForm")
    void throwForNullForm() {
        assertThrows(NullPointerException.class, () -> bSignpostFormMapper.map(null), 
            "Mapping should throw a NullPointerException for null BSignpostForm");
    }

    @Test
    @DisplayName("Should map BSignpostForm with empty fields")
    void mapEmptyFields() {
        BSignpostForm signpostForm = new BSignpostForm(
                1L,
                "", // Localização vazia
                ""  // Setor vazio
        );

        BSignpost result = bSignpostFormMapper.map(signpostForm);

        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isEqualTo("");
        assertThat(result.getSector()).isEqualTo("");
    }

    @Test
    @DisplayName("Should map BSignpostForm with null fields")
    void mapNullFields() {
        BSignpostForm signpostForm = new BSignpostForm(
                1L,
                null, // Localização nula
                null  // Setor nulo
        );

        BSignpost result = bSignpostFormMapper.map(signpostForm);

        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isNull();
        assertThat(result.getSector()).isNull();
    }

    @Test
    @DisplayName("Should map BSignpostForm with null ID")
    void mapFormWithNullId() {
       
        when(materialRepository.findById(null)).thenThrow(new EntityNotFoundException("Material not found for id: null"));

       
        BSignpostForm signpostForm = new BSignpostForm(
                null, // ID de material nulo
                "Localização da Placa",
                "Setor"
        );

        
        assertThrows(EntityNotFoundException.class, () -> bSignpostFormMapper.map(signpostForm),
                "Mapping should throw an EntityNotFoundException for null idMaterial");
    }

    @Test
    @DisplayName("Should handle inconsistent data correctly")
    void handleInconsistentData() {
        BSignpostForm signpostForm = new BSignpostForm(
                1L,
                "Localização Inconsistente",
                "Setor Inconsistente"
        );

        BSignpost result = bSignpostFormMapper.map(signpostForm);

        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isEqualTo("Localização Inconsistente");
        assertThat(result.getSector()).isEqualTo("Setor Inconsistente");
    }
}
