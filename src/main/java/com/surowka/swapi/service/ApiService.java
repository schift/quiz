package com.surowka.swapi.service;

import com.surowka.swapi.model.PokemonSpeciesResponse;
import com.surowka.swapi.model.StarWarsSpeciesResponse;
import com.surowka.util.Constatns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class ApiService implements IApiAdapter {

    private RestTemplate restTemplate;
    private HttpEntity<String> requestEntity;
    private static final Logger log = LoggerFactory.getLogger(ApiService.class);

    @Autowired
    public ApiService(RestTemplate restTemplate, HttpEntity<String> requestHeaders) {
        this.restTemplate = restTemplate;
        this.requestEntity = requestHeaders;
    }

    @Override
    public List<String> getAllStarWarsSpecies() {
        String uri = String.join("/",Constatns.STARWARS_BASE_URL, "species/");
        log.info("Calling uri: {}", uri);

        ResponseEntity<StarWarsSpeciesResponse> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, StarWarsSpeciesResponse.class);

        Optional.of(response).orElseThrow(() -> new IllegalArgumentException());
        if (response.getStatusCode() != HttpStatus.OK)
            throw new RestClientException("Rest StarWars service error, code: " + response.getStatusCode());

        return response.getBody().getResults().stream().map(p -> p.getName()).collect(Collectors.toList());
   }

    @Override
    public List<String> getAllPokemonSpecies() {
        String uri = String.join("/",Constatns.POKEMON_BASE_URL, "pokemon-species/");
        log.info("Calling uri: {}", uri);

        ResponseEntity<PokemonSpeciesResponse> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, PokemonSpeciesResponse.class);

        Optional.of(response).orElseThrow(() -> new IllegalArgumentException());
        if (response.getStatusCode() != HttpStatus.OK)
            throw new RestClientException("Rest Pokemon service error, code: " + response.getStatusCode());

        return response.getBody().getResults().stream().map(p -> p.getName()).collect(Collectors.toList());
    }
}
