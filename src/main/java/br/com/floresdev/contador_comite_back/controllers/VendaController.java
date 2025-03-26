package br.com.floresdev.contador_comite_back.controllers;

import java.math.BigDecimal;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    @Autowired
    private VendaService service;

    @PostMapping
    public ResponseEntity<VendaDTO> createVenda(@RequestBody @Valid VendaDTO vendaDTO) {
        if (vendaDTO.getTotalPrice() == null && vendaDTO.getUnitPrice() != null && vendaDTO.getQuantity() != null) {
            vendaDTO.setTotalPrice(
                    vendaDTO.getUnitPrice()
                            .multiply(
                                    BigDecimal.valueOf((vendaDTO.getQuantity())
                                    )
                            )
            );
        }

        Venda venda = service.createOrUpdateVenda(vendaDTO.toEntity());
        return ResponseEntity.ok(VendaDTO.fromEntity(venda));
    }

    @GetMapping
    public ResponseEntity<List<VendaDTO>> getVendas() {
        List<VendaDTO> vendas = service.getVendas()
            .stream()
            .map(VendaDTO::fromEntity)
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<VendaDTO>> getVendasByDate(
        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date
    ) {
        List<VendaDTO> vendas = service.getVendasByDate(date)
            .stream()
            .map(VendaDTO::fromEntity)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(vendas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaDTO> updateVenda(
        @PathVariable Long id,
        @RequestBody VendaDTO vendaDTO
    ) {
        if (!id.equals(vendaDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Venda venda = service.createOrUpdateVenda(vendaDTO.toEntity());
        return ResponseEntity.ok(VendaDTO.fromEntity(venda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        service.deleteVenda(id);
        return ResponseEntity.noContent().build();
    }

}
