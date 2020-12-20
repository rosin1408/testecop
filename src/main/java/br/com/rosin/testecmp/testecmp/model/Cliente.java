package br.com.rosin.testecmp.testecmp.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

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
    private String nome;
    private Sexo sexo;
    private LocalDate dataNascimento;
    private Integer idade;
    private Cidade cidade;
}
