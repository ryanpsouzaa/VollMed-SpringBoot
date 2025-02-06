package med.voll.api.medicos;

public record DadosMedicoListagem(Long id, String nome, String crm, String email, Especialidade especialidade) {

    public DadosMedicoListagem(Medico dadosMedico){
        this(dadosMedico.getId(), dadosMedico.getNome(), dadosMedico.getCrm(),
                dadosMedico.getEmail(), dadosMedico.getEspecialidade());
    }
}
