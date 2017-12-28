package com.surowka.service;

import com.surowka.swapi.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Stream;

@Service
public class RestDataExecutor {

    @Autowired
    private ApiService apiService;
    @Autowired
    private QuizDataHelper quizDataHelper;

    private static final Logger log = LoggerFactory.getLogger(RestDataExecutor.class);
    private final ExecutorService pool = Executors.newFixedThreadPool(5);

    public Map<String, String> fetchRestData() throws InterruptedException, ExecutionException {

        log.info("Fetching data from services...");
        CompletableFuture<List<String>> taskGetAllStarWarsSpecies = CompletableFuture.supplyAsync(() ->
                apiService.getAllStarWarsSpecies(), pool);

        CompletableFuture<List<String>> taskGetAllPokemonSpecies = CompletableFuture.supplyAsync(() ->
                apiService.getAllPokemonSpecies(), pool);

        pool.shutdownNow();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        Stream.of(taskGetAllStarWarsSpecies, taskGetAllPokemonSpecies).map(CompletableFuture::join);

        return quizDataHelper.prepareData(taskGetAllStarWarsSpecies.get(), taskGetAllPokemonSpecies.get());
    }
}
