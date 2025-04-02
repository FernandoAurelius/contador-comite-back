package br.com.floresdev.contador_comite_back.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.capital.dto.CapitalDTO;
import br.com.floresdev.contador_comite_back.services.CapitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/capital")
@Tag(name = "Capital", description = "API para gerenciamento do capital financeiro")
public class CapitalController {

    @Autowired
    private CapitalService service;

    @GetMapping
    @Operation(summary = "Obter capital atual", description = "Recupera as informações do capital atual ou cria um novo registro caso não exista")
    @ApiResponse(responseCode = "200", description = "Capital recuperado com sucesso",
                content = @Content(schema = @Schema(implementation = CapitalDTO.class)))
    public ResponseEntity<CapitalDTO> getCapital() {
        return ResponseEntity.ok(
            CapitalDTO.fromEntity(
                service.getOrCreateCapital()
            )
        );
    }

    @PutMapping("/initial")
    @Operation(summary = "Atualizar capital inicial", description = "Define um novo valor para o capital inicial")
    @ApiResponse(responseCode = "200", description = "Capital inicial atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = CapitalDTO.class)))
    public ResponseEntity<CapitalDTO> updateCapitalInicial(
        @Parameter(description = "Dados do capital com novo valor inicial") 
        @RequestBody CapitalDTO dto
    ) {
        return ResponseEntity.ok(
            CapitalDTO.fromEntity(
                service.updateInitialCapital(dto.getInitialAmount())
            )
        );
    }

    @PutMapping("/current/add")
    @Operation(summary = "Adicionar valor ao capital", description = "Adiciona um valor ao capital atual")
    @ApiResponse(responseCode = "200", description = "Valor adicionado com sucesso",
                content = @Content(schema = @Schema(implementation = CapitalDTO.class)))
    public ResponseEntity<CapitalDTO> addCapital(
        @Parameter(description = "Valor a ser adicionado") 
        @RequestBody BigDecimal amount
    ) {
        return ResponseEntity.ok(
            CapitalDTO.fromEntity(
                service.addValue(amount)
            )
        );
    }

    @PutMapping("/current/subtract")
    @Operation(summary = "Subtrair valor do capital", description = "Subtrai um valor do capital atual")
    @ApiResponse(responseCode = "200", description = "Valor subtraído com sucesso",
                content = @Content(schema = @Schema(implementation = CapitalDTO.class)))
    public ResponseEntity<CapitalDTO> subtractCapital(
        @Parameter(description = "Valor a ser subtraído") 
        @RequestBody BigDecimal amount
    ) {
        return ResponseEntity.ok(
            CapitalDTO.fromEntity(
                service.subtractValue(amount)
            )
        );
    }
}
