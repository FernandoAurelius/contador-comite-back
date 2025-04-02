package br.com.floresdev.contador_comite_back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.financeiro.ReportDTO;
import br.com.floresdev.contador_comite_back.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reports/")
@Tag(name = "Relatórios", description = "API para geração de relatórios financeiros")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping()
    @Operation(
        summary = "Gerar relatório financeiro", 
        description = "Gera um relatório financeiro baseado no período especificado e no tipo de evento"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso",
                    content = @Content(schema = @Schema(implementation = ReportDTO.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros de consulta inválidos",
                    content = @Content)
    })
    public ResponseEntity<ReportDTO> getReport(
        @Parameter(
            description = "Período para o relatório (daily, weekly, monthly, yearly)", 
            required = true
        ) @RequestParam String period, 
        @Parameter(
            description = "Indica se o relatório é para eventos de trote (true) ou eventos regulares (false)", 
            required = true
        ) @RequestParam boolean trote
    ) {
        try {
            return ResponseEntity.ok(service.getReport(period, trote));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
