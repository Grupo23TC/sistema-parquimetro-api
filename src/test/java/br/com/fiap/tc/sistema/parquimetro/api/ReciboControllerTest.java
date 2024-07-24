package br.com.fiap.tc.sistema.parquimetro.api;

import br.com.fiap.tc.sistema.parquimetro.api.controller.ReciboController;
import br.com.fiap.tc.sistema.parquimetro.api.model.Periodo;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.EnderecoDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoRequest;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.ReciboDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.StatusReciboEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.TipoPeriodoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.service.ReciboService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static br.com.fiap.tc.sistema.parquimetro.api.model.enums.StatusPagamentoEnum.CONFIRMADO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReciboController.class)
public class ReciboControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReciboService reciboService;

    @Test
    public void iniciarLocacaoTest() throws Exception {
        // Configuração
        LocacaoRequest locacaoRequest = new LocacaoRequest("123",
                "ABC-1234",
                new EnderecoDTO("Rua A", "123", "São Paulo", "SP"),
                new Periodo(TipoPeriodoEnum.FIXO, 1));
        ReciboDTO reciboDTO = new ReciboDTO("123",StatusReciboEnum.ABERTO, null, "1 hora", 10.0, FormaPagamentoEnum.CARTAO_CREDITO, 10.0);


        ObjectMapper objectMapper = new ObjectMapper();
        String locacaoRequestJson = objectMapper.writeValueAsString(locacaoRequest);
        String reciboDTOJson = objectMapper.writeValueAsString(reciboDTO);

        given(reciboService.iniciarLocacao(any(LocacaoRequest.class))).willReturn(reciboDTO);

        // Ação e Verificação
        mockMvc.perform(post("/recibos/iniciar-locacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locacaoRequestJson))
                .andExpect(status().isCreated()) // Alterado para verificar o status 201
                .andExpect(content().json(reciboDTOJson));
    }
}