package br.com.rosin.testecmp.testecmp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.rosin.testecmp.testecmp.model.Cidade;
import br.com.rosin.testecmp.testecmp.model.Cliente;
import br.com.rosin.testecmp.testecmp.model.Sexo;
import br.com.rosin.testecmp.testecmp.repository.ClienteRepository;
import java.time.LocalDate;
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
public class ClienteControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void inserirDados() {
        final Cidade chapeco = chapeco();

        clienteRepository.deleteAll();
        clienteRepository.save(new Cliente("abc", "Roberto Rosin", Sexo.MASCULINO, LocalDate.of(1990, 8, 14), 30, chapeco));
        clienteRepository.save(new Cliente("def", "Roberto Souza", Sexo.MASCULINO, LocalDate.of(1990, 8, 14), 30, chapeco));
        clienteRepository.save(new Cliente("ghi", "Anderson Rosin", Sexo.MASCULINO, LocalDate.of(1990, 8, 14), 30, chapeco));
    }

    @Test
    public void deveCadastrarCliente() {
        final Cliente cliente = new Cliente(null, "Cadastrado Agora", Sexo.FEMININO, LocalDate.of(1995, 7, 2), 25, chapeco());
        final String url = "http://localhost:" + port + "/cliente";
        restTemplate.postForObject(url, cliente, Cliente.class);

        final Cliente clienteCadastrado = clienteRepository.findByNomeLikeIgnoreCase("Cadastrado Agora").stream().findFirst().orElseThrow();

        assertThat(clienteCadastrado).isNotNull();
    }

    @Test
    public void deveRetornarErro400QuandoNomeDoClienteNaoPreenchido() {
        final Cliente cliente = new Cliente(null, "", Sexo.FEMININO, LocalDate.of(1995, 7, 2), 25, chapeco());
        final String url = "http://localhost:" + port + "/cliente";
        final ResponseEntity<Cliente> response = restTemplate.postForEntity(url, cliente, Cliente.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void deveRetornarErro400QuandoSexoDoClienteNaoPreenchido() {
        final Cliente cliente = new Cliente(null, "Novo Cliente", null, LocalDate.of(1995, 7, 2), 25, chapeco());
        final String url = "http://localhost:" + port + "/cliente";
        final ResponseEntity<Cliente> response = restTemplate.postForEntity(url, cliente, Cliente.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void deveRetornarErro400QuandoDataDeNascimentoDoClienteNaoPreenchido() {
        final Cliente cliente = new Cliente(null, "Novo Cliente", Sexo.FEMININO, null, 25, chapeco());
        final String url = "http://localhost:" + port + "/cliente";
        final ResponseEntity<Cliente> response = restTemplate.postForEntity(url, cliente, Cliente.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void deveRetornarErro400QuandoCidadeDoClienteNaoPreenchido() {
        final Cliente cliente = new Cliente(null, "Novo Cliente", Sexo.FEMININO, LocalDate.of(1995, 7, 2), 25, null);
        final String url = "http://localhost:" + port + "/cliente";
        final ResponseEntity<Cliente> response = restTemplate.postForEntity(url, cliente, Cliente.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void deveConsultarClientesPorNome() {
        final String url = "http://localhost:" + port + "/cliente/buscarPorNome?nome=roberto";
        final ResponseEntity<List<Cliente>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        assertThat(response.getBody()).allMatch(c -> c.getNome().contains("Roberto"));
    }

    @Test
    public void deveConsultarClientesPorId() {
        final String url = "http://localhost:" + port + "/cliente/abc";
        final Cliente cliente = restTemplate.getForObject(url, Cliente.class);

        assertThat(cliente.getId()).isEqualTo("abc");
        assertThat(cliente.getNome()).isEqualTo("Roberto Rosin");
    }

    @Test
    public void deveRetornarErro404AoConsultarClientePorIdInexistente() {
        final String url = "http://localhost:" + port + "/cliente/abcd";
        final ResponseEntity<Cliente> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, Cliente.class);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deveRemoverCliente() {
        final String url = "http://localhost:" + port + "/cliente/def";
        restTemplate.delete(url);

        final List<Cliente> clientes = clienteRepository.findAll();
        assertThat(clientes).hasSize(2);
    }

    @Test
    public void deveAlterarNomeDoCliente() {
        final Cliente cliente = new Cliente(null, "Nome Alterado", Sexo.FEMININO, LocalDate.of(1995, 7, 2), 25, chapeco());
        final String url = "http://localhost:" + port + "/cliente/def/nome/Nome Alterado";
        restTemplate.put(url, cliente, Cliente.class);

        final Cliente clienteAlterado = clienteRepository.findById("def").stream().findFirst().orElseThrow();

        assertThat(clienteAlterado).isNotNull();
        assertThat(clienteAlterado.getNome()).isEqualTo("Nome Alterado");
    }

    @Test
    public void deveRetornarErro404AoAlterarNomeDeClienteInexistente() {
        final String url = "http://localhost:" + port + "/cliente/defe/nome/Nome Alterado";
        final ResponseEntity<Cliente> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, null, Cliente.class);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    private Cidade chapeco() {
        return new Cidade("1", "Chapecó", "SC");
    }
}
