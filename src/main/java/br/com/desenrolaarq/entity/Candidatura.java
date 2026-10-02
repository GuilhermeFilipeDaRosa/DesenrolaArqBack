package br.com.desenrolaarq.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Entity
@Table(name = "CANDIDATURA", uniqueConstraints = {
        @UniqueConstraint(
                name = "UK_CANDIDATO_VAGA",
                columnNames = {"SCANDIDATO", "SVAGA"}
        )
})
@Getter
@Setter
public class Candidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCANDIDATURA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SCANDIDATO", nullable = false)
    private Candidato candidato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SVAGA", nullable = false)
    private Vaga vaga;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    @Column(name = "NOTA_IA", precision = 3, scale = 2)
    private BigDecimal notaIa;

    @Column(name = "RESULTADO_IA", columnDefinition = "TEXT")
    private String resultadoIa;

    @Column(name = "DATA_CANDIDATURA", nullable = false)
    private LocalDateTime dataCandidatura = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusCandidatura status = StatusCandidatura.PENDENTE;
}