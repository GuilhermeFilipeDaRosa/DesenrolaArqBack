package br.com.desenrolaarq.service;

import br.com.desenrolaarq.entity.Candidato;
import br.com.desenrolaarq.repository.CandidatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;

    public CandidatoService(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
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


}