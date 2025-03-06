package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consultas.AgendaDeConsultas;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.consultas.DadosCancelamentoConsulta;
import med.voll.api.domain.consultas.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agendaDeConsultas;

    @Transactional
    @PostMapping
    public ResponseEntity agendar(@Valid @RequestBody DadosAgendamentoConsulta dados){
        var dadosDetalhamento = agendaDeConsultas.agendar(dados);
        return ResponseEntity.ok(dadosDetalhamento);

    }
    @DeleteMapping
    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody DadosCancelamentoConsulta dados){
        agendaDeConsultas.cancelar(dados);

        return ResponseEntity.ok().body("Consulta cancelada");
    }
}
