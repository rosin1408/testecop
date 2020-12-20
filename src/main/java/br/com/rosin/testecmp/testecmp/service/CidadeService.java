package br.com.rosin.testecmp.testecmp.service;

import br.com.rosin.testecmp.testecmp.model.Cidade;
import br.com.rosin.testecmp.testecmp.repository.CidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CidadeService {

    private final CidadeRepository repository;

    public Cidade salvar(Cidade cidade) {
        return repository.save(cidade);
    }

    public List<Cidade> buscarPorNome(String nome) {
        return repository.findByNomeLikeIgnoreCase(nome);
    }

    public List<Cidade> buscarPorUf(String uf) {
        return repository.findByUfIgnoreCase(uf);
    }
}
