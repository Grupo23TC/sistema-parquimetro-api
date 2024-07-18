package br.com.fiap.tc.sistema.parquimetro.api.controller;

import br.com.fiap.tc.sistema.parquimetro.api.model.Recibo;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.ReciboDTO;
import br.com.fiap.tc.sistema.parquimetro.api.service.ReciboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/recibos")
public class ReciboController {

    @Autowired
    private ReciboService reciboService;

    @PostMapping("/iniciar-locacao")
    public ResponseEntity<Recibo> iniciarLocacao(@RequestBody LocacaoDTO locacaoDTO) {
        Recibo reciboSalvo = reciboService.iniciarLocacao(locacaoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reciboSalvo.getId())
                .toUri();
        return ResponseEntity.created(uri).body(reciboSalvo);
    }

    @PutMapping("/finalizar-locacao/{id}")
    public ResponseEntity<ReciboDTO> finalizarLocacao(@PathVariable String id) {
        reciboService.finalizarLocacao(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
