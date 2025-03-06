package med.voll.api.domain.consultas.validacao.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsultas {

    @Autowired
    private PacienteRepository repositorio;

    public void validar(DadosAgendamentoConsulta dados){
        if(!repositorio.findAtivoById(dados.idPaciente())){
            throw new ValidacaoException("Paciente inativo.");
        }

    }
}
