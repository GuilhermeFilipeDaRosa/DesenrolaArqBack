package br.com.desenrolaarq.controller;

import br.com.desenrolaarq.service.CurriculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/curriculo")
public class CurriculoController {

    private final CurriculoService curriculoService;

    public CurriculoController(CurriculoService curriculoService) {
        this.curriculoService = curriculoService;
    }

    @PostMapping("/{vagaId}")
    public ResponseEntity<?> uploadCurriculo(
            @PathVariable Long vagaId,
            @RequestPart("arquivo") MultipartFile arquivo) {

        try {

            String nomeArquivo = curriculoService.salvar(vagaId, arquivo);

            return ResponseEntity.ok(
                    Map.of(
                            "mensagem", "Currículo enviado com sucesso.",
                            "vagaId", vagaId,
                            "arquivo", nomeArquivo
                    )
            );

        } catch (IllegalArgumentException e) {

            if ("Vaga não encontrada.".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }

            if ("Candidato já possui candidatura para esta vaga.".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Map.of(
                                "erro", e.getMessage()
                        ));
            }

            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));

        } catch (IOException e) {

            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro ao salvar o arquivo."));
        }
    }
}