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

        return repository.save(new Capital(Capital.INSTANCE_ID, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, false));
    }

    public Capital updateInitialCapital(BigDecimal amount) {
        Capital capital = getOrCreateCapital();
        
        // Calcular a diferença para atualizar na meta
        BigDecimal difference = amount.subtract(capital.getInitialAmount());
        
        capital.setInitialAmount(amount);
        // Atualiza o total considerando a diferença entre o novo e o antigo valor inicial
        capital.setTotalAmount(capital.getTotalAmount().add(difference));
        capital.setInitialSetted(true);
        capital = repository.save(capital);

        // Atualiza a meta com a diferença, não com o valor total
        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            metaService.addValue(difference);
        }
        
        if (difference.compareTo(BigDecimal.ZERO) < 0) {
            metaService.subtractValue(difference.abs());
        }
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
