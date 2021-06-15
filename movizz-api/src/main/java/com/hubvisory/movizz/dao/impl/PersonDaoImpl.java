package com.hubvisory.movizz.dao.impl;

import com.hubvisory.movizz.bean.Person;
import com.hubvisory.movizz.dao.DAOFactory;
import com.hubvisory.movizz.dao.PersonDao;
import com.hubvisory.movizz.utils.deserializer.ActorDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static com.hubvisory.movizz.utils.Tools.concatenateUriWithTMDBApiKey;

@Component
public class PersonDaoImpl implements PersonDao {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);
    private DAOFactory daoFactory;
    private ActorDeserializer actorDeserializer;

    private WebClient tmdbApi;
    public PersonDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.actorDeserializer = new ActorDeserializer();
    }

    @Override
    public Person[] getActorsFromMovieTMDBAPI(List<String> criterias, String url, String uri, String key) {
        tmdbApi = WebClient.create(url);
        String uriAPI = concatenateUriWithTMDBApiKey(uri, key, criterias);
        Flux<String> apiRequest = tmdbApi.get().uri(uriAPI).retrieve().bodyToFlux(String.class).log();
        String result = apiRequest.blockLast(REQUEST_TIMEOUT);
        return actorDeserializer.actorsDeserialize(result);
    }

}
