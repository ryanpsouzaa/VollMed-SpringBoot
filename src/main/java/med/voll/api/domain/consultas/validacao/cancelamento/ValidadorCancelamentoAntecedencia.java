package med.voll.api.domain.consultas.validacao.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.Consulta;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorCancelamentoAntecedencia implements ValidadoresCancelamentoConsultas {

    @Autowired
    private ConsultaRepository repositorio;

    public void validar(DadosCancelamentoConsulta dados){
        Consulta consulta = repositorio.getReferenceById(dados.id());

        LocalDateTime dataCancelamentoAgora = LocalDateTime.now();
        LocalDateTime dataConsulta = consulta.getData();

        Long diferenca = Duration.between(dataCancelamentoAgora, dataConsulta).toHours();
        if(diferenca < 24){
            throw new ValidacaoException("O cancelamento da consulta deve ser realizado com uma antecedência de 24 horas");
        }

    }
}
