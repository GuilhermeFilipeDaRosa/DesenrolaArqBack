package br.com.desenrolaarq.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriterioAnalise {

    private String criterio;

    private Boolean atende;

    private String observacao;
}