package br.com.desenrolaarq.dto;

import br.com.desenrolaarq.entity.StatusCandidatura;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CandidatoDetalheDTO(
        Long candidaturaId,
        Long candidatoId,
        String nome,
        String email,
        String telefone,
        String cidade,
        String estado,
        Long vagaId,
        String vagaTitulo,
        BigDecimal notaIa,
        String resultadoIa,
        LocalDateTime dataCandidatura,
        StatusCandidatura status
) {
}