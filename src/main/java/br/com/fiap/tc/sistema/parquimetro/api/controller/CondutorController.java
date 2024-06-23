package br.com.fiap.tc.sistema.parquimetro.api.controller;

import br.com.fiap.tc.sistema.parquimetro.api.dto.CondutorDto;
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
  public ResponseEntity<CondutorDto> cadastrar(@RequestBody CondutorDto condutorDto) {
    CondutorDto condutorSalvo = condutorService.criarCondutor(condutorDto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(condutorSalvo.id())
        .toUri();
    return ResponseEntity.created(uri).body(condutorSalvo);
  }

  @GetMapping
  public ResponseEntity<List<CondutorDto>> listar() {
    return ResponseEntity.ok(condutorService.listaCondutor());
  }

}
