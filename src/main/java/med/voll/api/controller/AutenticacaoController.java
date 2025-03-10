package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DadosAutenticacao;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@Valid @RequestBody DadosAutenticacao dados){
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var autenticacao = manager.authenticate(authenticationToken);
            String token = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
            return ResponseEntity.ok(new DadosTokenJWT(token));
        } catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
