package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medicos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/medicos")
public class MedicosController {

    @Autowired
    MedicoRepository repositorio;


    @PostMapping
    @Transactional
    public void cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dadosMedico){
        repositorio.save(new Medico(dadosMedico));
    }

    @GetMapping
    public Page<DadosMedicoListagem> listarMedicos(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao){
        return repositorio.findAll(paginacao)
                .map(DadosMedicoListagem::new);
    }

    /*
    public List<DadosMedicoListagem> listarMedicos(){
        return repositorio.findAll().stream().map(DadosMedicoListagem::new).toList();
        }
        Metodo sem paginação

     */

    @PutMapping
    public void atualizarDados(@RequestBody @Valid DadosAtualizaMedico dadosAtualizar){
        var medico = repositorio.getReferenceById(dadosAtualizar.id());
        medico.atualizarDados(dadosAtualizar);
        //nao está mudando no DB
    }


}
