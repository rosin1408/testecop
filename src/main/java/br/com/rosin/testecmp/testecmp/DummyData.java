package br.com.rosin.testecmp.testecmp;

import br.com.rosin.testecmp.testecmp.repository.CidadeRepository;
import br.com.rosin.testecmp.testecmp.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DummyData implements CommandLineRunner {

    private final CidadeRepository repository;
    private final ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
//        repository.deleteAll();
//
//        final Cidade chapeco = new Cidade("1", "Chapecó", "SC");
//        repository.save(chapeco);
//        repository.save(new Cidade("2", "São Lourenço do Oeste", "SC"));
//        repository.save(new Cidade("3", "Passo Fundo", "RS"));
//
//        System.out.println("Listando cidades cadastradas");
//        repository.findAll().forEach(System.out::println);
//
//        clienteRepository.deleteAll();
//        clienteRepository.save(new Cliente(null, "Roberto Rosin", Sexo.MASCULINO, LocalDate.of(1990,8,14), 30, chapeco));
//        clienteRepository.save(new Cliente(null, "Roberto Souza", Sexo.MASCULINO, LocalDate.of(1990,8,14), 30, chapeco));
//        clienteRepository.save(new Cliente(null, "Anderson Rosin", Sexo.MASCULINO, LocalDate.of(1990,8,14), 30, chapeco));
//
//        System.out.println("Listando clientes cadastrados");
//        clienteRepository.findAll().forEach(System.out::println);
    }
}
