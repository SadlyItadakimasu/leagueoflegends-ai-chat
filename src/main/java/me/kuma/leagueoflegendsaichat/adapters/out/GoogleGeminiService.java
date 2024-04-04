package me.kuma.leagueoflegendsaichat.adapters.out;

import feign.FeignException;
import feign.RequestInterceptor;
import me.kuma.leagueoflegendsaichat.domain.ports.GenerativeAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "spring.generative-ai.provider", havingValue = "GEMINI")
@FeignClient(name = "GeminiApi", url = "${gemini.base-url}", configuration = GoogleGeminiService.Config.class)
public interface GoogleGeminiService extends GenerativeAiService {

    @PostMapping("/v1beta/models/gemini-pro:generateContent")
    GoogleGeminiResp textOnlyInput(GoogleGeminiReq req);

    @Override
    default String generateContent(String objective, String context){

        String prompt = """
                %s
                %s
                """.formatted(objective, context);

        GoogleGeminiReq req = new GoogleGeminiReq(
                List.of(new Content(List.of(new Part(prompt))))
        );
        try {
            GoogleGeminiResp resp = textOnlyInput(req);

            return resp.candidates().get(0).content().parts().get(0).text();
        } catch (FeignException httpErrors){
            return "Erro de comunicacao com a API Gemini.";
        }catch (Exception unexpectedError){
            return "O retorno da API Gemini nao contem os dados esperados.";
        }
    }


    public record GoogleGeminiReq(List<Content> contents){}
    record GoogleGeminiResp(List<Candidate> candidates){}
    record Content(List<Part> parts){}
    record Part(String text){}
    record Candidate(Content content){}



        class Config{
            @Bean
            public RequestInterceptor apiKeyRequestInterceptor(@Value("${gemini.api-key}") String apiKey){
            return requestTemplate -> requestTemplate.query("key", apiKey);
            }
        }
    }

