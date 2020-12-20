package br.com.rosin.testecmp.testecmp.service;

import br.com.rosin.testecmp.testecmp.model.Cliente;
import br.com.rosin.testecmp.testecmp.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public Cliente salvar(Cliente cliente) {
        if (cliente.getIdade() == null) {
            cliente.calculaIdade();
        }
        return repository.save(cliente);
    }

    public Optional<Cliente> buscarPorId(String id) {
        return repository.findById(id);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return repository.findByNomeLikeIgnoreCase(nome);
    }

    public void excluir(String id) {
        repository.deleteById(id);
    }

    public Cliente alterarNome(String id, String nome) throws NotFoundException {
        Cliente cliente = repository.findById(id).orElseThrow(NotFoundException::new);
        cliente.setNome(nome);
        return repository.save(cliente);
    }
}
