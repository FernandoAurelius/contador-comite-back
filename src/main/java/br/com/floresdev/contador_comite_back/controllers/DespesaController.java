package br.com.floresdev.contador_comite_back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.despesa.dto.DespesaDTO;
import br.com.floresdev.contador_comite_back.services.DespesaService;

@RestController
@RequestMapping
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
}
