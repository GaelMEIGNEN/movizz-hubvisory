package com.hubvisory.movizz.controller;

import com.hubvisory.movizz.bean.Movie;
import com.hubvisory.movizz.bean.Person;
import com.hubvisory.movizz.config.ApplicationProperties;
import com.hubvisory.movizz.utils.deserializer.ActorDeserializer;
import com.hubvisory.movizz.utils.deserializer.MovieDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping("quizz")
public class QuizzController {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApplicationProperties appProperties;
    @Autowired
    private MovieDeserializer movieDeserializer = new MovieDeserializer();
    @Autowired
    private ActorDeserializer actorDeserializer = new ActorDeserializer();

    private WebClient tmdbApi;

    @PostConstruct
    public void init() {
        tmdbApi = WebClient.create(appProperties.getUrl());
    }

    /**
     * Create a question  like 'Did XXX star in YYY', where XXX is the name of an actor and YYY is the name of a movie
     *
     * @return A map where the key is a String and the value is also a String
     *         4 keys are returned :
     *                              question    : value = question which will be asked to the user
     *                              answer      : value = answer expected to this question
     *                              url_poster  : value = url of the movie's poster
     *                              url_profile : value = url of the actor's picture
     */
    @GetMapping(value = "/question")
    public Map<String, String> createQuestion() {
        logger.info("Entering /quizz/question createQuestion method");

        Movie movieQuestion;
        Person actorQuestion;
        Map<String, String> response = new HashMap<>();

        //We sort by popularity desc and we pick a random page between the most popular movies, with 20 results per page, it means questions can be about 1000 different movies
        ArrayList<String> criterias = new ArrayList<>();
        criterias.add("sort_by=popularity.desc");
        criterias.add("vote_count.gte=2500");
        criterias.add("page=" + (int) (Math.random() * (50) + 1));

        String uriWithParameters = concatenateUriWithTMDBApiKey(appProperties.getDiscover_movie(), criterias);

        if (uriWithParameters != null) {
            try {
                // Return a movie by getting a list (~20) of films from TMDB API and picking one of them randomly
                movieQuestion = (Movie) getRandomObject(getMoviesFromTMDBApi(uriWithParameters));

                //There is a lot of actors and at least as much movies, so to increase chance to get an actor who stars in the movie we've got, we randomize this part :
                //If we get a number <5, the actor picked from the API stars in the movie; otherwise, the actor doesn't star in the movie

                criterias.clear();
                criterias.add(String.valueOf(movieQuestion.getId()));
                uriWithParameters = concatenateUriWithTMDBApiKey(appProperties.getGet_credits_from_movie(), criterias);
                //We gather the actors who star in the film we will send, and then we pick one of them randomly
                Person[] actorsFromMovie = getActorsFromMovieFromTMDBApi(uriWithParameters);
                List<Person> actorsInMovie = fromActorTableToActorList(actorsFromMovie);

                movieQuestion.setActors_in_movie(actorsInMovie);
                actorQuestion = (Person) getRandomObject(actorsFromMovie);

                if ((int) (Math.random() * (10)) < 5) {

                    uriWithParameters = concatenateUriWithTMDBApiKey(appProperties.getDiscover_movie(), criterias);
                    // Return a movie by getting a list (~20) of films from TMDB API and picking one of them randomly (it can be identical as movieToSend)
                    Movie movieToGetActorFrom = (Movie) getRandomObject(getMoviesFromTMDBApi(uriWithParameters));

                    criterias.clear();
                    criterias.add(String.valueOf(movieToGetActorFrom.getId()));
                    uriWithParameters = concatenateUriWithTMDBApiKey(appProperties.getGet_credits_from_movie(), criterias);

                    actorQuestion = (Person) getRandomObject(getActorsFromMovieFromTMDBApi(uriWithParameters));
                }

                response.put("question", "Did " + actorQuestion.getName() + " star in " + movieQuestion.getTitle() + " ?");
                response.put("answer", isActorStarringInMovie(actorQuestion, movieQuestion));
                response.put("url_poster", movieQuestion.getPoster_path());
                response.put("url_profile", actorQuestion.getProfile_path());
            } catch (Exception e) {
                logger.error("Error in QuizzController::createQuestion :", e);
            }
        }
        logger.info("Exiting /quizz/question createQuestion method");
        return response;
    }

