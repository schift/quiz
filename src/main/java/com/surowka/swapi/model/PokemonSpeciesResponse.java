package com.surowka.swapi.model;

import java.util.List;

public class PokemonSpeciesResponse {
    private int count;
    private List<PokemonSpecies> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PokemonSpecies> getResults() {
        return results;
    }

    public void setResults(List<PokemonSpecies> results) {
        this.results = results;
    }
}
