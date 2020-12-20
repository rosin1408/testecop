package br.com.rosin.testecmp.testecmp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.rosin.testecmp.testecmp.model.Cidade;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class CidadeRepositoryTest {

    @Autowired
    private CidadeRepository repository;

    @BeforeEach
    public void inserirDados() {
        repository.deleteAll();
        repository.save(new Cidade("jkl", "Erechim", "RS"));
        repository.save(new Cidade("abc", "Águas de Chapecó", "SC"));
        repository.save(new Cidade("def", "Chapecó", "SC"));
        repository.save(new Cidade("ghi", "Passo Fundo", "RS"));
    }

    @Test
    public void deveBuscarCidadePorNomeIgnorandoMaiusculasMinusculas() {
        final List<Cidade> cidades = repository.findByNomeLikeIgnoreCase("APE");

        assertThat(cidades).hasSize(2);
        assertThat(cidades).allMatch(cidade -> cidade.getNome().contains("ape"));
    }

    @Test
    public void deveBuscarCidadePorUfIgnorandoMaiusculasMinusculas() {
        final List<Cidade> cidades = repository.findByUfIgnoreCase("rs");

        assertThat(cidades).hasSize(2);
        assertThat(cidades).allMatch(cidade -> cidade.getUf().equals("RS"));
    }
}
