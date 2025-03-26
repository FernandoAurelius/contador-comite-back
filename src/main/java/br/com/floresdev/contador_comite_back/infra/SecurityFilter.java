package br.com.floresdev.contador_comite_back.infra;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import br.com.floresdev.contador_comite_back.domain.repositories.UserRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService service;

    @Autowired
    UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            var token = this.recoverToken(request);
            if (token != null) {
                var email = service.validateToken(token);
                repository.findByEmail(email).ifPresent(user -> {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }); 
            }

            filterChain.doFilter(request, response); // Se o token é nulo, nós só repassamos o filtro para a próxima etapa (que é a autenticação)
        } catch (IOException e) {
            System.out.println("Erro de IO ao tentar validar o token: " + e.getMessage());
        } catch (ServletException e) {
            System.out.println("Erro de Servlet ao tentar validar o token: " + e.getMessage());
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;

        /* O Bearer ("Portador" em Português) é um tipo de identificador do token sendo passado na requisição.authenticationController
        * Precisamos removê-lo antes de efetivamente retornar o token
        */
        return authHeader.replace("Bearer ", "");
    }
}
