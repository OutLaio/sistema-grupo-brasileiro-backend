package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.printed;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form.PrintedForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.form.BPrintedsFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.BPrintedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PrintedServiceTest {

    @Mock
    private BPrintedRepository bPrintedRepository;

    @Mock
    private BPrintedsFormMapper bPrintedsFormMapper;

    @InjectMocks
    private PrintedService printedService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Arrange
        PrintedForm form = new PrintedForm(1L, 2L, "A4", 2, 20); 
        Briefing briefing = new Briefing();
        BPrinted bPrinted = new BPrinted();

        // Configura o comportamento do mapper
        when(bPrintedsFormMapper.map(form)).thenReturn(bPrinted);

        // Act
        printedService.register(form, briefing);

        // Assert
        // Verifica se o mapper foi chamado corretamente
        verify(bPrintedsFormMapper).map(form);
        // Verifica se o repositório salvou o objeto mapeado
        verify(bPrintedRepository).save(bPrinted);
        // Confirma se o briefing foi associado corretamente ao objeto mapeado
        assertEquals(briefing, bPrinted.getBriefing());
    }
}
