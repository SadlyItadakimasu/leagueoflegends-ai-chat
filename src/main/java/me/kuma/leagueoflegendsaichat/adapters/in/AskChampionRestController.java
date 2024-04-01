package me.kuma.leagueoflegendsaichat.adapters.in;

import me.kuma.leagueoflegendsaichat.application.AskChampionUseCase;
import me.kuma.leagueoflegendsaichat.domain.ports.ChampionRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/champion")
public record AskChampionRestController(AskChampionUseCase useCase) {

    @PostMapping("/{championId}/ask")
    public AskChampionResponse askChampion(@PathVariable Long championId, @RequestBody AskChampionRequest request) {

        String answer = useCase.askChampion(championId, request.question());

        return new AskChampionResponse(answer);
    }

    public record AskChampionRequest(String question){}
    public record AskChampionResponse(String response){}
}
