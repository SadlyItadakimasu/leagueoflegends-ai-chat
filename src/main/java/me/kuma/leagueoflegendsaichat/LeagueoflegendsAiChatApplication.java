package me.kuma.leagueoflegendsaichat;

import me.kuma.leagueoflegendsaichat.application.AskChampionUseCase;
import me.kuma.leagueoflegendsaichat.application.ListChampionsUseCase;
import me.kuma.leagueoflegendsaichat.domain.ports.ChampionRepository;
import me.kuma.leagueoflegendsaichat.domain.ports.GenerativeAiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LeagueoflegendsAiChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeagueoflegendsAiChatApplication.class, args);
    }

    @Bean
    public ListChampionsUseCase provideListChampionsUseCase(ChampionRepository repository) {
        return new ListChampionsUseCase(repository);
    }

    @Bean
    public AskChampionUseCase provideAskChampionUseCase(ChampionRepository repository, GenerativeAiService genAiApi) {
        return new AskChampionUseCase(repository, genAiApi);
    }
}
