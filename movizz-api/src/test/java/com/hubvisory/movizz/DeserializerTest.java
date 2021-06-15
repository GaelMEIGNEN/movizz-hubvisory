package com.hubvisory.movizz;

import com.hubvisory.movizz.bean.Movie;
import com.hubvisory.movizz.bean.Person;
import com.hubvisory.movizz.utils.deserializer.ActorDeserializer;
import com.hubvisory.movizz.utils.deserializer.MovieDeserializer;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeserializerTest extends TestCase {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new TestSuite(DeserializerTest.class));
    }

    private ActorDeserializer actorDeserializer;
    private MovieDeserializer movieDeserializer;



    @Test
    public void testActorDeserializer(){
        actorDeserializer = new ActorDeserializer();
        //Test case 1 : one actor to deserialize OK
        String toDeserialize = "{\n" +
                            "    \"id\": 8078,\n" +
                            "    \"cast\": [\n" +
                            "        {\n" +
                            "            \"adult\": false,\n" +
                            "            \"gender\": 1,\n" +
                            "            \"id\": 10205,\n" +
                            "            \"known_for_department\": \"Acting\",\n" +
                            "            \"name\": \"Sigourney Weaver\",\n" +
                            "            \"original_name\": \"Sigourney Weaver\",\n" +
                            "            \"popularity\": 8.087,\n" +
                            "            \"profile_path\": \"/sHWCLx54yLtaFtppp5ADjAsrWIc.jpg\",\n" +
                            "            \"cast_id\": 1,\n" +
                            "            \"character\": \"Ripley 8\",\n" +
                            "            \"credit_id\": \"52fe4492c3a36847f809d98b\",\n" +
                            "            \"order\": 0\n" +
                            "        }" +
                            "     ]" +
                            " }";

        Person[] actors = actorDeserializer.actorsDeserialize(toDeserialize);
        Person actorExpected = new Person(false
                , 1
                , Integer.toUnsignedLong(10205)
                , "Acting"
                , "Sigourney Weaver"
                , "Sigourney Weaver"
                , 8.087
                , "https://image.tmdb.org/t/p/w200/sHWCLx54yLtaFtppp5ADjAsrWIc.jpg"
                , 1
                , "Ripley 8"
                , "52fe4492c3a36847f809d98b"
                , 0);
        String toStringActorActual = actors[0].toString();
        String toStringActorExpected  = actorExpected.toString();
        Assertions.assertEquals(toStringActorExpected, toStringActorActual);

        //Test case 2 : two actors to deserialize OK
        toDeserialize = "{\n" +
                        "    \"id\": 8078,\n" +
                        "    \"cast\": [\n" +
                        "        {\n" +
                        "            \"adult\": false,\n" +
                        "            \"gender\": 1,\n" +
                        "            \"id\": 10205,\n" +
                        "            \"known_for_department\": \"Acting\",\n" +
                        "            \"name\": \"Sigourney Weaver\",\n" +
                        "            \"original_name\": \"Sigourney Weaver\",\n" +
                        "            \"popularity\": 8.087,\n" +
                        "            \"profile_path\": \"/sHWCLx54yLtaFtppp5ADjAsrWIc.jpg\",\n" +
                        "            \"cast_id\": 1,\n" +
                        "            \"character\": \"Ripley 8\",\n" +
                        "            \"credit_id\": \"52fe4492c3a36847f809d98b\",\n" +
                        "            \"order\": 0\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"adult\": false,\n" +
                        "            \"gender\": 1,\n" +
                        "            \"id\": 1920,\n" +
                        "            \"known_for_department\": \"Acting\",\n" +
                        "            \"name\": \"Winona Ryder\",\n" +
                        "            \"original_name\": \"Winona Ryder\",\n" +
                        "            \"popularity\": 8.747,\n" +
                        "            \"profile_path\": \"/5yteOSY2lgGOgSWmRTlxqfY59MS.jpg\",\n" +
                        "            \"cast_id\": 5,\n" +
                        "            \"character\": \"Annalee Call\",\n" +
                        "            \"credit_id\": \"52fe4492c3a36847f809d9a1\",\n" +
                        "            \"order\": 1\n" +
                        "        }" +
                        "    ]" +
                        "}";

        actors = actorDeserializer.actorsDeserialize(toDeserialize);
        Person actorExpected2 = new Person(false
                , 1
                , Integer.toUnsignedLong(1920)
                , "Acting"
                , "Winona Ryder"
                , "Winona Ryder"
                , 8.747
                , "https://image.tmdb.org/t/p/w200/5yteOSY2lgGOgSWmRTlxqfY59MS.jpg"
                , 5
                , "Annalee Call"
                , "52fe4492c3a36847f809d9a1"
                , 1);// [2] in list


        toStringActorActual = actors[0].toString();
        Assertions.assertEquals(toStringActorExpected, toStringActorActual);

        toStringActorActual = actors[1].toString();
        toStringActorExpected  = actorExpected2.toString();
        Assertions.assertEquals(toStringActorExpected, toStringActorActual);
    }

    @Test
    public void testMovieDeserializer(){
        movieDeserializer = new MovieDeserializer();
        //Test case 1 : one movie to deserialize OK
        String toDeserialize = "{\n" +
                               "    \"page\": 1,\n" +
                               "    \"results\": [\n" +
                               "        {\n" +
                               "            \"adult\": false,\n" +
                               "            \"backdrop_path\": \"/8ChCpCYxh9YXusmHwcE9YzP0TSG.jpg\",\n" +
                               "            \"genre_ids\": [\n" +
                               "                35,\n" +
                               "                80\n" +
                               "            ],\n" +
                               "            \"id\": 337404,\n" +
                               "            \"original_language\": \"en\",\n" +
                               "            \"original_title\": \"Cruella\",\n" +
                               "            \"overview\": \"In 1970s London amidst the punk rock revolution, a young grifter named Estella is determined to make a name for herself with her designs. She befriends a pair of young thieves who appreciate her appetite for mischief, and together they are able to build a life for themselves on the London streets. One day, Estella’s flair for fashion catches the eye of the Baroness von Hellman, a fashion legend who is devastatingly chic and terrifyingly haute. But their relationship sets in motion a course of events and revelations that will cause Estella to embrace her wicked side and become the raucous, fashionable and revenge-bent Cruella.\",\n" +
                               "            \"popularity\": 4774.831,\n" +
                               "            \"poster_path\": \"/rTh4K5uw9HypmpGslcKd4QfHl93.jpg\",\n" +
                               "            \"title\": \"Cruella\",\n" +
                               "            \"video\": false,\n" +
                               "            \"vote_average\": 8.6,\n" +
                               "            \"vote_count\": 2664\n" +
                               "       }" +
                               "   ]" +
                               "}" ; //JSON from TMDB API

        Movie[] movies = movieDeserializer.moviesDeserialize(toDeserialize);

        Movie movieExpected = new Movie(false
                , "/8ChCpCYxh9YXusmHwcE9YzP0TSG.jpg"
                , null
                , 337404
                , "en"
                , "Cruella"
                , "In 1970s London amidst the punk rock revolution, a young grifter named Estella is determined to make a name for herself with her designs. She befriends a pair of young thieves who appreciate her appetite for mischief, and together they are able to build a life for themselves on the London streets. One day, Estella’s flair for fashion catches the eye of the Baroness von Hellman, a fashion legend who is devastatingly chic and terrifyingly haute. But their relationship sets in motion a course of events and revelations that will cause Estella to embrace her wicked side and become the raucous, fashionable and revenge-bent Cruella."
                , 4774.831
                , "https://image.tmdb.org/t/p/w200/rTh4K5uw9HypmpGslcKd4QfHl93.jpg"
                , null
                , "Cruella"
                , false
                , 8.6
                , 2664
                , null);

        String toStringMovieActual = movies[0].toString();
        String toStringMovieExpected  = movieExpected.toString();
        Assertions.assertEquals(toStringMovieExpected, toStringMovieActual);

        //Test case 2 : two movies to deserialize OK
        toDeserialize = "{\n" +
                        "    \"page\": 1,\n" +
                        "    \"results\": [\n" +
                        "        {\n" +
                        "            \"adult\": false,\n" +
                        "            \"backdrop_path\": \"/8ChCpCYxh9YXusmHwcE9YzP0TSG.jpg\",\n" +
                        "            \"id\": 337404,\n" +
                        "            \"original_language\": \"en\",\n" +
                        "            \"original_title\": \"Cruella\",\n" +
                        "            \"overview\": \"In 1970s London amidst the punk rock revolution, a young grifter named Estella is determined to make a name for herself with her designs. She befriends a pair of young thieves who appreciate her appetite for mischief, and together they are able to build a life for themselves on the London streets. One day, Estella’s flair for fashion catches the eye of the Baroness von Hellman, a fashion legend who is devastatingly chic and terrifyingly haute. But their relationship sets in motion a course of events and revelations that will cause Estella to embrace her wicked side and become the raucous, fashionable and revenge-bent Cruella.\",\n" +
                        "            \"popularity\": 4774.831,\n" +
                        "            \"poster_path\": \"/rTh4K5uw9HypmpGslcKd4QfHl93.jpg\",\n" +
                        "            \"title\": \"Cruella\",\n" +
                        "            \"video\": false,\n" +
                        "            \"vote_average\": 8.6,\n" +
                        "            \"vote_count\": 2664\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"adult\": false,\n" +
                        "            \"backdrop_path\": \"/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg\",\n" +
                        "            \"id\": 460465,\n" +
                        "            \"original_language\": \"en\",\n" +
                        "            \"original_title\": \"Mortal Kombat\",\n" +
                        "            \"overview\": \"Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.\",\n" +
                        "            \"popularity\": 1845.381,\n" +
                        "            \"poster_path\": \"/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg\",\n" +
                        "            \"title\": \"Mortal Kombat\",\n" +
                        "            \"video\": false,\n" +
                        "            \"vote_average\": 7.5,\n" +
                        "            \"vote_count\": 2986\n" +
                        "        }" +
                        "    ]" +
                        " }";// String from TMDB API with 2+ movies
        movies = movieDeserializer.moviesDeserialize(toDeserialize);

        Movie movieExpected2 = new Movie(false
                , "/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg"
                , null
                , 460465
                , "en"
                , "Mortal Kombat"
                , "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."
                , 1845.381
                , "https://image.tmdb.org/t/p/w200/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg"
                , null
                , "Mortal Kombat"
                , false
                , 7.5
                , 2986
                , null);// [1] in list;

        toStringMovieActual = movies[0].toString();
        Assertions.assertEquals(toStringMovieExpected, toStringMovieActual);

        toStringMovieActual = movies[1].toString();
        toStringMovieExpected  = movieExpected2.toString();
        Assertions.assertEquals(toStringMovieExpected, toStringMovieActual);
    }
}
