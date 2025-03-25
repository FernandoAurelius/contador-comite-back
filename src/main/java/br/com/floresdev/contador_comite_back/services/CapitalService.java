package br.com.floresdev.contador_comite_back.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.floresdev.contador_comite_back.domain.capital.Capital;
import br.com.floresdev.contador_comite_back.domain.repositories.CapitalRepository;

@Service
public class CapitalService {

    @Autowired
    private CapitalRepository repository;

    public Capital getOrCreateCapital() {
        Optional<Capital> capital = repository.findById(1L);
        if (capital.isPresent()) {
            return capital.get();
        }

        return repository.save(new Capital(Capital.INSTANCE_ID, BigDecimal.ZERO, BigDecimal.ZERO));
    }

    public Capital updateInitialCapital(BigDecimal amount) {
        Capital capital = getOrCreateCapital();
        capital.setInitialAmount(amount);
        return repository.save(capital);
    }

    public Capital updateCurrentCapital(BigDecimal amount) {
        Capital capital = getOrCreateCapital();
        capital.setCurrentAmount(amount);
        return repository.save(capital);
    }
}
