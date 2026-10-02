package br.com.desenrolaarq.dto;

import java.math.BigDecimal;

public record CandidatoListagemDTO(
        Long candidatoId,

        Long candidaturaId,
        String nome,
        String email,
        String telefone,
        String cidade,
        String estado,
        Long vagaId,
        String vagaTitulo,
        BigDecimal notaIa
) {
}