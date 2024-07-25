package br.com.fiap.tc.sistema.parquimetro.api.controller;

import br.com.fiap.tc.sistema.parquimetro.api.exception.ErroCustomizado;
import br.com.fiap.tc.sistema.parquimetro.api.model.Condutor;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.CondutorDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.ErrorDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.service.CondutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/condutores")
@Tag(name = "Condutor Controller", description = "Controlador responsável por criar, atualizar forma de pagamento e listar todos os condutores do sistema de parquímetro")
public class CondutorController {
  @Autowired
  private CondutorService condutorService;

  @PostMapping
  @Operation(
      summary = "Cadastra um condutor.",
      description = "Realiza o cadastro de um condutor com todos os dados solicitados no body da requisição."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Condutor cadastrado com sucesso",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CondutorDTO.class))}),
      @ApiResponse(responseCode = "400", description = "Erro ao cadastrar condutor na base de dados",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroCustomizado.class))),
  })
  public ResponseEntity<CondutorDTO> cadastrar(@RequestBody CondutorDTO condutorDto) {
    CondutorDTO condutorSalvo = condutorService.criarCondutor(condutorDto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(condutorSalvo.id())
            .toUri();
    return ResponseEntity.created(uri).body(condutorSalvo);
  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Atualiza a forma de pagamento",
      description = "O parâmetro Id da URL é obrigatório e necessário para fazer a atualização dos dados de pagamento do condutor, além precisar da nova forma de pagamento no corpo da requisição"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Forma de pagamento atualizada com sucesso.")
  })
  public ResponseEntity<Void> atualizarFormaPagamento(@PathVariable String id,
                                      @RequestBody FormaPagamentoEnum formaPagamento) {

    this.condutorService.atualizarFormaPagamento(id, formaPagamento);

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @Operation(summary = "Lista de condutores", description = "Endpoint retorna uma lista com todos os condutores cadastrados na base de dados")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Lista de condutores",
          content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CondutorDTO.class))),}
      )
  })
  public ResponseEntity<List<CondutorDTO>> listar() {
    return ResponseEntity.ok(condutorService.listaCondutor());
  }

}