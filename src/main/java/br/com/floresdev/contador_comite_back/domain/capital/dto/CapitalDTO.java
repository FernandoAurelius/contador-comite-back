package br.com.floresdev.contador_comite_back.domain.capital.dto;

import java.math.BigDecimal;

import br.com.floresdev.contador_comite_back.domain.capital.Capital;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CapitalDTO {
    private Long id;
    private BigDecimal currentAmount;
    private BigDecimal initialAmount;

    public Capital toEntity() {
        return new Capital(id, currentAmount, initialAmount);
    }
    
    public static CapitalDTO fromEntity(Capital capital) {
        return new CapitalDTO(
            capital.getId(),
            capital.getCurrentAmount(),
            capital.getInitialAmount()
        );
    }
}
