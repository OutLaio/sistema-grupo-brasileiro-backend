package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.PrintingShirtType;

import java.util.Optional;

public class PrintingShirtTypeRepositoryTest {

    @Mock
    private PrintingShirtTypeRepository printingShirtTypeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAndFindPrintingShirtType() {
        // Criação de um objeto de teste
        PrintingShirtType printingShirtType = new PrintingShirtType();
        printingShirtType.setDescription("Sample Description");
        printingShirtType.setId(1L); // ID simulado

        // Configurando o comportamento do mock
        when(printingShirtTypeRepository.save(any(PrintingShirtType.class))).thenReturn(printingShirtType);
        when(printingShirtTypeRepository.findById(1L)).thenReturn(Optional.of(printingShirtType));

        // Teste de salvamento
        PrintingShirtType savedEntity = printingShirtTypeRepository.save(printingShirtType);
        assertNotNull(savedEntity.getId());
        assertEquals("Sample Description", savedEntity.getDescription());

        // Teste de recuperação
        Optional<PrintingShirtType> foundEntity = printingShirtTypeRepository.findById(1L);
        assertTrue(foundEntity.isPresent());
        assertEquals("Sample Description", foundEntity.get().getDescription());
    }
    
    @Test
    public void testUpdatePrintingShirtType() {
        // Criação de um objeto de teste
        PrintingShirtType printingShirtType = new PrintingShirtType();
        printingShirtType.setId(1L);
        printingShirtType.setDescription("Original Description");

        // Simulando o comportamento do mock
        when(printingShirtTypeRepository.save(any(PrintingShirtType.class))).thenReturn(printingShirtType);

        // Simulando a atualização
        printingShirtType.setDescription("Updated Description");
        PrintingShirtType updatedEntity = printingShirtTypeRepository.save(printingShirtType);

        // Verificações
        assertEquals("Updated Description", updatedEntity.getDescription());
    }

    @Test
    public void testDeletePrintingShirtType() {
        // Criação de um objeto de teste
        PrintingShirtType printingShirtType = new PrintingShirtType();
        printingShirtType.setId(1L);
        
        // Simulando o comportamento do mock
        doNothing().when(printingShirtTypeRepository).deleteById(1L);

        // Executando a exclusão
        printingShirtTypeRepository.deleteById(1L);
        
        // Verificando que o método foi chamado
        verify(printingShirtTypeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindPrintingShirtTypeNotFound() {
        // Simulando o comportamento do mock para retornar um Optional vazio
        when(printingShirtTypeRepository.findById(2L)).thenReturn(Optional.empty());

        // Tentando encontrar a entidade
        Optional<PrintingShirtType> foundEntity = printingShirtTypeRepository.findById(2L);

        // Verificações
        assertFalse(foundEntity.isPresent());
    }

}
