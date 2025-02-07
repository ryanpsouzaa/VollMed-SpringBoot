package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {

    @Autowired
    PacienteRepository repositorio;


    @PostMapping
    @Transactional
    public void cadastraPaciente(@RequestBody @Valid DadosCadastroPaciente dadosCadastro){
        repositorio.save(new Paciente(dadosCadastro));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listarPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return repositorio.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizaDadosPaciente(@Valid @RequestBody DadosAtualizaPaciente dadosAtualizar){
        var paciente = repositorio.getReferenceById(dadosAtualizar.id());
        paciente.atualizaDados(dadosAtualizar);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluirDados(@PathVariable Long id){
        Paciente paciente = repositorio.getReferenceById(id);
        paciente.excluirDados();

    }
}
