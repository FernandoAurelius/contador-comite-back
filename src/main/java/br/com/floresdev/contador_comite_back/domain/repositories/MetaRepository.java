package br.com.floresdev.contador_comite_back.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.floresdev.contador_comite_back.domain.meta.Meta;

public interface MetaRepository extends JpaRepository<Meta, Long>{

}
