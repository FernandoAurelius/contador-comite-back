package br.com.floresdev.contador_comite_back.domain.meta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.floresdev.contador_comite_back.domain.meta.Meta;
import br.com.floresdev.contador_comite_back.domain.meta.MetaStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaDTO {
    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Long id;
    private String description;
    private BigDecimal goalValue;
    private BigDecimal currentValue;
    private String startDate;
    private String endDate;
    private MetaStatus status;

    public Meta toEntity() {
        return new Meta(
            id,
            description,
            goalValue,
            currentValue,
            LocalDate.parse(startDate, fmt),
            LocalDate.parse(endDate, fmt),
            status
        );
    }

    public static MetaDTO fromEntity(Meta meta) {
        return new MetaDTO(
            meta.getId(),
            meta.getDescription(),
            meta.getGoalValue(),
            meta.getCurrentValue(),
            meta.getStartDate().format(fmt),
            meta.getEndDate().format(fmt),
            meta.getStatus()
        );
    }
}
