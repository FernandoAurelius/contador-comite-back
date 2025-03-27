package br.com.floresdev.contador_comite_back.domain.venda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.floresdev.contador_comite_back.domain.venda.ItemType;
import br.com.floresdev.contador_comite_back.domain.venda.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {

    private Long id;
    private String date;
    private ItemType itemType;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String notes;

    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Venda toEntity() {
        Venda venda = new Venda();

        venda.setId(this.id);
        venda.setDate(
            LocalDate.from(fmt.parse(this.date))
        );
        venda.setItemType(this.itemType);
        venda.setQuantity(this.quantity);
        venda.setUnitPrice(this.unitPrice);
        venda.setTotalPrice(this.totalPrice);
        venda.setNotes(this.notes);
        
        return venda;
    }

    public static VendaDTO fromEntity(Venda venda) {
        VendaDTO dto = new VendaDTO();

        dto.setId(venda.getId());
        dto.setDate(venda.getDate().format(fmt));
        dto.setItemType(venda.getItemType());
        dto.setQuantity(venda.getQuantity());
        dto.setUnitPrice(venda.getUnitPrice());
        dto.setTotalPrice(venda.getTotalPrice());
        dto.setNotes(venda.getNotes());

        return dto;
    }
}
