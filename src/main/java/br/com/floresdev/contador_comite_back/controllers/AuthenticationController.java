package br.com.floresdev.contador_comite_back.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.floresdev.contador_comite_back.domain.repositories.UserRepository;
import br.com.floresdev.contador_comite_back.domain.user.User;
import br.com.floresdev.contador_comite_back.domain.user.UserRole;
import br.com.floresdev.contador_comite_back.domain.user.dto.AuthenticationDTO;
import br.com.floresdev.contador_comite_back.domain.user.dto.UserDTO;
import br.com.floresdev.contador_comite_back.domain.user.dto.RegisterDTO;
import br.com.floresdev.contador_comite_back.infra.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "API para autenticação e registro de usuários")
public class AuthenticationController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

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
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "403", description = "Credenciais inválidas", 
                    content = @Content)
    })
    public ResponseEntity<UserDTO> login(
        @RequestBody @Valid AuthenticationDTO dto, 
        HttpServletRequest request,
        HttpServletResponse response) {
        String ipAddress = request.getRemoteAddr();
        logger.info("Tentativa de login para o email: {} de IP: {}", dto.email(), ipAddress);
        
        try {
            var userPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            var auth = authManager.authenticate(userPassword);
            
            var user = (User) auth.getPrincipal();
            var token = service.generateToken(user);

            // Pra facilitar pro front-end e deixar a aplicação mais robusta: vamos usar um cookie HttpOnly
            Cookie jwtCookie = new Cookie("auth_token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(false); // Temporariamente como falso, será true para apenas permitir via HTTPS
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(3 * 60 * 60); // 3h, igual ao token

            response.addCookie(jwtCookie);
            
            logger.info("Login bem-sucedido para o usuário: {}, ID: {}, IP: {}", 
                    user.getUsername(), user.getId(), ipAddress);
            
            return ResponseEntity.ok(new UserDTO(user.getName(), user.getEmail(), user.getRole().toString()));
        } catch (Exception e) {
            logger.warn("Falha na autenticação para o email: {} de IP: {} - Motivo: {}", 
                    dto.email(), ipAddress, e.getMessage());
            throw e;
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout de usuário", description = "Anula o token JWT do usuário, 'deslogando-o' efetivamente")
    @ApiResponse(responseCode = "200", description = "Usuário deslogado com sucesso.")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("auth_token", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);

        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    @Operation(summary = "Registro de usuário", description = "Registra um novo usuário no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso", 
                    content = @Content),
        @ApiResponse(responseCode = "400", description = "E-mail já em uso ou papel de usuário inválido", 
                    content = @Content)
    })
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO dto, HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("Tentativa de registro para o email: {} com papel: {} de IP: {}", 
                dto.email(), dto.role(), ipAddress);
        
        if (repository.findByEmail(dto.email()).isPresent()) {
            logger.warn("Tentativa de registro falhou: email já em uso: {} de IP: {}", dto.email(), ipAddress);
            return ResponseEntity.badRequest().build();
        }

        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
            UserRole userRole = UserRole.fromString(dto.role());
            User newUser = new User(dto.name(), dto.email(), encryptedPassword, userRole);
            repository.save(newUser);
            
            logger.info("Usuário registrado com sucesso: {}, papel: {}, IP: {}", 
                    dto.email(), userRole, ipAddress);
            
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao registrar usuário com email: {} - Papel inválido: {} de IP: {} - Mensagem: {}", 
                    dto.email(), dto.role(), ipAddress, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Erro inesperado ao registrar usuário com email: {} de IP: {} - Mensagem: {}", 
                    dto.email(), ipAddress, e.getMessage(), e);
            throw e;
        }
    }
}
