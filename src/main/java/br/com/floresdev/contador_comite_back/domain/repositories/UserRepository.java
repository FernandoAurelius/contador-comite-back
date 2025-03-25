package br.com.floresdev.contador_comite_back.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.floresdev.contador_comite_back.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long>{
    UserDetails findByEmail(String email);
}
