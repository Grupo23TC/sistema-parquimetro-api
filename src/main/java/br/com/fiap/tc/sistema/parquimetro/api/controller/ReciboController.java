package br.com.fiap.tc.sistema.parquimetro.api.controller;

import br.com.fiap.tc.sistema.parquimetro.api.model.Recibo;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoRequest;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.ReciboDTO;
import br.com.fiap.tc.sistema.parquimetro.api.service.ReciboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/recibos")
public class ReciboController {

    @Autowired
    private ReciboService reciboService;

    @PostMapping("/iniciar-locacao")
    public ResponseEntity<ReciboDTO> iniciarLocacao(@RequestBody LocacaoRequest locacaoRequest) {
        ReciboDTO reciboSalvo = reciboService.iniciarLocacao(locacaoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reciboSalvo.id())
                .toUri();
        return ResponseEntity.created(uri).body(reciboSalvo);
    }

}
