package br.com.floresdev.contador_comite_back.domain.despesa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.floresdev.contador_comite_back.domain.despesa.Despesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaDTO {

    private Long id;
    private LocalDate date;
    private String item;
    private Integer quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private String notes;

    public Despesa toEntity() {
        return new Despesa(
            this.id,
            this.date,
            this.item,
            this.quantity,
            this.unitCost,
            this.totalCost,
            this.notes
        );
    }

    public static DespesaDTO fromEntity(Despesa despesa) {
        return new DespesaDTO(
            despesa.getId(),
            despesa.getDate(),
            despesa.getItem(),
            despesa.getQuantity(),
            despesa.getUnitCost(),
            despesa.getTotalCost(),
            despesa.getNotes()
        );
    }
}
