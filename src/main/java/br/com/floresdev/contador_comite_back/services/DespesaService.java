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

    @Autowired
    private CapitalService capitalService;

    public Despesa createOrUpdateDespesa(Despesa despesa) {
        despesa = repository.save(despesa);

        capitalService.subtractValue(despesa.getTotalCost());

        return despesa;
    }

    public List<Despesa> getDespesas() {
        return repository.findAll();
    }

    public Optional<List<Despesa>> getDespesasByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public void deleteDespesa(Long id) {
        Despesa despesa = repository.findById(id).orElse(null);

        if (despesa == null) {
            System.out.printf("Despesa com id %d não encontrada.", id);
        }

        capitalService.addValue(despesa.getTotalCost());

        repository.deleteById(id);
    }

}
