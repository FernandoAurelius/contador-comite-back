package br.com.floresdev.contador_comite_back.domain.financeiro;

import java.math.BigDecimal;
import java.util.Map;

import br.com.floresdev.contador_comite_back.domain.venda.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceitaResumoDTO<T> {

    private BigDecimal totalValue;
    private Map<ItemType, T> productDetails;

}
