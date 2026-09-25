package br.com.desenrolaarq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CandidatosPorVagaDTO {

    private Long vagaId;
    private String vaga;
    private long quantidade;
}