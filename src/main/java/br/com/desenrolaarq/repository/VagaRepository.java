package br.com.desenrolaarq.repository;

import br.com.desenrolaarq.entity.StatusVaga;
import br.com.desenrolaarq.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    long countByStatus(StatusVaga status);
}