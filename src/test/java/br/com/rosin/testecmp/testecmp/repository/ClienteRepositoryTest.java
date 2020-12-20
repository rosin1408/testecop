package br.com.rosin.testecmp.testecmp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.rosin.testecmp.testecmp.model.Cidade;
import br.com.rosin.testecmp.testecmp.model.Cliente;
import br.com.rosin.testecmp.testecmp.model.Sexo;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @BeforeEach
    public void inserirDados() {
        final Cidade chapeco = new Cidade("abc", "Chapecó", "SC");

        repository.deleteAll();
        repository.save(new Cliente("abc", "Roberto Rosin", Sexo.MASCULINO, LocalDate.of(1990, 8, 14), 30, chapeco));
        repository.save(new Cliente("def", "Roberto Souza", Sexo.MASCULINO, LocalDate.of(1990, 8, 14), 30, chapeco));
        repository.save(new Cliente("ghi", "Anderson Rosin", Sexo.MASCULINO, LocalDate.of(1990, 8, 14), 30, chapeco));
    }

    @Test
    public void deveBuscarPorNomeIgnorandoMaiusculasMinusculas() {
        List<Cliente> clientes = repository.findByNomeLikeIgnoreCase("rosin");

        assertThat(clientes).hasSize(2);
        assertThat(clientes).allMatch(cliente -> cliente.getNome().contains("Rosin"));
    }
}
