package br.com.floresdev.contador_comite_back.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.meta.dto.MetaDTO;
import br.com.floresdev.contador_comite_back.services.MetaService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/meta")
@CrossOrigin(origins = "*")
public class MetaController {

    @Autowired
    private MetaService service;

    @GetMapping
    public ResponseEntity<MetaDTO> getMeta() {
        return ResponseEntity.ok(
            MetaDTO.fromEntity(service.getOrCreateMeta())
        );
    }
}
