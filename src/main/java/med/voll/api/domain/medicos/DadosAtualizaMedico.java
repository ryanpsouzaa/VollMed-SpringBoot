package med.voll.api.domain.medicos;

import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizaMedico(Long id, String nome, String crm, String telefone, DadosEndereco endereco) {
}
