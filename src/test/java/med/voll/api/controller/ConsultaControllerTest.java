package med.voll.api.controller;

import med.voll.api.domain.consultas.AgendaDeConsultas;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.consultas.DadosDetalhamentoConsulta;
import med.voll.api.domain.medicos.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc simuladorMvc;
    //simulador das requisicoes http

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJacksonTester;
    //criador do json do objeto dadosAgendamento

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJacksonTester;
    //criador do json do objeto dadosDetalhamento


    @MockBean
    private AgendaDeConsultas agendaDeConsultas;
    //criar o mock desse objeto - evitando as reais dependencias que estao nele como a utilizacao do DB

    @Test
    @WithMockUser
    @DisplayName("Devolver codigo http 400 quando informacoes estao invalidas/nao preenchidas")
    void testarAgendarControllerCenarioUm() throws Exception {
        var resposta = simuladorMvc.perform(post("/consultas")).andReturn().getResponse();
        assertThat(resposta.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @WithMockUser
    @DisplayName("Devolver codigo http 200 quando as informaçoes estao validas")
    void testarAgendaControllerCenarioDois() throws Exception {

        LocalDateTime data = LocalDateTime.now().plusHours(1);
        Especialidade especialidade = Especialidade.CARDIOLOGIA;

        DadosDetalhamentoConsulta dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);
        when(agendaDeConsultas.agendar(any())) //quando o metodo agendar for testado, independente do parametro...
                .thenReturn(dadosDetalhamento); //retorne esse DTO

        var resposta = simuladorMvc.perform(
                post("/consultas")
                    .contentType(MediaType.APPLICATION_JSON) //define o tipo do formato para JSON
                    .content(dadosAgendamentoConsultaJacksonTester.write(new DadosAgendamentoConsulta(2l, 5l, data, especialidade))
                        .getJson()) //cria o json com o JacksonTester para ir no corpo da requisição simulada
        ).andReturn().getResponse(); //retorna a resposta da requisicao para essa variavel

        assertThat(resposta.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJacksonTester.write(new DadosDetalhamentoConsulta(null, 2l, 5l, data))
                .getJson();
        //json esperado para vir na resposta

        assertThat(resposta.getContentAsString()).isEqualTo(jsonEsperado);


    }
}