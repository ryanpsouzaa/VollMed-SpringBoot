package med.voll.api.domain.consultas.validacao.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaMesmoHorario implements ValidadorAgendamentoConsultas {

    @Autowired
    private ConsultaRepository repositorio;

    public void validar(DadosAgendamentoConsulta dados){
        Boolean consultaMesmoHorario = repositorio.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if(consultaMesmoHorario){
            throw new ValidacaoException("Já existe uma consulta agendada para o médico nesse mesmo horário");
        }

    }
}
