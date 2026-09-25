package br.com.desenrolaarq.repository;

import br.com.desenrolaarq.dto.CandidatosPorVagaDTO;
import br.com.desenrolaarq.dto.UltimaAnaliseDTO;
import br.com.desenrolaarq.entity.Candidatura;
import br.com.desenrolaarq.entity.Candidato;
import br.com.desenrolaarq.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {

    boolean existsByCandidatoAndVaga(Candidato candidato, Vaga vaga);

    @Query("""
        SELECT COUNT(c)
        FROM Candidatura c
        WHERE c.notaIa IS NOT NULL
    """)
    long countCandidatosAnalisados();

    @Query("""
        SELECT COUNT(c)
        FROM Candidatura c
        WHERE c.notaIa IS NOT NULL
          AND c.notaIa >= c.vaga.notaCorte
    """)
    long countCandidatosAprovados();

    @Query("""
        SELECT new br.com.desenrolaarq.dto.CandidatosPorVagaDTO(
            c.vaga.id,
            c.vaga.titulo,
            COUNT(c)
        )
        FROM Candidatura c
        GROUP BY c.vaga.id, c.vaga.titulo
        ORDER BY COUNT(c) DESC
    """)
    List<CandidatosPorVagaDTO> countCandidatosPorVaga();

    @Query("""
        SELECT new br.com.desenrolaarq.dto.UltimaAnaliseDTO(
            c.id,
            c.candidato.nome,
            c.vaga.titulo,
            c.notaIa
        )
        FROM Candidatura c
        WHERE c.notaIa IS NOT NULL
        ORDER BY c.dataCandidatura DESC
    """)
    List<UltimaAnaliseDTO> buscarUltimasAnalises();
}