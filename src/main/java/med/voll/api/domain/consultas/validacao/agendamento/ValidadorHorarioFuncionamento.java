package med.voll.api.domain.consultas.validacao.agendamento;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoConsultas {

    public void validar(DadosAgendamentoConsulta dados){

        /*
        O horário de funcionamento da clínica é de segunda a sábado,
         das 07:00 às 19:00;
         */

        boolean ehDomingo = dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean ehHorarioPosterior = dados.data().getHour() > 18;
        boolean ehHorarioAntecedente = dados.data().getHour() < 7;

        if(ehDomingo || ehHorarioAntecedente || ehHorarioPosterior){
            throw new ValidationException("Consulta fora do horário de atendimento da clínica");
        }
    }
}
