package br.com.floresdev.contador_comite_back.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.despesa.Despesa;
import br.com.floresdev.contador_comite_back.domain.despesa.dto.DespesaDTO;
import br.com.floresdev.contador_comite_back.services.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/despesas")
@CrossOrigin(origins = "*")
@Tag(name = "Despesas", description = "API para gerenciamento de despesas")
public class DespesaController {

    @Autowired
    private DespesaService service;

    @PostMapping
    @Operation(summary = "Criar nova despesa", description = "Registra uma nova despesa no sistema")
    @ApiResponse(responseCode = "200", description = "Despesa criada com sucesso",
                content = @Content(schema = @Schema(implementation = DespesaDTO.class)))
    public ResponseEntity<DespesaDTO> createDespesa(@RequestBody DespesaDTO dto) {
        return ResponseEntity.ok(
            DespesaDTO.fromEntity(
                service.createOrUpdateDespesa(dto.toEntity())
            )
        );
    }

    @GetMapping
    @Operation(summary = "Listar todas as despesas", description = "Recupera todas as despesas registradas no sistema")
    @ApiResponse(responseCode = "200", description = "Lista de despesas recuperada com sucesso",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = DespesaDTO.class))))
    public ResponseEntity<List<DespesaDTO>> getDespesas() {
        return ResponseEntity.ok(
            service.getDespesas().
                stream()
                .map(DespesaDTO::fromEntity)
                .collect(Collectors.toList())
        );
    } 

    @GetMapping("/{date}")
    @Operation(summary = "Buscar despesas por data", description = "Recupera todas as despesas registradas em uma data específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Despesas encontradas para a data especificada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DespesaDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Nenhuma despesa encontrada para esta data",
                    content = @Content),
        @ApiResponse(responseCode = "400", description = "Formato de data inválido",
                    content = @Content)
    })
    public ResponseEntity<List<DespesaDTO>> getDespesasByDate(
        @Parameter(description = "Data no formato dd-MM-yyyy")
        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date
    ) {
        Optional<List<Despesa>> despesasOpt = service.getDespesasByDate(date);
        List<Despesa> despesas = despesasOpt.orElse(null);

        if (despesas == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
            despesas.stream()
                .map(DespesaDTO::fromEntity)
                .collect(Collectors.toList())
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar despesa", description = "Atualiza os dados de uma despesa existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Despesa atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DespesaDTO.class))),
        @ApiResponse(responseCode = "400", description = "ID na URL não corresponde ao ID no corpo da requisição",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Despesa não encontrada",
                    content = @Content)
    })
    public ResponseEntity<DespesaDTO> updateDespesa(
        @Parameter(description = "ID da despesa") @PathVariable Long id, 
        @RequestBody DespesaDTO dto
    ) {
        if (!id.equals(dto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Despesa despesa = service.createOrUpdateDespesa(dto.toEntity());
        return ResponseEntity.ok(DespesaDTO.fromEntity(despesa));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir despesa", description = "Remove uma despesa do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Despesa excluída com sucesso",
                    content = @Content),
        @ApiResponse(responseCode = "404", description = "Despesa não encontrada",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteDespesa(
        @Parameter(description = "ID da despesa") @PathVariable Long id
    ) {
        service.deleteDespesa(id);
        return ResponseEntity.noContent().build();
    }
}
