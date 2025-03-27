package br.com.floresdev.contador_comite_back.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.floresdev.contador_comite_back.domain.repositories.VendaRepository;
import br.com.floresdev.contador_comite_back.domain.venda.Venda;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repository;

    @Autowired
    private CapitalService capitalService;

    public Venda createOrUpdateVenda(Venda venda) {
        venda = repository.save(venda);
        
        capitalService.addValue(venda.getTotalPrice());
        
        return venda;
    }

    public List<Venda> getVendas() {
        return repository.findAll();
    }

    public List<Venda> getVendasByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public void deleteVenda(Long id) {
        Venda venda = repository.findById(id).orElse(null);

        if (venda == null) {
            System.out.printf("Venda com id '%d' não encontrada", id);
            return;
        }

        capitalService.subtractValue(venda.getTotalPrice());
        
        repository.deleteById(id);
    }
}
