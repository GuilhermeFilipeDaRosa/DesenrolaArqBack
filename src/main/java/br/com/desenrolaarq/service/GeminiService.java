package br.com.desenrolaarq.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.ListModelsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;

import java.util.List;
import java.util.Map;
@Service
public class GeminiService {

    private final Client client;

    public GeminiService(
            @Value("${gemini.api.key}") String apiKey) {

        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String testar() {

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-flash-latest",
                        "Responda apenas: Gemini conectado com sucesso!",
                        null
                );

        return response.text();
    }

    public String testarJson() {

        String prompt = """
            Avalie um candidato fictício para uma vaga de Desenvolvedor Java.

            Vaga:
            Desenvolvedor Java Pleno.

            Critérios:
            - Java
            - Spring Boot
            - APIs REST
            - SQL

            Currículo:
            Candidato com 5 anos de experiência em Java,
            Spring Boot e desenvolvimento de APIs REST.

            Gere a avaliação seguindo exatamente o formato JSON solicitado.
            """;

        GenerateContentConfig config = GenerateContentConfig.builder()
                .responseMimeType("application/json")
                .responseSchema(criarSchemaAnalise())
                .build();

        GenerateContentResponse response =
                client.models.generateContent(
                       // "gemini-flash-latest", // dando 503 muita gente usando
                        //"gemini-3.8-flash", //You exceeded your current quota
                        "gemini-3.1-flash-lite",
                        prompt,
                        config
                );

        return response.text();
    }

    public String listarModelos() {

        StringBuilder resultado = new StringBuilder();

        client.models.list(ListModelsConfig.builder().build())
                .forEach(model -> {

                    resultado.append(model.name())
                            .append(" | ")
                            .append(model.supportedActions())
                            .append("\n");
                });

        return resultado.toString();
    }

    private Schema criarSchemaAnalise() {

        Schema criterioSchema = Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(Map.of(
                        "criterio", Schema.builder()
                                .type(Type.Known.STRING)
                                .description("Critério da vaga que está sendo avaliado.")
                                .build(),

                        "atende", Schema.builder()
                                .type(Type.Known.BOOLEAN)
                                .description("Indica se o candidato atende ao critério.")
                                .build(),

                        "observacao", Schema.builder()
                                .type(Type.Known.STRING)
                                .description("Explicação objetiva sobre o atendimento do critério.")
                                .build()
                ))
                .required(List.of("criterio", "atende", "observacao"))
                .build();

        return Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(Map.of(
                        "nota", Schema.builder()
                                .type(Type.Known.NUMBER)
                                .description("Nota final de aderência do candidato à vaga, obrigatoriamente entre 0 e 10.")
                                .build(),

                        "resumo", Schema.builder()
                                .type(Type.Known.STRING)
                                .description("Resumo geral da avaliação.")
                                .build(),

                        "pontosFortes", Schema.builder()
                                .type(Type.Known.ARRAY)
                                .items(Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build())
                                .build(),

                        "pontosFracos", Schema.builder()
                                .type(Type.Known.ARRAY)
                                .items(Schema.builder()
                                        .type(Type.Known.STRING)
                                        .build())
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
}