package br.com.rosin.testecmp.testecmp.model;

import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String nome;
    @NotBlank
    private String uf;
}
