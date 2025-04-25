apackage br.com.floresdev.contador_comite_back.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.meta.Meta;
import br.com.floresdev.contador_comite_back.domain.meta.dto.MetaDTO;
import br.com.floresdev.contador_comite_back.services.MetaService;
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
    
    @PutMapping
    @Operation(summary = "Atualizar meta", description = "Atualiza os dados da meta financeira")
    @ApiResponse(responseCode = "200", description = "Meta atualizada com sucesso",
                content = @Content(schema = @Schema(implementation = MetaDTO.class)))
    public ResponseEntity<MetaDTO> updateMeta(@RequestBody MetaDTO metaDTO) {
        Meta meta = metaDTO.toEntity();
        return ResponseEntity.ok(
            MetaDTO.fromEntity(service.updateMeta(meta))
        );
    }
    
    @PostMapping("/add")
    @Operation(summary = "Adicionar valor à meta", description = "Adiciona um valor ao montante atual da meta")
    @ApiResponse(responseCode = "200", description = "Valor adicionado com sucesso",
                content = @Content(schema = @Schema(implementation = MetaDTO.class)))
    public ResponseEntity<MetaDTO> addValue(@RequestParam BigDecimal value) {
        return ResponseEntity.ok(
            MetaDTO.fromEntity(service.addValue(value))
        );
    }
    
    @PostMapping("/subtract")
    @Operation(summary = "Subtrair valor da meta", description = "Subtrai um valor do montante atual da meta")
    @ApiResponse(responseCode = "200", description = "Valor subtraído com sucesso",
                content = @Content(schema = @Schema(implementation = MetaDTO.class)))
    public ResponseEntity<MetaDTO> subtractValue(@RequestParam BigDecimal value) {
        return ResponseEntity.ok(
            MetaDTO.fromEntity(service.subtractValue(value))
        );
    }
}
