package br.com.floresdev.contador_comite_back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.user.User;
import br.com.floresdev.contador_comite_back.domain.user.dto.UserDTO;
import br.com.floresdev.contador_comite_back.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Dados do usuário atual", description = "Obtém informações do usuário com base no token recebido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna o usuário com base na sessão atual", content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "", description = "Usuário não encontrado na sessão atual", content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        User user = service.getUserByToken(request);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }

}
