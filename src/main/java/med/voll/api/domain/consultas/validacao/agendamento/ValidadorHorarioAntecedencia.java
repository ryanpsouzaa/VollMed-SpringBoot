package med.voll.api.domain.consultas.validacao.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoConsultas {

    public void validar(DadosAgendamentoConsulta dados){
        LocalDateTime dataConsulta = dados.data();
        LocalDateTime dataPresente = LocalDateTime.now();

        var diferenca = Duration.between(dataPresente, dataConsulta).toMinutes();

        if(diferenca < 30){
            throw new ValidacaoException("Consultas só podem ser agendadas com antecedência mínima de 30 minutos");
        }

    }
}
