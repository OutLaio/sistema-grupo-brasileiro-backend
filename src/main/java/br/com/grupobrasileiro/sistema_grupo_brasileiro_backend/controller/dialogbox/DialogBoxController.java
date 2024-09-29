package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.dialogbox;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/dialogs")
@Tag(name = "Dialog Box", description = "Managing briefing-related dialog messages")
public class DialogBoxController {

    @Autowired
    private DialogBoxService dialogBoxService;

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
        DialogBoxView dialogBoxView = dialogBoxService.createMessage(form);
        return new ResponseEntity<>(dialogBoxView, HttpStatus.CREATED);
    }

    @Operation(summary = "Get messages for a briefing",
            description = "Retrieves all dialog messages associated with a specific briefing.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Briefing not found")
    })
    @GetMapping("/briefing/{idBriefing}")
    public ResponseEntity<Set<DialogBoxView>> getMessagesForBriefing(
            @Parameter(description = "ID of the briefing to retrieve messages for", required = true)
            @PathVariable Long idBriefing) {
        Set<DialogBoxView> messages = dialogBoxService.getMessagesByBriefingId(idBriefing);
        return ResponseEntity.ok(messages);
    }
}