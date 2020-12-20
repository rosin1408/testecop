package br.com.rosin.testecmp.testecmp.repository;

import br.com.rosin.testecmp.testecmp.model.Cidade;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CidadeRepository extends MongoRepository<Cidade, String> {

    List<Cidade> findByNomeLikeIgnoreCase(String nome);

    List<Cidade> findByUfIgnoreCase(String uf);
}
