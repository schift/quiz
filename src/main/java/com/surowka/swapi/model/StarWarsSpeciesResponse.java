package com.surowka.swapi.model;

import java.util.List;

public class StarWarsSpeciesResponse {
    private int count;
    private List<StarWarsSpecies> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<StarWarsSpecies> getResults() {
        return results;
    }

    public void setResults(List<StarWarsSpecies> results) {
        this.results = results;
    }
}
