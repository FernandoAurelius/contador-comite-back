package br.com.floresdev.contador_comite_back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.floresdev.contador_comite_back.domain.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

}
