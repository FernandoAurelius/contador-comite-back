package br.com.floresdev.contador_comite_back.domain.meta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.floresdev.contador_comite_back.domain.meta.Meta;
import br.com.floresdev.contador_comite_back.domain.meta.MetaStatus;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaDTO {
    private Long id;
    private String description;
    private BigDecimal goalValue;
    private BigDecimal currentValue;
    private LocalDate startDate;
    private LocalDate endDate;
    private MetaStatus status;

    public Meta toEntity() {
        return new Meta(
            id,
            description,
            goalValue,
            currentValue,
            startDate,
            endDate,
            status
        );
    }

    public static MetaDTO fromEntity(Meta meta) {
        return new MetaDTO(
            meta.getId(),
            meta.getDescription(),
            meta.getGoalValue(),
            meta.getCurrentValue(),
            meta.getStartDate(),
            meta.getEndDate(),
            meta.getStatus()
        );
    }
}
