package br.com.floresdev.contador_comite_back.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.floresdev.contador_comite_back.domain.despesa.Despesa;
import br.com.floresdev.contador_comite_back.domain.repositories.DespesaRepository;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository repository;

    public Despesa createOrUpdateDespesa(Despesa despesa) {
        return repository.save(despesa);
    }

    public List<Despesa> getDespesas() {
        return repository.findAll();
    }

    public Optional<List<Despesa>> getDespesasByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public void deleteDespesa(Long id) {
        if (!repository.existsById(id)) {
            System.out.printf("Despesa com id '%d' não encontrada", id);
            return;
        }
        repository.deleteById(id);
    }

}
