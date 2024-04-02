package me.kuma.leagueoflegendsaichat.domain.ports;

public interface GenerativeAiService {

    String generateContent(String objective, String context);
}
