package br.com.floresdev.contador_comite_back.domain.financeiro;

import java.math.BigDecimal;
import java.util.List;

import br.com.floresdev.contador_comite_back.domain.despesa.dto.DespesaDTO;

public record DespesaReportDTO(BigDecimal totalCost, List<DespesaDTO> despesas) {

}
