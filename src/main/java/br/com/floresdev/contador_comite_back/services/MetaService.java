package br.com.floresdev.contador_comite_back.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.floresdev.contador_comite_back.domain.meta.Meta;
import br.com.floresdev.contador_comite_back.domain.meta.MetaStatus;
import br.com.floresdev.contador_comite_back.domain.repositories.MetaRepository;

@Service
public class MetaService {

    @Autowired
    private MetaRepository repository;
    
    public Meta getOrCreateMeta() {
        Optional<Meta> metaOpt = repository.findById(Meta.INSTANCE_ID);
        if (metaOpt.isPresent()) {
            return metaOpt.get();
        }

        return repository.save(createDefaultMeta());
    }

    private Meta createDefaultMeta() {
        return new Meta(
            Meta.INSTANCE_ID,
            "Meta para formatura",
            new BigDecimal("14520.00"), // Valor meta para ~100 alunos
            BigDecimal.ZERO,
            LocalDate.now(),
            LocalDate.from(DateTimeFormatter.ofPattern("dd/MM/yyyy").parse("29/08/2025")),
            MetaStatus.ATIVA
        );
    }

    public Meta updateMeta(Meta meta) {
        meta.setId(Meta.INSTANCE_ID);
        return repository.save(meta);
    }

    public Meta addValue(BigDecimal value) {
        Meta meta = getOrCreateMeta();
        meta.setCurrentValue(meta.getCurrentValue().add(value));

        if (meta.getCurrentValue().compareTo(meta.getGoalValue()) >= 0) {
            meta.setStatus(MetaStatus.CONCLUIDA);
        }
        
        return updateMeta(meta);
    }

    public Meta subtractValue(BigDecimal value) {
        Meta meta = getOrCreateMeta();
        meta.setCurrentValue(meta.getCurrentValue().subtract(value));

        // Essa expressão é curiosa pois compareTo() retorna três valores possíveis: -1, 0 ou 1, sendo -1 menor que, 0 igual a e 1 maior que
        // Então, se o valor atual for menor que o valor da meta (se o retorno for -1, ou seja, menor que 0), a meta ainda está ativa
        if (meta.getCurrentValue().compareTo(meta.getGoalValue()) < 0) {
            meta.setStatus(MetaStatus.ATIVA);
        }

        return updateMeta(meta);
    }
}
