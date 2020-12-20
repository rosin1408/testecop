package br.com.rosin.testecmp.testecmp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.rosin.testecmp.testecmp.model.Cidade;
import br.com.rosin.testecmp.testecmp.model.Cliente;
import br.com.rosin.testecmp.testecmp.model.Sexo;
import br.com.rosin.testecmp.testecmp.repository.ClienteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;


@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;
    @InjectMocks
    private ClienteService service;

    @Test
    public void deveSalvarClienteInvocandoMetodoSaveDoRepository() {
        Cidade cidade = new Cidade("1", "Chapecó", "SC");
        Cliente cliente = new Cliente("1", "Roberto Rosin", Sexo.MASCULINO, LocalDate.of(1990,8,14), 30, cidade);
        when(repository.save(cliente)).thenReturn(cliente);

        final Cliente clienteSalvo = service.salvar(cliente);

        verify(repository, only()).save(cliente);
        assertThat(clienteSalvo).isEqualTo(cliente);
    }

    @Test
    public void deveCalcularIdadeDoClienteQuandoNaoInformado() {
        Cidade cidade = new Cidade("1", "Chapecó", "SC");
        Cliente cliente = new Cliente("1", "Roberto Rosin", Sexo.MASCULINO, LocalDate.of(1990,8,14), null, cidade);
        when(repository.save(cliente)).thenReturn(cliente);

        final Cliente clienteSalvo = service.salvar(cliente);

        verify(repository, only()).save(new Cliente("1", "Roberto Rosin", Sexo.MASCULINO, LocalDate.of(1990,8,14), 30, cidade));
        assertThat(clienteSalvo).isEqualTo(cliente);
    }

    @Test
    public void deveBuscarClientePorNomeInvocandoMetodoDoRepository() {
        Cidade cidade = new Cidade("1", "Chapecó", "SC");
        Cliente cliente = new Cliente("1", "Roberto Rosin", Sexo.MASCULINO, LocalDate.of(1990,8,14), 30, cidade);
        final List<Cliente> clientes = List.of(cliente);

        when(repository.findByNomeLikeIgnoreCase("Roberto")).thenReturn(clientes);

        final List<Cliente> clientesRetornados = service.buscarPorNome("Roberto");

        verify(repository, only()).findByNomeLikeIgnoreCase("Roberto");
        assertThat(clientesRetornados).hasSize(1);
        assertThat(clientesRetornados).isEqualTo(clientes);
    }

    @Test
    public void deveBuscarClientePorIdInvocandoMetodoDoRepository() {
        Cidade cidade = new Cidade("1", "Chapecó", "SC");
        Cliente cliente = new Cliente("1", "Roberto Rosin", Sexo.MASCULINO, LocalDate.of(1990,8,14), 30, cidade);

        when(repository.findById("1")).thenReturn(Optional.of(cliente));

        final Optional<Cliente> clienteRetornado = service.buscarPorId("1");

        verify(repository, only()).findById("1");
        assertThat(clienteRetornado).isPresent();
        assertThat(clienteRetornado).isEqualTo(Optional.of(cliente));
    }

    @Test
    public void deveRemoverClientePorIdInvocandoMetodoDoRepository() {
        service.excluir("1");

        verify(repository, only()).deleteById("1");
    }

    @Test
    public void deveAlterarNomeDoCliente() throws NotFoundException {
        Cidade cidade = new Cidade("1", "Chapecó", "SC");
        Cliente cliente = new Cliente("123", "Roberto Rosin", Sexo.MASCULINO, LocalDate.of(1990,8,14), 30, cidade);

        when(repository.findById("123")).thenReturn(Optional.of(cliente));
        when(repository.save(cliente)).thenReturn(cliente);

        final Cliente clienteAlterado = service.alterarNome("123", "Novo Nome");

        assertThat(clienteAlterado.getNome()).isEqualTo("Novo Nome");
        verify(repository, times(1)).save(any());
    }

    @Test
    public void deveLancarExcessaoQuandoNaoEncontrarCliente()  {
        when(repository.findById("123")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.alterarNome("123", "Novo Nome")).isInstanceOf(NotFoundException.class);
    }

}
