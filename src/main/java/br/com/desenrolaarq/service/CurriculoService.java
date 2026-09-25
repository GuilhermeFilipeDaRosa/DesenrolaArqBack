package br.com.desenrolaarq.service;

import br.com.desenrolaarq.dto.AnaliseIaResponse;
import br.com.desenrolaarq.entity.Candidato;
import br.com.desenrolaarq.entity.Candidatura;
import br.com.desenrolaarq.entity.Vaga;
import br.com.desenrolaarq.repository.VagaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CurriculoService {

    private final VagaRepository vagaRepository;
    private final PdfService pdfService;
    private final CandidatoExtractorService candidatoExtractorService;
    private final CandidatoService candidatoService;
    private final CandidaturaService candidaturaService;

    private final Path diretorioUpload = Paths.get("uploads/curriculos");
    private final AnaliseIaService analiseIaService;

    public CurriculoService(VagaRepository vagaRepository,
                            PdfService pdfService,
                            CandidatoExtractorService candidatoExtractorService,
                            CandidatoService candidatoService,
                            CandidaturaService candidaturaService,
                            AnaliseIaService analiseIaService) {
        this.vagaRepository = vagaRepository;
        this.pdfService = pdfService;
        this.candidatoExtractorService = candidatoExtractorService;
        this.candidatoService = candidatoService;
        this.candidaturaService = candidaturaService;
        this.analiseIaService = analiseIaService;
    }

    public String salvar(Long vagaId, MultipartFile arquivo) throws IOException {

        Vaga vaga = vagaRepository.findById(vagaId)
                .orElseThrow(() -> new IllegalArgumentException("Vaga não encontrada."));

        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não informado.");
        }

        String nomeOriginal = arquivo.getOriginalFilename();

        if (nomeOriginal == null || !nomeOriginal.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Apenas arquivos PDF são permitidos.");
        }

        Files.createDirectories(diretorioUpload);

        String nomeArquivo = UUID.randomUUID() + ".pdf";

        Path caminhoArquivo = diretorioUpload.resolve(nomeArquivo);

        Files.copy(arquivo.getInputStream(), caminhoArquivo);

        String texto = pdfService.extrairTexto(caminhoArquivo);
        Candidato candidato = candidatoExtractorService.extrair(texto);
        Candidato candidatoSalvo = candidatoService.encontrarOuCriar(candidato);
        Candidatura candidatura = candidaturaService.criar(candidatoSalvo, vaga);

        try {
            String resultadoJson =
                    analiseIaService.analisar(vaga, texto);

            AnaliseIaResponse resultado =
                    analiseIaService.converterResultado(resultadoJson);

            candidaturaService.atualizarAnaliseIa(
                    candidatura,
                    resultado.getNota(),
                    resultadoJson
            );
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível analisar no momento!");
        }

        return nomeArquivo;
    }
}