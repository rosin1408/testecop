package br.com.rosin.testecmp.testecmp.controller;

import br.com.rosin.testecmp.testecmp.model.Cliente;
import br.com.rosin.testecmp.testecmp.service.ClienteService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    private Cliente novo(@RequestBody @Valid Cliente cliente) {
        return service.salvar(cliente);
    }

    @PutMapping("/{id}/nome/{nome}")
    private Cliente alterar(@PathVariable String id, @PathVariable String nome) {
        try {
            return service.alterarNome(id, nome);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable String id) {
        service.excluir(id);
    }

    @RequestMapping("/{id}")
    public Cliente buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @RequestMapping("/buscarPorNome")
    public List<Cliente> buscarPorNome(@RequestParam String nome) {
        return service.buscarPorNome(nome);
    }
}
