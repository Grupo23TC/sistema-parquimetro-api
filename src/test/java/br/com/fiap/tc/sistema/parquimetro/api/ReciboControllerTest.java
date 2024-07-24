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
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReciboController.class)
public class ReciboControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReciboService reciboService;

    private static String reciboId;

    @Test
    @Order(1)
    public void iniciarLocacaoTest() throws Exception {
        LocacaoRequest locacaoRequest = new LocacaoRequest("123", "ABC-1234", new EnderecoDTO("Rua A", "123", "São Paulo", "SP", "12345-123"), new Periodo(TipoPeriodoEnum.FIXO, 1));
        ReciboDTO reciboDTO = new ReciboDTO("255", StatusReciboEnum.ABERTO, null, "1 hora", 10.0, FormaPagamentoEnum.CARTAO_CREDITO, 10.0);

        ObjectMapper objectMapper = new ObjectMapper();
        String locacaoRequestJson = objectMapper.writeValueAsString(locacaoRequest);

        given(reciboService.iniciarLocacao(any(LocacaoRequest.class))).willReturn(reciboDTO);

        mockMvc.perform(post("/recibos/iniciar-locacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locacaoRequestJson))
                .andExpect(status().isCreated());

        reciboId = reciboDTO.id(); // Armazenar o ID do recibo para uso no próximo teste
    }
    @Test
    public void finalizarLocacaoTest() throws Exception {
        mockMvc.perform(put("/recibos/finalizar-locacao/" + reciboId) // Use a variável reciboId definida anteriormente
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verificar se o método finalizarLocacao do reciboService foi chamado com o ID correto
        verify(reciboService).finalizarLocacao(reciboId);
        System.out.println("Recibo finalizado com sucesso!" + reciboId);
    }
}