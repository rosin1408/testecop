package br.com.rosin.testecmp.testecmp.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Cidade {

    @Id
    private String id;
    private String nome;
    private String uf;
}
