package br.com.desenrolaarq.service;

import br.com.desenrolaarq.entity.Vaga;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.desenrolaarq.dto.AnaliseIaResponse;

import java.util.List;
import java.util.Map;

@Service
public class AnaliseIaService {

    private final Client client;
    private final ObjectMapper objectMapper;

    public AnaliseIaService(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public String analisar(Vaga vaga, String textoCurriculo) {

        String prompt = """
            Você é um sistema de análise de currículos para recrutamento.

            Analise o candidato exclusivamente com base nas informações
            fornecidas abaixo.

            ====================
            VAGA
            ====================

            Título:
            %s

            Descrição:
            %s

            Critérios:
            %s

            Instrução adicional da IA:
            %s

            ====================
            CURRÍCULO
            ====================

            %s

            ====================
            REGRAS DA AVALIAÇÃO
            ====================

            1. A nota deve ser um número entre 0 e 10.
            2. Avalie a aderência do candidato à vaga.
            3. Não invente experiências ou informações que não estejam no currículo.
            4. Avalie individualmente cada critério informado pela vaga.
            5. Em "atende", use true somente quando houver evidência suficiente no currículo.
            6. Explique objetivamente cada avaliação.
            7. Considere experiência profissional, conhecimentos técnicos e formação quando forem relevantes.
            8. A resposta deve seguir exatamente o formato JSON solicitado.
            """
                .formatted(
                        vaga.getTitulo(),
                        vaga.getDescricao(),
                        vaga.getCriterios(),
                        vaga.getInstrucaoIa(),
                        textoCurriculo
                );

        GenerateContentConfig config = GenerateContentConfig.builder()
                .responseMimeType("application/json")
                .responseSchema(criarSchemaAnalise())
                .build();

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.1-flash-lite",
                        prompt,
                        config
                );

        return response.text();
    }

    private Schema criarSchemaAnalise() {

        Schema criterioSchema = Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(Map.of(
                        "criterio", Schema.builder()
                                .type(Type.Known.STRING)
                                .description("Critério da vaga avaliado.")
                                .build(),

                        "atende", Schema.builder()
                                .type(Type.Known.BOOLEAN)
                                .description("Indica se o candidato atende ao critério.")
                                .build(),

                        "observacao", Schema.builder()
                                .type(Type.Known.STRING)
                                .description("Justificativa objetiva da avaliação.")
                                .build()
                ))
                .required(List.of(
                        "criterio",
                        "atende",
                        "observacao"
                ))
                .build();

        return Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(Map.of(
                        "nota", Schema.builder()
                                .type(Type.Known.NUMBER)
                                .description("Nota final entre 0 e 10.")
                                .build(),

                        "resumo", Schema.builder()
                                .type(Type.Known.STRING)
                                .description("Resumo geral da avaliação.")
                                .build(),

                        "pontosFortes", Schema.builder()
                                .type(Type.Known.ARRAY)
                                .items(
                                        Schema.builder()
                                                .type(Type.Known.STRING)
                                                .build()
                                )
                                .build(),

                        "pontosFracos", Schema.builder()
                                .type(Type.Known.ARRAY)
                                .items(
                                        Schema.builder()
                                                .type(Type.Known.STRING)
                                                .build()
                                )
                                .build(),

                        "criterios", Schema.builder()
                                .type(Type.Known.ARRAY)
                                .items(criterioSchema)
                                .build()
                ))
                .required(List.of(
                        "nota",
                        "resumo",
                        "pontosFortes",
                        "pontosFracos",
                        "criterios"
                ))
                .build();
    }

    public AnaliseIaResponse converterResultado(String json)
            throws Exception {

        return objectMapper.readValue(
                json,
                AnaliseIaResponse.class
        );
    }
}