package com.hubvisory.movizz.servlets;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hubvisory.movizz.beans.Actor;
import com.hubvisory.movizz.beans.Movie;
import com.hubvisory.movizz.utils.deserializer.MovieDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quizz")
public class QuizzController extends HttpServlet {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    @Value("${tmdb.api.url}")
    String TMDBApiUrl;
    @Value("${tmdb.api.key}")
    String TMDBApiKey;
    @Value("${tmdb.api.uri.discover_movie}")
    String TMDBApiUriDiscoverMovie;

    private WebClient tmdbApi;

    @PostConstruct
    public void init(){
        tmdbApi = WebClient.create(TMDBApiUrl);
    }

    /**
     * Create a question  like 'Did XXX stars in YYY', with XXX the name of an actor and YYY the name of a movie
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     *///TODO : randomize data gathering from TMDB API : if 1, return an actor who stars in the movie, if 2-9, return another actor
    @GetMapping(name = "/question")
    protected Map<Movie, Actor> createQuestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<String> criterias = new ArrayList<>();
        //We sort by popularity desc and we pick a random page between the most popular movies, with 20 results per page, it means questions can be about 1000 different movies
        criterias.add("sort_by=popularity.desc");
        criterias.add("page=" + (int) (Math.random() * (50) + 1));

        String uriWithParameters = concatenateUriWithTMDBApiKey(TMDBApiUriDiscoverMovie, criterias);

        System.out.println(uriWithParameters);
        if (uriWithParameters != null){
            try {
                System.out.println(uriWithParameters);
                Flux<String> apiRequest = tmdbApi.get().uri(uriWithParameters).retrieve().bodyToFlux(String.class).log();
                System.out.println("test1");
                String result = apiRequest.blockLast(REQUEST_TIMEOUT);
                //System.out.println(result);

                ObjectMapper mapper = new ObjectMapper();
                SimpleModule module = new SimpleModule("MovieDeserializer", new Version(1, 0, 0 , null, null, null));
                module.addDeserializer(Movie[].class, new MovieDeserializer());
                mapper.registerModule(module);
                Movie[] movies = mapper.readValue(result, Movie[].class);
                //Collection<Movie> movies = Arrays.stream(objects).map(object -> mapper.convertValue(object, Movie.class)).collect(Collectors.toList());

                //System.out.println(objects.toString());
//                for (Movie movie:
//                     movies) {
//                    System.out.println(movie.getOriginial_title());
//                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        return null;
    }

    /*private Map<Movie, Actor> translateFromJsonToMap(String strToTranslate){
        jsonReader.readJson()
    }*/


    /**
     * This method tests if the TMDBApiKey written in the file application.properties is still working.
     *
     * @return boolean True if the API key is valid, false otherwise
     */
    /*private boolean isValidTMDBApiKey(){
        if (TMDBApiKey != null) {
            if (!TMDBApiKey.equals("")) {
                String result = template.getForObject(TMDBApiUrl, String.class);

                if (!result.contains("Invalid API key: You must be granted a valid key")){
                    return true;
                }
            }
        }
        return false;
    }*/

    /**
     * This method concatenate the URI the method calling is trying to access with the API key and an optional list of parameters
     *
     * @param uriApi URI the method calling is trying to access
     * @param parameters Optional list of String for URI parameters
     * @return If one of these string is null, we return null
     */
    private String concatenateUriWithTMDBApiKey(String uriApi, List<String> parameters){
        String finalUri = null;
        if (TMDBApiKey != null && uriApi != null) {
            if (!TMDBApiKey.equals("") && !uriApi.equals("")){
                finalUri = uriApi.concat("?api_key="+TMDBApiKey);
                if (parameters != null) {
                    if (!parameters.isEmpty()) {
                        for (String parameter : parameters){
                            if (!parameter.equals("")) {
                                finalUri = finalUri.concat("&"+parameter);
                            }
                        }
                    }
                }
                return finalUri;
            }
        }
        return finalUri;
    }


}
