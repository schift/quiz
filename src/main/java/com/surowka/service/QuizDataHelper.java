package com.surowka.service;

import com.surowka.util.Constatns;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuizDataHelper {

    public Map<String, String> prepareData(List<String> allStarWarsSpeciesList, List<String> allPokemonSpeciesList) {

        Collections.shuffle(allStarWarsSpeciesList);
        Collections.shuffle(allPokemonSpeciesList);

        int iterStarWars = 8;
        int iterPokemon = 8;
        Map<String, String> restData = new HashMap<>();
        for(int i = 1; i <= 15; i++) {
            if (i % 2 == 0)
                restData.put(allStarWarsSpeciesList.get(--iterStarWars), Constatns.STARWARS);
            else
                restData.put(allPokemonSpeciesList.get(--iterPokemon), Constatns.POKEMAN);

        }
        return restData;
    }
}
