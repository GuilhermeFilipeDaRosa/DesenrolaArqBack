package br.com.desenrolaarq.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AnaliseIaResponse {

    private BigDecimal nota;

    private String resumo;

    private List<String> pontosFortes;

    private List<String> pontosFracos;

    private List<CriterioAnalise> criterios;
}