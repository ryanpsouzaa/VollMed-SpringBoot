package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = recuperarToken(request);
        System.out.println(tokenJWT);
        
        filterChain.doFilter(request, response);

    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null){
            throw new RuntimeException("Token JWT não foi enviado para o cabecalho Authorization.");
        } else{

            return token.replace("Bearer ", "");
        }
    }
}
