package br.com.rosin.testecmp.testecmp.model;

import java.time.Period;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Cliente {

    @Id
    private String id;
    @NotBlank(message = "Informe o nome do cliente")
    private String nome;
    @NotNull(message = "Informe o sexo do cliente")
    private Sexo sexo;
    @NotNull(message = "Informe a data de nascimento do cliente")
    private LocalDate dataNascimento;
    private Integer idade;
    @NotNull(message = "Informe a cidade do cliente")
    private Cidade cidade;

    public void calculaIdade() {
        setIdade(Period.between(dataNascimento, LocalDate.now()).getYears());
    }
}
