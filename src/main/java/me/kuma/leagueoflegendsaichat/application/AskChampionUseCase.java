package me.kuma.leagueoflegendsaichat.application;

import me.kuma.leagueoflegendsaichat.domain.exception.ChampionNotFoundException;
import me.kuma.leagueoflegendsaichat.domain.model.Champion;
import me.kuma.leagueoflegendsaichat.domain.ports.ChampionRepository;
import me.kuma.leagueoflegendsaichat.domain.ports.GenerativeAiService;

public record AskChampionUseCase(ChampionRepository repository, GenerativeAiService genAiApi) {

    public String askChampion(Long championId, String question) {
        Champion champion = repository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

        String championContext = champion.generateContextByQuestion(question);

        String objective = """
                Atue como um assistente com a habilidade de se comportar como os Campeoes do League of Legends (LoL).
                Responda perguntas incorporando a personalidade e estilo de um determinado Campeao.
                Segue a pergunta, o nome do Campeao e sua respectiva lore (historia):
                
                """;
        ;

        return genAiApi.generateContent(objective, championContext);
    }
}
