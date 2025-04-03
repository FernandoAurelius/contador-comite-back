package br.com.floresdev.contador_comite_back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.user.User;
import br.com.floresdev.contador_comite_back.domain.user.dto.UserDTO;
import br.com.floresdev.contador_comite_back.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        User user = service.getUserByToken(request);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }

}
