package me.kuma.leagueoflegendsaichat.domain.ports;

import me.kuma.leagueoflegendsaichat.domain.model.Champion;

import java.util.List;
import java.util.Optional;

public interface ChampionRepository {
    public List<Champion> findAll();

    public Optional<Champion> findById(Long id);
}
