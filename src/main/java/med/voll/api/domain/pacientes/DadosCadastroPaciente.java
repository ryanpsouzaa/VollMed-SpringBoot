package med.voll.api.domain.pacientes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(

        @NotBlank
        String nome,

        @Email @NotBlank
        String email,

        @NotBlank
        String telefone,

        @NotBlank @Pattern(regexp = "\\d{6,11}")
        String cpf,

        @Valid
        DadosEndereco endereco
) {
}
