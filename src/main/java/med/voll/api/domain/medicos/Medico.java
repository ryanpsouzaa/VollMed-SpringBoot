package med.voll.api.domain.medicos;


import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico(DadosCadastroMedico dadosMedico) {
        this.nome = dadosMedico.nome();
        this.email = dadosMedico.email();
        this.telefone = dadosMedico.telefone();
        this.crm = dadosMedico.crm();
        this.especialidade = dadosMedico.especialidade();
        this.endereco = new Endereco(dadosMedico.endereco());
        this.ativo = true;
    }

    public void atualizarDados(DadosAtualizaMedico dados){
        if(dados.nome() != null){
            this.nome = dados.nome();
        }

        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }

        if(dados.crm() != null){
            this.crm = dados.crm();
        }
        if(dados.endereco() != null){
            this.endereco.atualizarDados(dados.endereco());
        }

    }

    public void excluirDados() {
        this.ativo = false;
    }
}
