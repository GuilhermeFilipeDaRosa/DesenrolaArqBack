package br.com.desenrolaarq.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "VAGA")
@Getter
@Setter
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SVAGA")
    private Long id;

    @Column(name = "TITULO", length = 50, nullable = false)
    private String titulo;

    @Column(name = "DESCRICAO", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "CRITERIOS", nullable = false, columnDefinition = "TEXT")
    private String criterios;

    @Column(name = "INSTRUCAOIA", nullable = false, columnDefinition = "TEXT")
    private String instrucaoIa;

    @Column(name = "NOTACORTE", precision = 5, scale = 2, nullable = false)
    private BigDecimal notaCorte;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private StatusVaga status = StatusVaga.ATIVA;

    // getters e setters
}