package br.com.desenrolaarq.service;

import br.com.desenrolaarq.dto.DashboardDTO;
import br.com.desenrolaarq.repository.CandidaturaRepository;
import br.com.desenrolaarq.repository.VagaRepository;
import br.com.desenrolaarq.entity.StatusVaga;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final VagaRepository vagaRepository;
    private final CandidaturaRepository candidaturaRepository;

    public DashboardService(
            VagaRepository vagaRepository,
            CandidaturaRepository candidaturaRepository
    ) {
        this.vagaRepository = vagaRepository;
        this.candidaturaRepository = candidaturaRepository;
    }

    public DashboardDTO buscarDashboard() {

        long vagasAtivas =
                vagaRepository.countByStatus(StatusVaga.ATIVA);

        long curriculosRecebidos =
                candidaturaRepository.count();

        long candidatosAnalisados =
                candidaturaRepository.countCandidatosAnalisados();

        long candidatosAprovados =
                candidaturaRepository.countCandidatosAprovados();

        var candidatosPorVaga =
                candidaturaRepository.countCandidatosPorVaga();

        var ultimasAnalises =
                candidaturaRepository.buscarUltimasAnalises();

        return new DashboardDTO(
                vagasAtivas,
                curriculosRecebidos,
                candidatosAnalisados,
                candidatosAprovados,
                candidatosPorVaga,
                ultimasAnalises
        );
    }
}