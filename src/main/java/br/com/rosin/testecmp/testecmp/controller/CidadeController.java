package br.com.rosin.testecmp.testecmp.controller;

import br.com.rosin.testecmp.testecmp.model.Cidade;
import br.com.rosin.testecmp.testecmp.service.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
