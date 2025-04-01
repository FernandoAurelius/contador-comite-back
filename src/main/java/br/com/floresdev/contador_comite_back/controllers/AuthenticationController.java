package br.com.floresdev.contador_comite_back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.repositories.UserRepository;
import br.com.floresdev.contador_comite_back.domain.user.User;
import br.com.floresdev.contador_comite_back.domain.user.UserRole;
import br.com.floresdev.contador_comite_back.domain.user.dto.AuthenticationDTO;
import br.com.floresdev.contador_comite_back.domain.user.dto.LoginResponseDTO;
import br.com.floresdev.contador_comite_back.domain.user.dto.RegisterDTO;
import br.com.floresdev.contador_comite_back.infra.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticação", description = "API para autenticação e registro de usuários")
public class AuthenticationController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService service;

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida", 
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "Credenciais inválidas", 
                    content = @Content)
    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto) {
        var userPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authManager.authenticate(userPassword);

        var token = service.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Registro de usuário", description = "Registra um novo usuário no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso", 
                    content = @Content),
        @ApiResponse(responseCode = "400", description = "E-mail já em uso ou papel de usuário inválido", 
                    content = @Content)
    })
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO dto) {
        if (repository.findByEmail(dto.email()).isPresent()) return ResponseEntity.badRequest().build();

        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
            repository.save(new User(dto.name(), dto.email(), encryptedPassword, UserRole.fromString(dto.role())));
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
