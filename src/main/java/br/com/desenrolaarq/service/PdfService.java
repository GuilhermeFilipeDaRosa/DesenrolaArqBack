package br.com.desenrolaarq.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class PdfService {

    public String extrairTexto(Path caminhoArquivo) throws IOException {

        try (PDDocument documento = Loader.loadPDF(caminhoArquivo.toFile())) {

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(documento);
        }
    }
}