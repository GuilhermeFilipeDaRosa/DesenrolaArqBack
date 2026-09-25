package br.com.desenrolaarq.service;

import br.com.desenrolaarq.entity.Candidato;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CandidatoExtractorService {

    public Candidato extrair(String texto) {

        Candidato candidato = new Candidato();

        candidato.setEmail(extrairEmail(texto));
        candidato.setTelefone(extrairTelefone(texto));

        String nome = extrairNome(texto);
        candidato.setNome(nome);

        String[] localizacao = extrairLocalizacao(texto);

        candidato.setCidade(localizacao[0]);
        candidato.setEstado(localizacao[1]);

        return candidato;
    }

    private String extrairEmail(String texto) {

        Pattern pattern = Pattern.compile(
                "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
        );

        Matcher matcher = pattern.matcher(texto);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }

    private String extrairTelefone(String texto) {

        Pattern pattern = Pattern.compile(
                "(?<!\\d)(?:\\(?\\d{2}\\)?\\s?)?\\d{4,5}[-\\s]?\\d{4}(?!\\d)"
        );

        Matcher matcher = pattern.matcher(texto);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }

    private String extrairNome(String texto) {

        String[] linhas = texto.split("\\R");

        for (String linha : linhas) {

            linha = linha.trim();

            if (linha.matches("[A-ZÁÀÂÃÉÊÍÓÔÕÚÇ ]{5,}")) {
                return linha;
            }
        }

        return null;
    }

    private String[] extrairLocalizacao(String texto) {

        String cidade = null;
        String estado = null;

        Pattern pattern = Pattern.compile(
                "Rua\\s+.+?\\s+(\\p{L}+)\\s*\\R\\s*([A-Z]{2})",
                Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = pattern.matcher(texto);

        if (matcher.find()) {
            cidade = matcher.group(1);
            estado = matcher.group(2).toUpperCase();
        }

        return new String[]{cidade, estado};
    }
}