package com.hubvisory.movizz.utils.deserializer;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hubvisory.movizz.bean.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MovieDeserializer extends StdDeserializer<Movie[]> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public MovieDeserializer(){
        this(null);
    }

    public MovieDeserializer(Class<?> c){
        super(c);
    }

    /**
     *  Deserialize the content of a Json file in an array of Movies
     *
     * @param jsonParser
     * @param deserializationContext
     * @return A table of Movies
     * @throws IOException
     */
    @Override
    public Movie[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec codec = jsonParser.getCodec();
        TreeNode treeNode = codec.readTree(jsonParser);
        try {
            TreeNode results = treeNode.get("results");
            Movie[] movies = new Movie[results.size()];
            for (int i = 0; i<results.size(); i++){
                Movie movie = new Movie();

                movie.setAdult(Boolean.getBoolean(results.get(i).get("adult").toString()));
                movie.setBackdrop_path(results.get(i).get("backdrop_path").toString().replaceAll("[\"\\].*[\\\"]]", ""));
                movie.setId(Long.parseLong(results.get(i).get("id").toString()));
                movie.setOriginal_language(results.get(i).get("original_language").toString().replaceAll("[\"\\].*[\\\"]]", ""));
                movie.setOriginal_title(results.get(i).get("original_title").toString().replaceAll("[\"\\].*[\\\"]]", ""));
                movie.setOverview(results.get(i).get("overview").toString().replaceAll("[\"\\].*[\\\"]]", ""));
                movie.setPopularity(Double.parseDouble(results.get(i).get("popularity").toString()));
                movie.setPoster_path(results.get(i).get("poster_path").toString().replaceAll("[\"\\].*[\\\"]]", ""));
                movie.setTitle(results.get(i).get("title").toString().replaceAll("[\"\\].*[\\\"]]", ""));
                movie.setVideo(Boolean.getBoolean(results.get(i).get("video").toString()));
                movie.setVote_average(Double.parseDouble(results.get(i).get("vote_average").toString()));
                movie.setVote_count(Integer.parseInt(results.get(i).get("vote_count").toString()));

                movies[i] = movie;
            }
            return movies;
        } catch (Exception e) {
            logger.error("Error in MovieDeserializer::deserialize :", e);
        }
        return null;
    }

    /**
     * This method use the class MovieDeserializer to deserialize the Json content from TMDB API.
     *
     * @param apiResults The Json content from TMDB API as a String
     * @return A list of deserialized movies
     */
    public Movie[] moviesDeserialize(String apiResults) {
        Movie[] movies = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule("MovieDeserializer", new Version(1, 0, 0, null, null, null));
            module.addDeserializer(Movie[].class, this);
            mapper.registerModule(module);
            movies = mapper.readValue(apiResults, Movie[].class);
        } catch (JsonMappingException e) {
            logger.error("Error in MovieDeserializer::moviesDeserialize :", e);
        } catch (JsonProcessingException e2) {
            logger.error("Error in MovieDeserializer::moviesDeserialize :", e2);
        }

        return movies;
    }


}
