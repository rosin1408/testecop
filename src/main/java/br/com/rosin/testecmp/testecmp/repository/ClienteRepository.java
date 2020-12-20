package br.com.rosin.testecmp.testecmp.repository;

import br.com.rosin.testecmp.testecmp.model.Cliente;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepository extends MongoRepository<Cliente, String> {

    List<Cliente> findByNomeLikeIgnoreCase(String nome);
}
