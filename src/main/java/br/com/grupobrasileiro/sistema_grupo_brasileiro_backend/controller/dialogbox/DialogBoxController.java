package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.dialogbox;

import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/dialogs")
@Tag(name = "Dialog Box", description = "Managing briefing-related dialog messages")
public class DialogBoxController {
	
	private static final Logger logger = LoggerFactory.getLogger(DialogBoxController.class);

    @Autowired
    private DialogBoxService dialogBoxService;

    /**
     * Cria uma nova mensagem de diálogo.
     *
     * @param form os detalhes da mensagem a ser criada
     * @return a mensagem criada com status 201
     */
    @Operation(summary = "Create a new dialog message",
            description = "Creates a new dialog message and persists it in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PostMapping
    public ResponseEntity<DialogBoxView> createMessage(
            @Parameter(description = "Details of the message to be created", required = true)
            @Valid @RequestBody DialogBoxForm form) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando criação de uma nova mensagem de diálogo.", requestId);
        logger.debug("[{}] Dados recebidos: briefing ID = {}, mensagem = {}",
                requestId, form.idBriefing(), form.message());

        DialogBoxView dialogBoxView = dialogBoxService.createMessage(form);
        logger.info("[{}] Mensagem de diálogo criada com sucesso. ID da mensagem: {}",
                requestId, dialogBoxView.id());

        return new ResponseEntity<>(dialogBoxView, HttpStatus.CREATED);
    }

    /**
     * Obtém todas as mensagens de diálogo associadas a um briefing específico.
     *
     * @param idBriefing o ID do briefing para o qual as mensagens serão buscadas
     * @return as mensagens associadas ao briefing com status 200
     */
    @Operation(summary = "Get messages for a briefing",
            description = "Retrieves all dialog messages associated with a specific briefing.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Briefing not found")
    })
    @GetMapping("/briefing/{idBriefing}")
    @Transactional
    public ResponseEntity<Set<DialogBoxView>> getMessagesForBriefing(
            @Parameter(description = "ID of the briefing to retrieve messages for", required = true)
            @PathVariable Long idBriefing) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Buscando mensagens para o briefing com ID: {}", requestId, idBriefing);
    	
        Set<DialogBoxView> messages = dialogBoxService.getMessagesByBriefingId(idBriefing);
        if (messages.isEmpty()) {
            logger.warn("[{}] Nenhuma mensagem encontrada para o briefing com ID: {}",
                    requestId, idBriefing);
        } else {
            logger.info("[{}] {} mensagens encontradas para o briefing com ID: {}",
                    requestId, messages.size(), idBriefing);
        }
        
        return ResponseEntity.ok(messages);
    }
}