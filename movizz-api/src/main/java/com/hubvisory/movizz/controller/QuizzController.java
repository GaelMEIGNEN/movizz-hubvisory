package com.hubvisory.movizz.controller;

import com.hubvisory.movizz.bean.Movie;
import com.hubvisory.movizz.bean.Person;
import com.hubvisory.movizz.config.ApplicationProperties;
import com.hubvisory.movizz.dao.DAOFactory;
import com.hubvisory.movizz.dao.MovieDao;
import com.hubvisory.movizz.dao.PersonDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.hubvisory.movizz.bean.Person.fromActorTableToActorList;
import static com.hubvisory.movizz.bean.Person.isActorStarringInMovie;
import static com.hubvisory.movizz.utils.Tools.getRandomObject;

@RestController
@RequestMapping("quizz")
public class QuizzController {

    @Autowired
    private ApplicationProperties appProperties;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private DAOFactory daoFactory;
    private PersonDao personDao;
    private MovieDao movieDao;

    @PostConstruct
    public void init() {
        daoFactory = DAOFactory.getInstance();
        personDao = daoFactory.getPersonDao();
        movieDao = daoFactory.getMovieDao();
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
    public Mono<ResponseEntity<Map<String, String>>> createQuestion() {
        logger.info("Entering /quizz/question createQuestion method");

        Movie movieQuestion;
        Person actorQuestion;
        Map<String, String> dataQuestion = new HashMap<>();

        //We sort by popularity desc and we pick a random page between the most popular movies, with 20 results per page, it means questions can be about 1000 different movies
        ArrayList<String> criterias = new ArrayList<>();
        criterias.add("sort_by=popularity.desc");
        criterias.add("vote_count.gte=2500");
        criterias.add("page=" + (int) (Math.random() * (50) + 1));


        if (criterias.size() == 3) {
            try {
                // Return a movie by getting a list (~20) of films from TMDB API and picking one of them randomly
                logger.info("Calling API TMDB : " + appProperties.getDiscover_movie());
                movieQuestion = (Movie) getRandomObject(movieDao.getMoviesFromTMDBAPI(criterias, appProperties.getUrl(), appProperties.getDiscover_movie(), appProperties.getKey()));

                criterias.clear();
                criterias.add(String.valueOf(movieQuestion.getId()));

                //We gather the actors who star in the film we will send, and then we pick one of them randomly
                logger.info("Calling API TMDB : " + appProperties.getGet_credits_from_movie());
                Person[] actorsFromMovie = personDao.getActorsFromMovieTMDBAPI(criterias, appProperties.getUrl(), appProperties.getGet_credits_from_movie(), appProperties.getKey());

                movieQuestion.setActors_in_movie(fromActorTableToActorList(actorsFromMovie));
                actorQuestion = (Person) getRandomObject(actorsFromMovie);

                //There is a lot of actors and at least as much movies, so to increase chance to get an actor who stars in the movie we've got, we randomize this part :
                //If we get a number <5, the actor picked from the API doesn't star in the movie
                if ((int) (Math.random() * (10)) < 5) {
                    criterias.clear();
                    criterias.add("sort_by=popularity.desc");
                    criterias.add("vote_count.gte=2500");
                    criterias.add("page=" + (int) (Math.random() * (50) + 1));
                    // Return a movie by getting a list (~20) of films from TMDB API and picking one of them randomly (it can be identical as movieToSend)
                    logger.info("Calling API TMDB : " + appProperties.getDiscover_movie());
                    Movie movieToGetActorFrom = (Movie) getRandomObject(movieDao.getMoviesFromTMDBAPI(criterias, appProperties.getUrl(), appProperties.getDiscover_movie(), appProperties.getKey()));

                    criterias.clear();
                    criterias.add(String.valueOf(movieToGetActorFrom.getId()));
                    logger.info("Calling API TMDB : " + appProperties.getGet_credits_from_movie());
                    actorQuestion = (Person) getRandomObject(personDao.getActorsFromMovieTMDBAPI(criterias, appProperties.getUrl(), appProperties.getGet_credits_from_movie(), appProperties.getKey()));
                }

                dataQuestion.put("question", "Did " + actorQuestion.getName() + " star in " + movieQuestion.getTitle() + " ?");
                dataQuestion.put("answer", isActorStarringInMovie(actorQuestion, movieQuestion));
                dataQuestion.put("url_poster", movieQuestion.getPoster_path());
                dataQuestion.put("url_profile", actorQuestion.getProfile_path());
            } catch (Exception e) {
                logger.error("Error in QuizzController::createQuestion :", e);
            }
        }

        logger.info("Exiting /quizz/question createQuestion method");
        return Mono.just(ResponseEntity.ok().header("Access-Control-Allow-Origin", "*").body(dataQuestion));
    }

}
