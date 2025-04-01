package br.com.floresdev.contador_comite_back.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.venda.Venda;
import br.com.floresdev.contador_comite_back.domain.venda.dto.VendaDTO;
import br.com.floresdev.contador_comite_back.services.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "*")
@Tag(name = "Vendas", description = "API para gerenciamento de vendas")
public class VendaController {

    @Autowired
    private VendaService service;

    @PostMapping
    @Operation(summary = "Criar uma nova venda", description = "Cria um novo registro de venda no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda criada com sucesso", 
                     content = @Content(schema = @Schema(implementation = VendaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", 
                     content = @Content)
    })
    public ResponseEntity<VendaDTO> createVenda(@RequestBody @Valid VendaDTO vendaDTO) {
        Venda venda = service.createOrUpdateVenda(vendaDTO.toEntity());
        return ResponseEntity.ok(VendaDTO.fromEntity(venda));
    }

    @GetMapping
    @Operation(summary = "Listar todas as vendas", description = "Retorna uma lista com todas as vendas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de vendas recuperada com sucesso", 
                 content = @Content(array = @ArraySchema(schema = @Schema(implementation = VendaDTO.class))))
    public ResponseEntity<List<VendaDTO>> getVendas() {
        List<VendaDTO> vendas = service.getVendas()
            .stream()
            .map(VendaDTO::fromEntity)
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{date}")
    @Operation(summary = "Buscar vendas por data", description = "Retorna todas as vendas realizadas em uma data específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vendas encontradas para a data especificada", 
                     content = @Content(array = @ArraySchema(schema = @Schema(implementation = VendaDTO.class)))),
        @ApiResponse(responseCode = "400", description = "Formato de data inválido", 
                     content = @Content)
    })
    public ResponseEntity<List<VendaDTO>> getVendasByDate(
        @Parameter(description = "Data no formato dd-MM-yyyy") 
        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date
    ) {
        List<VendaDTO> vendas = service.getVendasByDate(date)
            .stream()
            .map(VendaDTO::fromEntity)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(vendas);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar venda", description = "Atualiza os dados de uma venda existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso", 
                     content = @Content(schema = @Schema(implementation = VendaDTO.class))),
        @ApiResponse(responseCode = "400", description = "ID na URL não corresponde ao ID no corpo da requisição", 
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", 
                     content = @Content)
    })
    public ResponseEntity<VendaDTO> updateVenda(
        @Parameter(description = "ID da venda") @PathVariable Long id,
        @RequestBody VendaDTO vendaDTO
    ) {
        if (!id.equals(vendaDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Venda venda = service.createOrUpdateVenda(vendaDTO.toEntity());
        return ResponseEntity.ok(VendaDTO.fromEntity(venda));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir venda", description = "Remove uma venda do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venda excluída com sucesso", 
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", 
                     content = @Content)
    })
    public ResponseEntity<Void> deleteVenda(
        @Parameter(description = "ID da venda") @PathVariable Long id
    ) {
        service.deleteVenda(id);
        return ResponseEntity.noContent().build();
    }
}
