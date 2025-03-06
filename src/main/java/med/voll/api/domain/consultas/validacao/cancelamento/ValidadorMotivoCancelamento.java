package med.voll.api.domain.consultas.validacao.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.DadosCancelamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMotivoCancelamento implements ValidadoresCancelamentoConsultas{

    public void validar(DadosCancelamentoConsulta dados){
        if(dados.motivoCancelamento() == null){
            throw new ValidacaoException("É obrigatório o preechimento do campo: Motivo do Cancelamento");
        }
    }

}
