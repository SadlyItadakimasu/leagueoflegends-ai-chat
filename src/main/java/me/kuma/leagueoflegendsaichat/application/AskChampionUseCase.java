package me.kuma.leagueoflegendsaichat.application;

import me.kuma.leagueoflegendsaichat.domain.exception.ChampionNotFoundException;
import me.kuma.leagueoflegendsaichat.domain.model.Champion;
import me.kuma.leagueoflegendsaichat.domain.ports.ChampionRepository;

public record AskChampionUseCase(ChampionRepository repository) {

    public String askChampion(Long championId, String question) {
        Champion champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String championContext = champion.generateContextByQuestion(question);

        return championContext;
    }
}
