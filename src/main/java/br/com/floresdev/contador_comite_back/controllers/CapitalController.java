package br.com.floresdev.contador_comite_back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.capital.dto.CapitalDTO;
import br.com.floresdev.contador_comite_back.services.CapitalService;

@RestController
@RequestMapping("/api/capital")
@CrossOrigin(origins = "*")
public class CapitalController {

    @Autowired
    private CapitalService service;

    @GetMapping
    public ResponseEntity<CapitalDTO> getCapital() {
        return ResponseEntity.ok(
            CapitalDTO.fromEntity(
                service.getOrCreateCapital()
            )
        );
    }

    @PutMapping("/initial")
    public ResponseEntity<CapitalDTO> updateCapitalInicial(CapitalDTO dto) {
        return ResponseEntity.ok(
            CapitalDTO.fromEntity(
                service.updateInitialCapital(dto.getInitialAmount())
            )
        );
    }

    @PutMapping("/current")
    public ResponseEntity<CapitalDTO> updateCurrentCapital(CapitalDTO dto) {
        return ResponseEntity.ok(
            CapitalDTO.fromEntity(
                service.updateCurrentCapital(dto.getCurrentAmount())
            )
        );
    }
}
