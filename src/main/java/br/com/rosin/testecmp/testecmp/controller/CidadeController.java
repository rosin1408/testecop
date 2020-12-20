package br.com.rosin.testecmp.testecmp.controller;

import br.com.rosin.testecmp.testecmp.model.Cidade;
import br.com.rosin.testecmp.testecmp.service.CidadeService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cidade")
@AllArgsConstructor
public class CidadeController {

    private final CidadeService service;

    @PostMapping
    public Cidade novo(@RequestBody Cidade cidade) {
        return service.salvar(cidade);
    }

    @GetMapping("/buscarPorNome")
    public List<Cidade> buscarPorNome(@RequestParam("nome") String nome) {
        return service.buscarPorNome(nome);
    }

    @GetMapping("/buscarPorUf")
    public List<Cidade> buscarPorUf(@RequestParam("uf") String uf) {
        return service.buscarPorUf(uf);
    }
}
