package med.voll.api.domain.consultas;


import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.validacao.agendamento.ValidadorAgendamentoConsultas;
import med.voll.api.domain.consultas.validacao.cancelamento.ValidadoresCancelamentoConsultas;
import med.voll.api.domain.medicos.Medico;
import med.voll.api.domain.medicos.MedicoRepository;
import med.voll.api.domain.pacientes.Paciente;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository repositorioConsulta;

    @Autowired
    private MedicoRepository repositorioMedico;

    @Autowired
    private PacienteRepository repositorioPaciente;

    @Autowired
    private List<ValidadorAgendamentoConsultas> validadoresAgendamento;

    @Autowired
    private List<ValidadoresCancelamentoConsultas> validadoresCancelamento;


    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        if(!repositorioPaciente.existsById(dados.idPaciente())){
            //verifica se o id do paciente está presente no DB
            throw new ValidacaoException("Id do paciente não existe");
        }

        if(dados.idMedico() != null && !repositorioMedico.existsById(dados.idMedico())){
            //se o campo de medico for preenchido pelo usuario, verifica se está presente no DB
            throw new ValidacaoException("Id do médico não existe");
        }

        //cada validador será executado com os dados recebidos
        validadoresAgendamento.forEach(v -> v.validar(dados));



        Medico medico = escolherMedico(dados);
        if(medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }

        Paciente paciente = repositorioPaciente.findById(dados.idPaciente()).get();
        //recupera paciente no DB

        Consulta consulta = new Consulta(null, medico, paciente, dados.data(), null);

        repositorioConsulta.save(consulta);
        //salva Consulta no DB

        return new DadosDetalhamentoConsulta(consulta);
    }

    public void cancelar(DadosCancelamentoConsulta dados){
        if(!repositorioConsulta.existsById(dados.id())){
            throw new ValidacaoException("ID da consulta não existe");
        }
        validadoresCancelamento.forEach(v -> v.validar(dados));

        Consulta consultaCancelar = repositorioConsulta.getReferenceById(dados.id());
        consultaCancelar.cancelar(dados.motivoCancelamento());
        //Consulta cancelada

    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico()!= null){
            return repositorioMedico.getReferenceById(dados.idMedico());
        }
        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade do médico não informada");
        }
        return repositorioMedico.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }
}
