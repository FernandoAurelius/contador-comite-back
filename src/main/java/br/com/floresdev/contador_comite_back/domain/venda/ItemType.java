package br.com.floresdev.contador_comite_back.domain.venda;

import java.math.BigDecimal;

import lombok.Getter;

public enum ItemType {
    REFRI_COPO(BigDecimal.valueOf(0.8)),
    REFRI_GARRAFA(BigDecimal.valueOf(8.0)),
    PICOLE(BigDecimal.valueOf(1.6)),
    CARTELA_BINGO(BigDecimal.valueOf(0.0)),
    CADEIA_DO_AMOR(BigDecimal.valueOf(0.0)),
    CORREIO_ELEGANTE(BigDecimal.valueOf(0.0)),
    OUTROS(BigDecimal.valueOf(0.0));

    @Getter
    private final BigDecimal price;

    ItemType(BigDecimal price) {
        this.price = price;
    }
}
