package br.com.desenrolaarq.service;

import br.com.desenrolaarq.dto.CandidatoDetalheDTO;
import br.com.desenrolaarq.dto.CandidatoListagemDTO;
import br.com.desenrolaarq.entity.Candidato;
import br.com.desenrolaarq.entity.Candidatura;
import br.com.desenrolaarq.entity.StatusCandidatura;
import br.com.desenrolaarq.repository.CandidatoRepository;
import br.com.desenrolaarq.repository.CandidaturaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatoService {

    private final CandidaturaRepository candidaturaRepository;

    private final CandidatoRepository candidatoRepository;

    public CandidatoService(CandidatoRepository candidatoRepository,
                            CandidaturaRepository candidaturaRepository) {
        this.candidatoRepository = candidatoRepository;
        this.candidaturaRepository = candidaturaRepository;
    }

    public List<Candidato> listarTodos() {
        return candidatoRepository.findAll();
    }

    public Candidato criar(Candidato candidato) {
        return candidatoRepository.save(candidato);
    }

    public Candidato buscarPorId(Long id) {
        return candidatoRepository.findById(id)
                .orElseThrow();
    }

    public void excluir(Long id) {
        candidatoRepository.deleteById(id);
    }

    public Candidato buscarPorEmail(String email) {
        return candidatoRepository.findByEmail(email)
                .orElse(null);
    }

    public Candidato encontrarOuCriar(Candidato candidato) {

        if (candidato.getEmail() != null && !candidato.getEmail().isBlank()) {

            return candidatoRepository.findByEmail(candidato.getEmail())
                    .orElseGet(() -> candidatoRepository.save(candidato));
        }

        return candidatoRepository.save(candidato);
    }

    public Page<CandidatoListagemDTO> listar(
            String nome,
            Long vagaId,
            Pageable pageable
    ) {

        if (nome != null) {
            nome = nome.trim();
        }

        if (nome == null || nome.isEmpty()) {
            nome = "";
        }

        Page<Candidatura> candidaturas =
                candidaturaRepository.buscarCandidatos(
                        nome,
                        vagaId,
                        pageable
                );

        return candidaturas.map(candidatura ->
                new CandidatoListagemDTO(
                        candidatura.getCandidato().getId(),
                        candidatura.getId(),
                        candidatura.getCandidato().getNome(),
                        candidatura.getCandidato().getEmail(),
                        candidatura.getCandidato().getTelefone(),
                        candidatura.getCandidato().getCidade(),
                        candidatura.getCandidato().getEstado(),
                        candidatura.getVaga().getId(),
                        candidatura.getVaga().getTitulo(),
                        candidatura.getNotaIa()
                )
        );
    }

    public CandidatoDetalheDTO buscar(Long candidaturaId) {

        Candidatura candidatura = candidaturaRepository.findById(candidaturaId)
                .orElseThrow(() ->
                        new RuntimeException("Candidatura não encontrada")
                );

        return new CandidatoDetalheDTO(
                candidatura.getId(),
                candidatura.getCandidato().getId(),
                candidatura.getCandidato().getNome(),
                candidatura.getCandidato().getEmail(),
                candidatura.getCandidato().getTelefone(),
                candidatura.getCandidato().getCidade(),
                candidatura.getCandidato().getEstado(),
                candidatura.getVaga().getId(),
                candidatura.getVaga().getTitulo(),
                candidatura.getNotaIa(),
                candidatura.getResultadoIa(),
                candidatura.getDataCandidatura(),
                candidatura.getStatus()
        );
    }

    public void alterarStatus(
            Long candidaturaId,
            StatusCandidatura status
    ) {

        Candidatura candidatura = candidaturaRepository.findById(candidaturaId)
                .orElseThrow(() ->
                        new RuntimeException("Candidatura não encontrada")
                );

        candidatura.setStatus(status);

        candidaturaRepository.save(candidatura);
    }
}