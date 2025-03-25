package br.com.floresdev.contador_comite_back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.floresdev.contador_comite_back.domain.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
    
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return repository.findByEmail(email);
    }

}
