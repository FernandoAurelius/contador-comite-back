package br.com.floresdev.contador_comite_back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.floresdev.contador_comite_back.domain.repositories.UserRepository;
import br.com.floresdev.contador_comite_back.domain.user.User;
import br.com.floresdev.contador_comite_back.infra.SecurityFilter;
import br.com.floresdev.contador_comite_back.infra.TokenService;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService service;

    public User getUserByEmail(String email) {
        var userOpt = repository.findByEmail(email);

        if (userOpt.isPresent()) return (User) userOpt.get();

        return null;
    }

    public User getUserByToken(HttpServletRequest request) {
        var token = SecurityFilter.recoverTokenFromCookie(request);
        var email = service.validateToken(token);
        return this.getUserByEmail(email);
    }

}
