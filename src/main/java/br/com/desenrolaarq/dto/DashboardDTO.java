package br.com.desenrolaarq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardDTO {

    private long vagasAtivas;
    private long curriculosRecebidos;
    private long candidatosAnalisados;
    private long candidatosAprovados;

    private List<CandidatosPorVagaDTO> candidatosPorVaga;
    private List<UltimaAnaliseDTO> ultimasAnalises;
}