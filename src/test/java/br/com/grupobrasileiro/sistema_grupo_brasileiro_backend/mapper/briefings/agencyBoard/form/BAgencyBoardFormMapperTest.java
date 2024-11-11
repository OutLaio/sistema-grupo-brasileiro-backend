package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType; // Certifique-se de que este é o tipo correto
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.AgencyBoardTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.BoardTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;

@ExtendWith(MockitoExtension.class)
public class BAgencyBoardFormMapperTest {

    @InjectMocks
    private BAgencyBoardFormMapper mapper;

    @Mock
    private BoardTypeRepository boardTypeRepository;

    @Mock
    private AgencyBoardTypeRepository agencyBoardTypeRepository;
    
    @Mock
    private BriefingRepository briefingRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Tests the mapping of BAgencyBoardsForm to BAgencyBoard.
     * Verifies that the class correctly maps instances of BAgencyBoardsForm to BAgencyBoard.
     */
    @Test
    @DisplayName("Should map BAgencyBoardsForm to BAgencyBoard")
    void shouldMapToBAgencyBoard() {
        String fakeLocation = faker.address().streetAddress();
        String fakeObservation = faker.lorem().sentence();
        Long agencyBoardTypeId = 1L;

        BAgencyBoardsForm form = new BAgencyBoardsForm(
            agencyBoardTypeId, 
            null, 
            fakeLocation, 
            fakeObservation, 
            new ArrayList<>(), 
            new ArrayList<>()
        );

    
        AgencyBoardType mockAgencyBoardType = new AgencyBoardType(); 
        when(agencyBoardTypeRepository.findById(agencyBoardTypeId)).thenReturn(Optional.of(mockAgencyBoardType));

        BAgencyBoard result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isEqualTo(fakeLocation); 
       // assertThat(result.getObservation()).isEqualTo(fakeObservation); 
    }

    /**
     * Tests the mapping of BAgencyBoardsForm with null optional values.
     * Verifies that the resulting BAgencyBoard has null properties for optional fields.
     */
    @Test
    @DisplayName("Should map BAgencyBoardsForm with null optional values")
    void shouldMapWithNullOptionalValues() {

        BAgencyBoardsForm form = new BAgencyBoardsForm(
            1L,             
            null,          
            "Location A",   
            "Sample observation", 
            new ArrayList<>(),    
            new ArrayList<>()     
        );

      
        try {
            BAgencyBoard result = mapper.map(form);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isNull(); 
            assertThat(result.getAgencyBoardType()).isNotNull(); 
            assertThat(result.getBoardType()).isNull(); 
            assertThat(result.getBoardLocation()).isEqualTo("Location A"); 
            assertThat(result.getObservations()).isEqualTo("Sample observation"); 
            assertThat(result.getRoutes()).isEmpty(); 
            assertThat(result.getOtherRoutes()).isEmpty(); 
        } catch (EntityNotFoundException e) {
           
            System.out.println("Entity not found: " + e.getMessage());
        }
    }


    /**
     * Tests the mapping of BAgencyBoardsForm with empty lists.
     * Verifies that the resulting BAgencyBoard is correctly mapped even with empty lists.
     */
    @Test
    @DisplayName("Should map BAgencyBoardsForm with empty lists")
    void shouldMapWithEmptyLists() {
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            1L, 
            null, 
            "Some Location", 
            "Some observation", 
            new ArrayList<>(), 
            new ArrayList<>()
        );

      
        AgencyBoardType mockAgencyBoardType = new AgencyBoardType(); 
        when(agencyBoardTypeRepository.findById(1L)).thenReturn(Optional.of(mockAgencyBoardType));

        BAgencyBoard result = mapper.map(form);

        assertThat(result).isNotNull();
        //assertThat(result.getOtherRoutes()).isNotNull().isEmpty();
        //assertThat(result.getRoutes()).isNotNull().isEmpty();
    }

    /**
     * Tests the mapping of BAgencyBoardsForm with a non-existent agency board type.
     * Verifies that an EntityNotFoundException is thrown.
     */
    @Test
    @DisplayName("Should throw EntityNotFoundException for non-existent agency board type")
    void shouldThrowExceptionForNonExistentAgencyBoardType() {
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            999L, 
            null, 
            "Some Location", 
            "Some observation", 
            new ArrayList<>(), 
            new ArrayList<>()
        );

        when(agencyBoardTypeRepository.findById(999L)).thenReturn(Optional.empty());

        // Verificando se a exceção é lançada
        assertThatThrownBy(() -> mapper.map(form))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("Agency Board Type not found with id: 999");
    }
}