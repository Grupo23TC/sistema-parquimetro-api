package br.com.fiap.tc.sistema.parquimetro.api.repository;

import br.com.fiap.tc.sistema.parquimetro.api.model.Recibo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReciboRepository extends MongoRepository<Recibo, String> {

}
