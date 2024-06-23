package br.com.fiap.tc.sistema.parquimetro.api.repository;

import br.com.fiap.tc.sistema.parquimetro.api.model.Condutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondutorRepository extends MongoRepository<Condutor, String>{

    Condutor findByCpf(String cpf);

}
