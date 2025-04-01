package br.com.floresdev.contador_comite_back.domain.venda;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.floresdev.contador_comite_back.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vendas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(nullable = false)
    private BigDecimal purchasePrice;

    private Integer quantity;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    private String notes;

    @ManyToOne
    private User user;

    // Como o valor de purchasePrice é baseado no valor de cada itemType definido no Enum, temos que calcular e setar ele dinamicamente
    @PrePersist
    @PreUpdate
    @SuppressWarnings("unused")
    private void updatePurchasePrice() {
        if (this.itemType != null) {
            this.purchasePrice = this.getItemType().getPrice();
        }
    }
}
