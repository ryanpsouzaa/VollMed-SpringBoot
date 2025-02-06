package med.voll.api.medicos;

import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizaMedico(Long id, String nome, String crm, String telefone, DadosEndereco endereco) {
}
