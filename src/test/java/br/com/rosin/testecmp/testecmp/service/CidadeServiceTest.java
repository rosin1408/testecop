package br.com.rosin.testecmp.testecmp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.rosin.testecmp.testecmp.model.Cidade;
import br.com.rosin.testecmp.testecmp.repository.CidadeRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class CidadeServiceTest {

    @Mock
    private CidadeRepository repository;
    @InjectMocks
    private CidadeService service;

    @Test
    public void deveSalvarCidadeInvocandoMetodoSaveDoRepository() {
        Cidade cidade = new Cidade("1", "Chapecó", "SC");
        service.salvar(cidade);
        verify(repository, only()).save(cidade);
    }

    @Test
    public void deveBuscarCidadePorNomeInvocandoMetodoFindByNameIgnoringCaseDoRepository() {
        Cidade chapeco = new Cidade("1", "Chapecó", "SC");
        Cidade aguasDeChapeco = new Cidade("2", "Aguas de Chapecó", "SC");
        List<Cidade> cidades = List.of(chapeco, aguasDeChapeco);

        when(repository.findByNomeLikeIgnoreCase("Chapecó")).thenReturn(cidades);

        final List<Cidade> cidadesRetornadas = service.buscarPorNome("Chapecó");

        verify(repository, only()).findByNomeLikeIgnoreCase("Chapecó");
        assertThat(cidadesRetornadas).hasSize(2);
        assertThat(cidadesRetornadas).isEqualTo(cidades);
    }

    @Test
    public void deveBuscarCidadesPorUfInvocandoMetodoDoRepository() {
        Cidade chapeco = new Cidade("1", "Chapecó", "SC");
        Cidade aguasDeChapeco = new Cidade("2", "Aguas de Chapecó", "SC");
        List<Cidade> cidades = List.of(chapeco, aguasDeChapeco);

        when(repository.findByUfIgnoreCase("SC")).thenReturn(cidades);

        final List<Cidade> cidadesRetornadas = service.buscarPorUf("SC");

        verify(repository, only()).findByUfIgnoreCase("SC");
        assertThat(cidadesRetornadas).hasSize(2);
        assertThat(cidadesRetornadas).isEqualTo(cidades);
    }

}
