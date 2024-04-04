package me.kuma.leagueoflegendsaichat.adapters.in;

import me.kuma.leagueoflegendsaichat.application.ListChampionsUseCase;
import me.kuma.leagueoflegendsaichat.domain.model.Champion;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/champion")
public record ChampionRestController(ListChampionsUseCase useCase) {

    @GetMapping
    public List<Champion> findAll(){

        return useCase.findAll();
    }


}
