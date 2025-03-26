package br.com.floresdev.contador_comite_back.infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.floresdev.contador_comite_back.domain.user.User;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            // Vamos usar o HMAC-256 para geração do token
            Algorithm alg = Algorithm.HMAC256(secret); // O algoritmo recebe uma chave secreta da aplicação para gerar os hashes
            
            return JWT.create()
                .withIssuer("contador-receita-comite-back")
                .withSubject(user.getEmail())
                .withExpiresAt(generateExpirationDate())
                .sign(alg);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao criar token JWT", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm alg = Algorithm.HMAC256(secret);
            return JWT.require(alg)
                .withIssuer("contador-receita-comite-back")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant generateExpirationDate() {
        // Usando o fuso de Brasília
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }

}
