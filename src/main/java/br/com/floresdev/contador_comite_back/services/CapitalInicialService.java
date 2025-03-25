package br.com.floresdev.contador_comite_back.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.floresdev.contador_comite_back.domain.capital_inicial.CapitalInicial;
import br.com.floresdev.contador_comite_back.domain.repositories.CapitalInicialRepository;

@Service
public class CapitalInicialService {

    @Autowired
    private CapitalInicialRepository repository;

    public CapitalInicial getOrCreateCapitalInicial() {
        Optional<CapitalInicial> capital = repository.findById(1L);
        if (capital.isPresent()) {
            return capital.get();
        }

        CapitalInicial newCapital = new CapitalInicial();
        newCapital.setId(1L);
        newCapital.setAmount(BigDecimal.ZERO);
        return repository.save(newCapital);
    }

    public CapitalInicial updateCapitalInicial(BigDecimal amount) {
        CapitalInicial capital = getOrCreateCapitalInicial();
        capital.setAmount(amount);
        return repository.save(capital);
    }
}
