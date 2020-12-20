package br.com.rosin.testecmp.testecmp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.rosin.testecmp.testecmp.model.Cidade;
import br.com.rosin.testecmp.testecmp.repository.CidadeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CidadeControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CidadeRepository cidadeRepository;

    @BeforeEach
    public void inserirDados() {
        cidadeRepository.deleteAll();
        cidadeRepository.save(new Cidade("abc", "São Lourenço do Oeste", "SC"));
        cidadeRepository.save(new Cidade("def", "Chapecó", "SC"));
        cidadeRepository.save(new Cidade("ghi", "Passo Fundo", "RS"));
    }

    @Test
    public void deveCadastrarCidade() {
        final Cidade cidade = new Cidade(null, "Erechim", "RS");

        final String url = "http://localhost:" + port + "/cidade";
        restTemplate.postForObject(url, cidade, Cidade.class);

        final Cidade cidadeCadastrada = cidadeRepository.findByNomeLikeIgnoreCase("Erechim").stream().findFirst().orElseThrow();

        assertThat(cidadeCadastrada).isNotNull();
    }

    @Test
    public void deveRetornarErro400QuandoNomeDaCidadeNaoPreenchido() {
        final Cidade cidade = new Cidade(null, "", "RS");

        final String url = "http://localhost:" + port + "/cidade";
        final ResponseEntity<Cidade> response = restTemplate.postForEntity(url, cidade, Cidade.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void deveRetornarErro400QuandoUfDaCidadeNaoPreenchido() {
        final Cidade cidade = new Cidade(null, "Erechim", "");

        final String url = "http://localhost:" + port + "/cidade";
        final ResponseEntity<Cidade> response = restTemplate.postForEntity(url, cidade, Cidade.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void deveConsultarCidadePeloNome() {
        final String url = "http://localhost:" + port + "/cidade/buscarPorNome?nome=chap";
        final ResponseEntity<List<Cidade>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        final List<Cidade> cidades = response.getBody();

        assertThat(cidades).hasSize(1);
        assertThat(response.getBody()).allMatch(c -> c.getNome().contains("Chapecó"));
    }

    @Test
    public void deveConsultarCidadePorUf() {
        final String url = "http://localhost:" + port + "/cidade/buscarPorUf?uf=SC";
        final ResponseEntity<List<Cidade>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        final List<Cidade> cidades = response.getBody();

        assertThat(cidades).hasSize(2);
        assertThat(response.getBody()).allMatch(c -> c.getUf().equals("SC"));
    }

}
