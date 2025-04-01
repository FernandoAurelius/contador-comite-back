package br.com.floresdev.contador_comite_back.domain.despesa.dto;

import java.math.BigDecimal;

public record CostSummaryDTO(String item, BigDecimal totalCost) {}
