package med.voll.api.medicos;

public record DadosCadastroMedico(String nome, String email,
                                  String crm, Especialidade especialidade, DadosEndereco endereco) {
}