    /**
     * Concatenates the URI the method calling is trying to access with the API key and an optional list of parameters
     * If the URI contains "{", this method will use the first parameters to set the id inside the URI, and when there is no more "{id}" to set, the last parameters are written as query parameters
     * at the end of the URI
     *
     * @param uriApi     URI the method calling is trying to access
     * @param parameters Optional list of String for URI parameters
     * @return If one of these string is null, we return null
     */
    private String concatenateUriWithTMDBApiKey(String uriApi, List<String> parameters) {
        String finalUri = "";

        if (appProperties.getKey() != null && uriApi != null) {
            if (!appProperties.getKey().equals("") && !uriApi.equals("")) {
                //If an id is needed inside the URI, we put the first parameter in the list as the id
                if (parameters != null) {
                    if (!parameters.isEmpty()) {
                        for (int i = 0; i < parameters.size(); i++) {
                            int locationIdFirstBracket = uriApi.indexOf("{");
                            int locationIdSecondBracket = uriApi.indexOf("}");
                            if (locationIdFirstBracket != -1 && locationIdSecondBracket != -1) {
                                finalUri = uriApi.substring(0, locationIdFirstBracket) + uriApi.replaceFirst(".*\\{.*\\}.*", parameters.get(i)) + uriApi.substring(locationIdSecondBracket+1);

                            }
                            parameters.remove(i);
                        }
                    }
                    if (finalUri.equals("")) {
                        finalUri = uriApi.concat("?api_key=" + appProperties.getKey());
                    } else {
                        finalUri = finalUri.concat("?api_key=" + appProperties.getKey());
                    }

                    //When every id is filled from the list of parameters, we put the remaining parameters at then end of the URL, in the query parameters
                    if (!parameters.isEmpty()) {
                        for (String parameter : parameters) {
                            if (!parameter.equals("")) {
                                finalUri = finalUri.concat("&" + parameter);
                            }
                        }
                    }
                }
            }
        }
        logger.info("Calling API TMDB: " + finalUri);
        return finalUri;
    }

    /**
     * Gets a random object from an array of object
     *
     * @param objects The table of objects
     * @return A randomly chosen object from the list given
     */
    private Object getRandomObject(Object[] objects) {
        if (objects != null) {
            if (objects.length > 0) {
                return objects[(int) (Math.random() * objects.length-1)];
            }
        }
        return null;
    }

    /**
     * This method call TMDB API and make an array of movies from the Json answered by the API
     *
     * @param uri TMDB API URI to call
     * @return A table of movies
     */
    private Movie[] getMoviesFromTMDBApi(String uri) {
        Flux<String> apiRequest = tmdbApi.get().uri(uri).retrieve().bodyToFlux(String.class).log();
        String result = apiRequest.blockLast(REQUEST_TIMEOUT);
        return movieDeserializer.moviesDeserialize(result);
    }

    /**
     * Calls TMDB API and make an array of actors from the Json answered by the API
     *
     * @param uri TMDB API URI to call
     * @return A table of movies
     */
    private Person[] getActorsFromMovieFromTMDBApi(String uri) {
        Flux<String> apiRequest = tmdbApi.get().uri(uri).retrieve().bodyToFlux(String.class).log();
        String result = apiRequest.blockLast(REQUEST_TIMEOUT);
        return actorDeserializer.actorsDeserialize(result);
    }

    /**
     * Finds if the actor in parameter stars in the movie in parameter
     *
     * @param actor
     * @param movie
     * @return "YES" if the actor stars in the movie, "NO" otherwise
     */
    private String isActorStarringInMovie(Person actor, Movie movie) {
        if (actor != null && movie != null) {
            if (movie.getActors_in_movie() != null) {
                if (movie.getActors_in_movie().contains(actor)) {
                    return "YES";
                } else {
                    return "NO";
                }
            }
        }
        return "";
    }

    /**
     * Takes all actors from an array of actor and put them in a list of actor
     *
     * @param actors An array of actor
     * @return A list of actor
     */
    private List<Person> fromActorTableToActorList (Person[] actors) {
        List<Person> actorsList = new ArrayList<>();
        if (actors != null) {
            for (Person actor:
                 actors) {
                actorsList.add(actor);
            }
        }
        return actorsList;
    }

}
