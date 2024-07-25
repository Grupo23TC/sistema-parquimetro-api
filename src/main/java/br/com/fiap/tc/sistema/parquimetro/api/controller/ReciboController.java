package br.com.fiap.tc.sistema.parquimetro.api.controller;

import br.com.fiap.tc.sistema.parquimetro.api.exception.ErroCustomizado;
import br.com.fiap.tc.sistema.parquimetro.api.model.Recibo;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.CondutorDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoRequest;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.ReciboDTO;
import br.com.fiap.tc.sistema.parquimetro.api.service.ReciboService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/recibos")
@Tag(name = "Recibo Controller", description = "Controlador responsável por gerar e atualizar recibos do sistema de parquímetro")
public class ReciboController {

    @Autowired
    private ReciboService reciboService;

    @PostMapping("/iniciar-locacao")
    @Operation(summary = "Inicia a locação do condutor.", description = "Cria a locação do condutor se os dados apresentados no corpo da requisição estiverem corretos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Locação iniciada com sucesso.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReciboDTO.class))}),
        @ApiResponse(responseCode = "404", description = "Veículo não está cadastrado na lista de veículos do condutor.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErroCustomizado.class))}),
        @ApiResponse(responseCode = "400", description = "Erro na validação dos campos: duração ou forma de pagamento.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErroCustomizado.class))})
    })
    public ResponseEntity<ReciboDTO> iniciarLocacao(@RequestBody LocacaoRequest locacaoRequest) {
        ReciboDTO reciboSalvo = reciboService.iniciarLocacao(locacaoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reciboSalvo.id())
                .toUri();
        return ResponseEntity.created(uri).body(reciboSalvo);
    }

    @PutMapping("/finalizar-locacao/{id}")
    @Operation(summary = "Finaliza o Recibo do condutor.", description = "Atualiza o status do Recibo para FINALIZADO caso a operação seja bem sucedida.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recibo finalizado com sucesso.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReciboDTO.class))}),
    })
    public ResponseEntity<ReciboDTO> finalizarLocacao(@PathVariable String id) {
        return ResponseEntity.ok(reciboService.finalizarLocacao(id));
    }

}
