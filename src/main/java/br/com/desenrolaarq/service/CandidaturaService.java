package br.com.desenrolaarq.service;

import br.com.desenrolaarq.entity.Candidato;
import br.com.desenrolaarq.entity.Candidatura;
import br.com.desenrolaarq.entity.Vaga;
import br.com.desenrolaarq.repository.CandidaturaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CandidaturaService {

    private final CandidaturaRepository candidaturaRepository;

    public CandidaturaService(CandidaturaRepository candidaturaRepository) {
        this.candidaturaRepository = candidaturaRepository;
    }

    public Candidatura criar(Candidato candidato, Vaga vaga) {

        if (candidaturaRepository.existsByCandidatoAndVaga(candidato, vaga)) {
            throw new IllegalArgumentException(
                    "Candidato já possui candidatura para esta vaga."
            );
        }

        Candidatura candidatura = new Candidatura();

        candidatura.setCandidato(candidato);
        candidatura.setVaga(vaga);

        return candidaturaRepository.save(candidatura);
    }

    public Candidatura atualizarAnaliseIa(
            Candidatura candidatura,
            BigDecimal nota,
            String resultadoJson) {

        candidatura.setNotaIa(nota);
        candidatura.setResultadoIa(resultadoJson);

        return candidaturaRepository.save(candidatura);
    }
}