package br.com.rosin.testecmp.testecmp.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    @Test
    public void deveCalcularIdade() {
        final Cliente cliente = Cliente.builder().dataNascimento(LocalDate.of(1990, 8, 14)).build();
        cliente.calculaIdade();
        assertThat(cliente.getIdade()).isEqualTo(30);
    }
}
