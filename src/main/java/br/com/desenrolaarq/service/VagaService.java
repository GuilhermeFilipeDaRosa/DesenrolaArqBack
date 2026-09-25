package br.com.desenrolaarq.service;

import br.com.desenrolaarq.entity.Vaga;
import br.com.desenrolaarq.repository.VagaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    public VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    public List<Vaga> listarTodas() {
        return vagaRepository.findAll();
    }

    public Vaga criar(Vaga vaga) {
        return vagaRepository.save(vaga);
    }

    public Vaga buscarPorId(Long id) {
        return vagaRepository.findById(id)
                .orElseThrow();
    }

    public Vaga atualizar(Long id, Vaga vaga) {
        Vaga vagaExistente = vagaRepository.findById(id)
                .orElseThrow();

        vagaExistente.setTitulo(vaga.getTitulo());
        vagaExistente.setDescricao(vaga.getDescricao());
        vagaExistente.setCriterios(vaga.getCriterios());
        vagaExistente.setInstrucaoIa(vaga.getInstrucaoIa());
        vagaExistente.setNotaCorte(vaga.getNotaCorte());

        return vagaRepository.save(vagaExistente);
    }

    public void excluir(Long id) {
        vagaRepository.deleteById(id);
    }
}