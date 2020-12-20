package br.com.rosin.testecmp.testecmp.model;

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
    private String nome;
    private Sexo sexo;
    private LocalDate dataNascimento;
    private Integer idade;
    private Cidade cidade;
}
