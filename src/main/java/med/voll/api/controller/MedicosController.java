package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medicos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/medicos")
public class MedicosController {

    @Autowired
    MedicoRepository repositorio;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dadosMedico, UriComponentsBuilder uriBuilder){
        var medico = new Medico(dadosMedico);
        repositorio.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedicos(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosMedicoListagem>> listarMedicos(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao){
        var page = repositorio.findAllByAtivoTrue(paginacao)
                .map(DadosMedicoListagem::new);
        return ResponseEntity.ok(page);
        /*
        return repositorio.findAll(paginacao).map(DadosMedicoListagem::new);

         */

    }

    /*
    public List<DadosMedicoListagem> listarMedicos(){
        return repositorio.findAll().stream().map(DadosMedicoListagem::new).toList();
        }
        Metodo sem paginação

     */

    @PutMapping
    @Transactional
    public ResponseEntity atualizarDados(@RequestBody @Valid DadosAtualizaMedico dadosAtualizar){
        var medico = repositorio.getReferenceById(dadosAtualizar.id());
        medico.atualizarDados(dadosAtualizar);
        return ResponseEntity.ok(new DadosDetalhamentoMedicos(medico));
    }

    /*
    @DeleteMapping("/{id}")
    public void deletarDados(@PathVariable Long id){
        repositorio.deleteById(id);
    }

     */
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity excluirDados(@PathVariable Long id){
        var medico = repositorio.getReferenceById(id);
        medico.excluirDados();
        return ResponseEntity.noContent().build();

    }

    @GetMapping("{id}")
    public ResponseEntity detalharMedico(@PathVariable Long id){
        var medico = repositorio.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedicos(medico));

    }

}
