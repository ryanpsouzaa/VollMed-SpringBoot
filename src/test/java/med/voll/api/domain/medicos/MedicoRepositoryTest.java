package med.voll.api.domain.medicos;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import med.voll.api.domain.consultas.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.pacientes.DadosCadastroPaciente;
import med.voll.api.domain.pacientes.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MedicoRepository repositorioMedico;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado nao esta com a especialidade diferente")
    void escolherMedicoAleatorioLivreNaDataCenarioUm() {
        cadastrarMedico("Tulio", "tulio.alves@voll.med", "123123", Especialidade.ORTOPEDIA);
        cadastrarPaciente("Robson", "robson.robs@gmail.com", "123123");


        LocalDateTime proximaSegundaAsDez = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .toLocalDate().atTime(10, 0);

        var medicoLivre = repositorioMedico.escolherMedicoAleatorioLivreNaData(
                Especialidade.CARDIOLOGIA, proximaSegundaAsDez
        ); // é para ser nulo
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Unico medico cadastrado é passado normalmente")
    void escolherMedicoAleatorioLivreNaDataCenarioDois(){
        var medico = cadastrarMedico("Tulio", "tulio.alves@voll.med", "123123", Especialidade.ORTOPEDIA);
        var paciente = cadastrarPaciente("Robson", "robson.robs@gmail.com", "123123");

        var proximaSegundaAsDez = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .toLocalDate().atTime(10, 0);

        var medicoEscolhido = repositorioMedico.escolherMedicoAleatorioLivreNaData(Especialidade.ORTOPEDIA, proximaSegundaAsDez);
        assertThat(medicoEscolhido).isEqualTo(medico);
    }

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado esta com outra consulta ja cadastrada na data")
    void esccolherMedicoAleatorioLivreNaDataCenarioTres(){
        Medico medico = cadastrarMedico("Tulio", "tulio.alves@voll.med", "123123", Especialidade.ORTOPEDIA);
        Paciente pacienteUm = cadastrarPaciente("Robson", "robson.robs@gmail.com", "123123");
        Paciente pacienteDois = cadastrarPaciente("Renato", "renato@gmail.com", "2222222");

        var proximaSegundaAsDez = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .toLocalDate().atTime(10, 0);

        cadastrarConsulta(medico, pacienteUm, proximaSegundaAsDez); //cadastrando consulta com o medico na mesma data
        Medico medicoEscolhido = repositorioMedico.escolherMedicoAleatorioLivreNaData(Especialidade.ORTOPEDIA, proximaSegundaAsDez);
        assertThat(medicoEscolhido).isNull();
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade){
        var medico = new Medico(criarMedico(nome, email, crm, especialidade));
        entityManager.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf){
        var paciente = new Paciente(criarPaciente(nome, email, cpf));
        entityManager.persist(paciente);
        return paciente;
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data){
        var consulta = new Consulta(medico, paciente, data);
        entityManager.persist(consulta);
    }


    private DadosCadastroMedico criarMedico(String nome, String email, String crm, Especialidade especialidade){
        return new DadosCadastroMedico(
                nome,
                email,
                "9888888",
                crm,
                especialidade,
                criarEndereco()
        );
    }

    private DadosEndereco criarEndereco(){
        return new DadosEndereco(
                "rua generica",
                "bairro",
                "00000000",
                "Cidade generica",
                "GN",
                null,
                null
        );
    }

    private DadosCadastroPaciente criarPaciente(String nome, String email, String cpf){
        return new DadosCadastroPaciente(nome,
                email,
                "98765432",
                cpf,
                criarEndereco());
    }
}