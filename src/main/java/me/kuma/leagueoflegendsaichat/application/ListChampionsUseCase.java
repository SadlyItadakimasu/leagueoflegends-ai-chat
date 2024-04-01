package me.kuma.leagueoflegendsaichat.application;

import me.kuma.leagueoflegendsaichat.domain.model.Champion;
import me.kuma.leagueoflegendsaichat.domain.ports.ChampionRepository;

import java.util.List;

public record ListChampionsUseCase(ChampionRepository repository) {

    public List<Champion> findAll() {
        return repository.findAll();
    }
}
