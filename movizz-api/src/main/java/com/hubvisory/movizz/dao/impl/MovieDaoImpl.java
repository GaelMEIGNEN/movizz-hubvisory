package com.hubvisory.movizz.dao.impl;

import com.hubvisory.movizz.bean.Movie;
import com.hubvisory.movizz.dao.DAOFactory;
import com.hubvisory.movizz.dao.MovieDao;
import com.hubvisory.movizz.utils.deserializer.MovieDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static com.hubvisory.movizz.utils.Tools.concatenateUriWithTMDBApiKey;

@Component
public class MovieDaoImpl implements MovieDao {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);


    private DAOFactory daoFactory;
    private MovieDeserializer movieDeserializer;

    private WebClient tmdbApi;

    public MovieDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.movieDeserializer = new MovieDeserializer();

    }

    @Override
    public Movie[] getMoviesFromTMDBAPI(List<String> criterias, String url, String uri, String key) {
        tmdbApi = WebClient.create(url);
        String uriAPI = concatenateUriWithTMDBApiKey(uri, key, criterias);
        Flux<String> apiRequest = tmdbApi.get().uri(uriAPI).retrieve().bodyToFlux(String.class).log();
        String result = apiRequest.blockLast(REQUEST_TIMEOUT);
        return movieDeserializer.moviesDeserialize(result);
    }
}
