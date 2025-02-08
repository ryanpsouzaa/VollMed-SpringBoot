package med.voll.api.pacientes;

import med.voll.api.endereco.Endereco;

public record DadosDetalhamentoPacientes(Long id, String nome, String email, String cpf, Endereco endereco) {

    public DadosDetalhamentoPacientes(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getEndereco());
    }
}
