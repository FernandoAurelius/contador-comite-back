package br.com.floresdev.contador_comite_back.domain.capital;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Essa classe vai ser uma instância singleton - não faz sentido ter mais de um capital
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Capital {

    public static final Long INSTANCE_ID = 1L;

    @Id
    private Long id = INSTANCE_ID;

    // Para inserção do valor inicial do início do rastreio no app
    private BigDecimal currentAmount;
    private BigDecimal initialAmount;
    private BigDecimal totalAmount;

    private Boolean initialSetted;
}
