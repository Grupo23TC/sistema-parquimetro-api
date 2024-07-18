package br.com.fiap.tc.sistema.parquimetro.api.controller;

import br.com.fiap.tc.sistema.parquimetro.api.model.dto.CondutorDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/condutores")
public class CondutorController {
  @Autowired
  private CondutorService condutorService;

  @PostMapping
  public ResponseEntity<CondutorDTO> cadastrar(@RequestBody CondutorDTO condutorDto) {
    CondutorDTO condutorSalvo = condutorService.criarCondutor(condutorDto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(condutorSalvo.id())
            .toUri();
    return ResponseEntity.created(uri).body(condutorSalvo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> atualizarFormaPagamento(@PathVariable String id,
                                      @RequestBody FormaPagamentoEnum formaPagamento) {

    this.condutorService.atualizarFormaPagamento(id, formaPagamento);

    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<CondutorDTO>> listar() {
    return ResponseEntity.ok(condutorService.listaCondutor());
  }

}