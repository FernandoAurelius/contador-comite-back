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

@RestController
@RequestMapping("/api/despesas")
@CrossOrigin(origins = "*")
public class DespesaController {

    @Autowired
    private DespesaService service;

    @PostMapping
    public ResponseEntity<DespesaDTO> createDespesa(@RequestBody DespesaDTO dto) {
        return ResponseEntity.ok(
            DespesaDTO.fromEntity(
                service.createOrUpdateDespesa(dto.toEntity())
            )
        );
    }

    @GetMapping
    public ResponseEntity<List<DespesaDTO>> getDespesas() {
        return ResponseEntity.ok(
            service.getDespesas().
                stream()
                .map(DespesaDTO::fromEntity)
                .collect(Collectors.toList())
        );
    } 

    @GetMapping("/{date}")
    public ResponseEntity<List<DespesaDTO>> getDespesasByDate(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
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
    public ResponseEntity<DespesaDTO> updateDespesa(@PathVariable Long id, @RequestBody DespesaDTO dto) {
        if (!id.equals(dto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Despesa despesa = service.createOrUpdateDespesa(dto.toEntity());
        return ResponseEntity.ok(DespesaDTO.fromEntity(despesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDespesa(@PathVariable Long id) {
        service.deleteDespesa(id);
        return ResponseEntity.noContent().build();
    }
}
