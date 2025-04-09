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

    @Autowired
    private MetaService metaService;

    public Capital getOrCreateCapital() {
        Optional<Capital> capital = repository.findById(1L);
        if (capital.isPresent()) {
            return capital.get();
        }

        return repository.save(new Capital(Capital.INSTANCE_ID, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
    }

    public Capital updateInitialCapital(BigDecimal amount) {
        Capital capital = getOrCreateCapital();

        capital.setInitialAmount(amount);
        capital.setTotalAmount(capital.getTotalAmount().add(amount));
        capital = repository.save(capital);

        metaService.addValue(amount);

        return capital;
    }

    public Capital addValue(BigDecimal amount) {
        Capital capital = getOrCreateCapital();

        metaService.addValue(amount);
        capital.setCurrentAmount(capital.getCurrentAmount().add(amount));
        capital.setTotalAmount(capital.getTotalAmount().add(amount));

        return repository.save(capital);
    }

    public Capital subtractValue(BigDecimal amount) {
        Capital capital = getOrCreateCapital();

        metaService.subtractValue(amount);
        capital.setCurrentAmount(capital.getCurrentAmount().subtract(amount));

        return repository.save(capital);
    }
}
