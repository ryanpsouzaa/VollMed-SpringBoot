package med.voll.api.domain.consultas.validacao.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoConsultas {

    @Autowired
    private ConsultaRepository repositorio;

    public void validar(DadosAgendamentoConsulta dados){
        LocalDateTime primeiroHorario = dados.data().withHour(7);
        LocalDateTime ultimoHorario = dados.data().withHour(18);

        Boolean pacientePossuiOutraConsultaNoDia =
                repositorio.existsByPacienteIdAndDataBetween(
                        dados.idPaciente(), primeiroHorario, ultimoHorario
                );
        if(pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
        }

    }
}
