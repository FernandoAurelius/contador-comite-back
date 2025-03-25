package br.com.floresdev.contador_comite_back.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.floresdev.contador_comite_back.domain.capital.Capital;

public interface CapitalRepository extends JpaRepository<Capital, Long> {

}
