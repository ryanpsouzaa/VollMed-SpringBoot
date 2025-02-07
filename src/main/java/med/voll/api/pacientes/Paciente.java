package med.voll.api.pacientes;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.endereco.Endereco;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String cpf;

    @Embedded
    private Endereco endereco;

    private boolean ativo;

    public Paciente(DadosCadastroPaciente dadosCadastro){
        this.nome = dadosCadastro.nome();
        this.email = dadosCadastro.email();
        this.telefone = dadosCadastro.telefone();
        this.cpf = dadosCadastro.cpf();
        this.endereco = new Endereco(dadosCadastro.endereco());

        this.ativo = true;

    }

    public void excluirDados() {
        this.ativo = false;
    }

    public void atualizaDados(DadosAtualizaPaciente dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if(dados.endereco() != null){
            this.endereco.atualizarDados(dados.endereco());
        }
    }
}
