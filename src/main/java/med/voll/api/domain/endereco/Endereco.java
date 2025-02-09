package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {
    public String logradouro;
    public String bairro;
    public String cep;
    public String cidade;
    public String uf;
    public String numero;
    public String complemento;

    public Endereco(DadosEndereco endereco) {
        this.logradouro = endereco.logradouro();
        this.bairro = endereco.bairro();
        this.cep = endereco.cep();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();
        this.numero = endereco.numero();
        this.complemento = endereco.complemento();
    }

    public Endereco(){

    }

    public void atualizarDados(DadosEndereco endereco) {
        if(endereco.logradouro() != null){
            this.logradouro = endereco.logradouro();
        }
        if(endereco.bairro() != null){
            this.bairro = endereco.bairro();
        }
        if(endereco.cep() != null){
            this.cep = endereco.cep();
        }
        if(endereco.cidade() != null){
            this.cidade = endereco.cidade();
        }
        if(endereco.uf() != null){
            this.uf = endereco.uf();
        }
        if(endereco.numero() != null){
            this.numero = endereco.numero();
        }
        if(endereco.complemento() != null){
            this.complemento = endereco.complemento();
        }
    }
}
