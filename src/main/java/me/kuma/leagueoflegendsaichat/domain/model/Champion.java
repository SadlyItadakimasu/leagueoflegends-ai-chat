package me.kuma.leagueoflegendsaichat.domain.model;

public record Champion(
        Long id,
        String nome,
        String role,
        String lore,
        String imageUrl
) {
}
