package br.com.rosin.testecmp.testecmp.repository;

import br.com.rosin.testecmp.testecmp.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClienteRepository extends MongoRepository<Cliente, String> {

    List<Cliente> findByNomeLikeIgnoreCase(String nome);
}
