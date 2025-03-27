package br.com.floresdev.contador_comite_back.domain.meta;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meta {

    // A ideia é que a meta seja única, então o id é fixo
    public static final Long INSTANCE_ID = 1L;

    @Id
    private Long id = INSTANCE_ID;

    private String description;

    private BigDecimal goalValue;
    private BigDecimal currentValue;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private MetaStatus status;
}
