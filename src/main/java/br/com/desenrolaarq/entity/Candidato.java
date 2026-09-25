package br.com.desenrolaarq.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CANDIDATO")
@Getter
@Setter
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCANDIDATO")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 150)
    private String nome;

    @Column(name = "EMAIL", length = 150, unique = true)
    private String email;

    @Column(name = "TELEFONE", length = 30)
    private String telefone;

    @Column(name = "CIDADE", length = 100)
    private String cidade;

    @Column(name = "ESTADO", length = 2)
    private String estado;
}