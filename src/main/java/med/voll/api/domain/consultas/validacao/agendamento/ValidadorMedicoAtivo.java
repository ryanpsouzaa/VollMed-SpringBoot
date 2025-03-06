package med.voll.api.domain.consultas.validacao.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.medicos.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsultas {

    @Autowired
    private MedicoRepository repositorio;

    public void validar(DadosAgendamentoConsulta dados){

        if(dados.idMedico() == null){ //medico é opcional
            return;
        }

        if(!repositorio.findAtivoById(dados.idMedico())){
            throw new ValidacaoException("Médico está inativo");
        }
    }
}
