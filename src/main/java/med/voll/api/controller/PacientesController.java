package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.pacientes.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {

    @Autowired
    PacienteRepository repositorio;


    @PostMapping
    @Transactional
    public ResponseEntity cadastraPaciente(@RequestBody @Valid DadosCadastroPaciente dadosCadastro, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(dadosCadastro);
        repositorio.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPacientes(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listarPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page =  repositorio.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizaDadosPaciente(@Valid @RequestBody DadosAtualizaPaciente dadosAtualizar){
        var paciente = repositorio.getReferenceById(dadosAtualizar.id());
        paciente.atualizaDados(dadosAtualizar);
        return ResponseEntity.ok(new DadosDetalhamentoPacientes(paciente));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirDados(@PathVariable Long id){
        Paciente paciente = repositorio.getReferenceById(id);
        paciente.excluirDados();
        return ResponseEntity.noContent().build();

    }
}
