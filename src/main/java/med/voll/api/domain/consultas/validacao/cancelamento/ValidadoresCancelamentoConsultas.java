package med.voll.api.domain.consultas.validacao.cancelamento;

import med.voll.api.domain.consultas.DadosCancelamentoConsulta;

public interface ValidadoresCancelamentoConsultas {

    void validar(DadosCancelamentoConsulta dados);
}
