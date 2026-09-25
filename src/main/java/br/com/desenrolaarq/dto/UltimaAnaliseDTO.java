package br.com.desenrolaarq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class UltimaAnaliseDTO {

    private Long id;
    private String nome;
    private String vaga;
    private BigDecimal nota;
}