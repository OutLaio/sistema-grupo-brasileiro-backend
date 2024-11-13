package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.project;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.DataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para gerenciamento de dados pré cadastrados.
 */
@RestController
@RequestMapping("/api/v1/data")
@Tag(name = "Data", description = "Data Management")
public class DataController {

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private DataService dataService;

    @Operation(summary = "Retrieve profiles", description = "Fetches all available profiles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profiles")
    })
    @GetMapping("/profiles")
    public ResponseEntity<?> getProfiles() {
        return ResponseEntity.ok().body(dataService.getProfiles());
    }

    @Operation(summary = "Retrieve briefing types", description = "Fetches all available briefing types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved briefing types")
    })
    @GetMapping("/briefing-types")
    public ResponseEntity<?> getBriefingTypes() {
        return ResponseEntity.ok().body(dataService.getBriefingTypes());
    }

    @Operation(summary = "Retrieve printed types", description = "Fetches all available printed types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved printed types")
    })
    @GetMapping("/printed-types")
    public ResponseEntity<?> getPrintedTypes() {
        return ResponseEntity.ok().body(dataService.getPrintedTypes());
    }

    @Operation(summary = "Retrieve printing types", description = "Fetches all available printing types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved printing types")
    })
    @GetMapping("/printing-types")
    public ResponseEntity<?> getPrintingTypes() {
        return ResponseEntity.ok().body(dataService.getPrintingTypes());
    }

    @Operation(summary = "Retrieve printing shirt types", description = "Fetches all available printing shirt types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved printing shirt types")
    })
    @GetMapping("/printing-shirt-types")
    public ResponseEntity<?> getPrintingShirtTypes() {
        return ResponseEntity.ok().body(dataService.getPrintingShirtTypes());
    }

    @Operation(summary = "Retrieve stamps", description = "Fetches all available stamps.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stamps")
    })
    @GetMapping("/stamps")
    public ResponseEntity<?> getStamps() {
        return ResponseEntity.ok().body(dataService.getStamps());
    }

    @Operation(summary = "Retrieve calendar types", description = "Fetches all available calendar types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved calendar types")
    })
    @GetMapping("/calendar-types")
    public ResponseEntity<?> getCalendarTypes() {
        return ResponseEntity.ok().body(dataService.getCalendarTypes());
    }

    @Operation(summary = "Retrieve gift types", description = "Fetches all available gift types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved gift types")
    })
    @GetMapping("/gift-types")
    public ResponseEntity<?> getGiftTypes() {
        return ResponseEntity.ok().body(dataService.getGiftTypes());
    }

    @Operation(summary = "Retrieve agency board types", description = "Fetches all available agency board types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved agency board types")
    })
    @GetMapping("/agency-board-types")
    public ResponseEntity<?> getAgencyBoardTypes() {
        return ResponseEntity.ok().body(dataService.getAgencyBoardTypes());
    }

    @Operation(summary = "Retrieve board types", description = "Fetches all available board types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved board types")
    })
    @GetMapping("/board-types")
    public ResponseEntity<?> getBoardTypes() {
        return ResponseEntity.ok().body(dataService.getBoardTypes());
    }

    @Operation(summary = "Retrieve cities", description = "Fetches all available cities.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cities")
    })
    @GetMapping("/cities")
    public ResponseEntity<?> getCities() {
        return ResponseEntity.ok().body(dataService.getCities());
    }

    @Operation(summary = "Retrieve companies", description = "Fetches all available companies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved companies")
    })
    @GetMapping("/companies")
    public ResponseEntity<?> getCompanies() {
        return ResponseEntity.ok().body(dataService.getCompanies());
    }

    @Operation(summary = "Retrieve sticker types", description = "Fetches all available sticker types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved sticker types")
    })
    @GetMapping("/sticker-types")
    public ResponseEntity<?> getStickerTypes() {
        return ResponseEntity.ok().body(dataService.getStickerTypes());
    }

    @Operation(summary = "Retrieve sticker information types", description = "Fetches all available sticker information types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved sticker information types")
    })
    @GetMapping("/sticker-information-types")
    public ResponseEntity<?> getStickerInformationTypes() {
        return ResponseEntity.ok().body(dataService.getStickerInformationTypes());
    }

    @Operation(summary = "Retrieve materials", description = "Fetches all available materials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved materials")
    })
    @GetMapping("/materials")
    public ResponseEntity<?> getMaterials() {
        return ResponseEntity.ok().body(dataService.getMaterials());
    }

    @Operation(summary = "Retrieve handout types", description = "Fetches all available handout types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved handout types")
    })
    @GetMapping("/handout-types")
    public ResponseEntity<?> getHandoutTypes() {
        return ResponseEntity.ok().body(dataService.getHandoutTypes());
    }

    @Operation(summary = "Retrieve stationery types", description = "Fetches all available stationery types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved stationery types")
    })
    @GetMapping("/stationery-types")
    public ResponseEntity<?> getStationeryTypes() {
        return ResponseEntity.ok().body(dataService.getStationeryTypes());
    }

    @Operation(summary = "Retrieve other items", description = "Fetches all available other items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved other items")
    })
    @GetMapping("/other-items")
    public ResponseEntity<?> getOtherItems() {
        return ResponseEntity.ok().body(dataService.getOtherItems());
    }
}
