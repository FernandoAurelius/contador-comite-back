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

    public Venda createOrUpdateVenda(Venda venda) {
        return repository.save(venda);
    }

    public List<Venda> getVendas() {
        return repository.findAll();
    }

    public List<Venda> getVendasByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public void deleteVenda(Long id) {
        if (!repository.existsById(id)) {
            System.out.printf("Venda com id '%d' não encontrada", id);
            return;
        }
        repository.deleteById(id);
    }
}
