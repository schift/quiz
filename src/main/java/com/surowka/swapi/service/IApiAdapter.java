package com.surowka.swapi.service;

import java.util.List;

public interface IApiAdapter {

    public List<String> getAllStarWarsSpecies();
    public List<String> getAllPokemonSpecies();
}
