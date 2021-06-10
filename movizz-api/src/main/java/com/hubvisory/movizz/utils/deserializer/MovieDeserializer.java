package com.hubvisory.movizz.utils.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubvisory.movizz.beans.Movie;

import java.io.IOException;

public class MovieDeserializer extends StdDeserializer<Movie[]> {

    public MovieDeserializer(){
        this(null);
    }

    private MovieDeserializer(Class<?> c){
        super(c);
    }

    @Override
    public Movie[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec codec = jsonParser.getCodec();
        TreeNode treeNode = codec.readTree(jsonParser);
        System.out.println("test4545");
        try {
            TreeNode results = treeNode.get("results");

            Movie[] movies = new Movie[results.size()];
            for (int i = 0; i<=results.size(); i++){
                Movie movie = new Movie();

                movie.setAdult(results.get(i).get("adult").toString());
                movie.setBackdrop_path(results.get(i).get("backdrop_path").toString());
                movie.setId(Long.parseLong(results.get(i).get("id").toString()));
                movie.setOriginal_language(results.get(i).get("original_language").toString());
                movie.setOriginal_title(results.get(i).get("original_title").toString());
                movie.setOverview(results.get(i).get("overview").toString());
                movie.setPopularity(Double.parseDouble(results.get(i).get("popularity").toString()));
                movie.setPoster_path(results.get(i).get("poster_path").toString());
                movie.setTitle(results.get(i).get("title").toString());
                movie.setVideo(Boolean.getBoolean(results.get(i).get("video").toString()));
                movie.setVote_average(Integer.parseInt(results.get(i).get("vote_average").toString()));
                movie.setVote_count(Integer.parseInt(results.get(i).get("vote_count").toString()));
                movies[i] = movie;
            }
            System.out.println(results.get(1));

            System.out.println("test8080");
            return movies;

        } catch (Exception e) {
            System.out.println("test8083");
            System.out.println(e.getMessage());
        }
        System.out.println("test8084");
        return null;
    }


}
