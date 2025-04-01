package br.com.floresdev.contador_comite_back.domain.venda.dto;

import java.math.BigDecimal;

public record ProductSummaryTroteDTO(
    BigDecimal cartelaSum,
    Double cartelaPercentage,
    
    BigDecimal correioSum,
    Double correioPercentage,
    
    BigDecimal cadeiaSum,
    Double cadeiaPercentage,

    BigDecimal outroSum,
    Double outroPercentage
) {}
