package br.com.desenrolaarq.controller;

import br.com.desenrolaarq.entity.Vaga;
import br.com.desenrolaarq.service.AnaliseIaService;
import br.com.desenrolaarq.service.GeminiService;
import br.com.desenrolaarq.service.VagaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiService geminiService;
    private final AnaliseIaService analiseIaService;
    private final VagaService vagaService;

    public GeminiController(GeminiService geminiService,
                            AnaliseIaService analiseIaService,
                            VagaService vagaService) {
        this.geminiService = geminiService;
        this.analiseIaService = analiseIaService;
        this.vagaService = vagaService;
    }

    @GetMapping("/teste")
    public String testar() {
        return geminiService.testar();
    }

    @GetMapping("/teste-json")
    public String testarJson() {
        return geminiService.testarJson();
    }

    @GetMapping("/modelos")
    public String listarModelos() {
        return geminiService.listarModelos();
    }

    @GetMapping("/analisar/{vagaId}")
    public String analisar(@PathVariable Long vagaId) {

        Vaga vaga = vagaService.buscarPorId(vagaId);

        String textoCurriculo = """
            GUILHERME FILIPE DA ROSA

            E-mail: guilhermefdarosa15@gmail.com
            Telefone: 55999966691
            Cidade: Horizontina - RS

            FORMAÇÃO ACADÊMICA
            Bacharelado em Engenharia de Software - Ampli
            Concluído em janeiro de 2026.

            HISTÓRICO PROFISSIONAL

            Desenvolvedor de software desde 2018.

            Experiência com desenvolvimento Full Stack,
            Java, Kotlin, JavaScript, SQL, Spring Boot,
            APIs REST e desenvolvimento de aplicações Android.

            Experiência com sistemas ERP, bancos de dados
            Firebird, MySQL e SQL Server.

            Desenvolvimento e manutenção de funcionalidades,
            integrações com e-commerces e APIs REST.

            HABILIDADES E COMPETÊNCIAS

            Java
            Kotlin
            JavaScript
            SQL
            Spring Boot
            APIs REST
            Android
            Vue.js
            Git
            Metodologias ágeis
            """;

        return analiseIaService.analisar(vaga, textoCurriculo);
    }
}