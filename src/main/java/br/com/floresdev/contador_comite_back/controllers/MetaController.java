package br.com.floresdev.contador_comite_back.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.meta.dto.MetaDTO;
import br.com.floresdev.contador_comite_back.services.MetaService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/meta")
@Tag(name = "Meta", description = "API para gerenciamento de metas financeiras")
public class MetaController {

    @Autowired
    private MetaService service;

    @GetMapping
    @Operation(summary = "Obter meta atual", description = "Recupera a meta financeira atual ou cria uma nova caso não exista")
    @ApiResponse(responseCode = "200", description = "Meta recuperada com sucesso",
                content = @Content(schema = @Schema(implementation = MetaDTO.class)))
    public ResponseEntity<MetaDTO> getMeta() {
        return ResponseEntity.ok(
            MetaDTO.fromEntity(service.getOrCreateMeta())
        );
    }
}
