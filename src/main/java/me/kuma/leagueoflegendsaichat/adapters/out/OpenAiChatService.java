package me.kuma.leagueoflegendsaichat.adapters.out;

import feign.FeignException;
import feign.RequestInterceptor;
import me.kuma.leagueoflegendsaichat.domain.ports.GenerativeAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpHeaders;
import java.util.List;
@ConditionalOnProperty(name = "spring.generative-ai.provider", havingValue = "OPENAI", matchIfMissing = true)
@FeignClient(name = "openAiApi", url = "${openai.base-url}", configuration = OpenAiChatService.Config.class)
public interface OpenAiChatService extends GenerativeAiService {

    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResp chatCompletion(OpenAiChatCompletionReq req);

    @Override
    default String generateContent(String objective, String context){

        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", context)
        );
        OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, messages);
        try{
        OpenAiChatCompletionResp resp = chatCompletion(req);

        return resp.choices().get(0).message().content();
        } catch (FeignException httpErrors){
            return "Erro de comunicacao com a API OpenAI.";
        }catch (Exception unexpectedError){
            return "O retorno da API OpenAI nao contem os dados esperados.";
        }
    }


    public record OpenAiChatCompletionReq(String model, List<Message> messages){ }

    public record Message(String role, String content){}

    record OpenAiChatCompletionResp(List<Choice> choices){}

    record Choice(Message message){}



        class Config{
            @Bean
            public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey){
            return requestTemplate -> requestTemplate.header(
                    HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
            }
        }
    }

