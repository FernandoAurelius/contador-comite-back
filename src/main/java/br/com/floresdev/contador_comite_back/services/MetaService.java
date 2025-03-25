package br.com.floresdev.contador_comite_back.services;

import java.math.BigDecimal;
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
            null,
            null,
            MetaStatus.ATIVA
        );
    }

    public Meta updateMeta(Meta meta) {
        meta.setId(Meta.INSTANCE_ID);
        return repository.save(meta);
    }

    public Meta updateMetaValue(BigDecimal currentValue) {
        Meta meta = getOrCreateMeta();
        meta.setCurrentValue(currentValue);

        if (currentValue.compareTo(meta.getGoalValue()) >= 0) {
            meta.setStatus(MetaStatus.CONCLUIDA);
        }
        
        return updateMeta(meta);
    }
}
