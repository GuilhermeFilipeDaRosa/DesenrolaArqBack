package br.com.desenrolaarq.controller;

import br.com.desenrolaarq.dto.AlterarStatusCandidaturaDTO;
import br.com.desenrolaarq.dto.CandidatoListagemDTO;
import br.com.desenrolaarq.dto.CandidatoDetalheDTO;
import br.com.desenrolaarq.service.CandidatoService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidatos")
@RequiredArgsConstructor
public class CandidatoController {

    private final CandidatoService candidatoService;

    @GetMapping
    public Page<CandidatoListagemDTO> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long vagaId,
            Pageable pageable
    ) {
        return candidatoService.listar(
                nome,
                vagaId,
                pageable
        );
    }

    @GetMapping("/{candidaturaId}")
    public CandidatoDetalheDTO buscar(
            @PathVariable Long candidaturaId
    ) {
        return candidatoService.buscar(candidaturaId);
    }

    @PatchMapping("/{candidaturaId}/status")
    public ResponseEntity<Void> alterarStatus(
            @PathVariable Long candidaturaId,
            @RequestBody AlterarStatusCandidaturaDTO dto
    ) {
        candidatoService.alterarStatus(
                candidaturaId,
                dto.status()
        );

        return ResponseEntity.noContent().build();
    }
}