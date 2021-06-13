package com.hubvisory.movizz.utils.deserializer;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hubvisory.movizz.bean.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ActorDeserializer extends StdDeserializer<Person[]> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ActorDeserializer(){
        this(null);
    }

    public ActorDeserializer(Class<?> c){
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
    public Person[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec codec = jsonParser.getCodec();
        TreeNode treeNode = codec.readTree(jsonParser);
        try {
            TreeNode results = treeNode.get("cast");

            Person[] actors = new Person[results.size()];
            int i = 0;
            int j = 0;
            while (i<results.size()) {
                if (results.get(i).get("known_for_department").toString().contains("Acting")){

                    Person actor = new Person();

                    actor.setAdult(Boolean.getBoolean(results.get(i).get("adult").toString()));
                    actor.setGender(Integer.parseInt(results.get(i).get("gender").toString()));
                    actor.setId(Long.parseLong(results.get(i).get("id").toString()));
                    actor.setKnown_for_department(results.get(i).get("known_for_department").toString().replaceAll("[\"\\].*[\\\"]]", ""));
                    actor.setName(results.get(i).get("name").toString().replaceAll("[\"\\].*[\\\"]]", ""));
                    actor.setOriginal_name(results.get(i).get("original_name").toString().replaceAll("[\"\\].*[\\\"]]", ""));
                    actor.setPopularity(Double.parseDouble(results.get(i).get("popularity").toString()));
                    actor.setProfile_path(results.get(i).get("profile_path").toString().substring(2,results.get(i).get("profile_path").toString().length()-1));

                    actors[j] = actor;
                    j++;
                }
                i++;
            }
            return actors;
        } catch (Exception e) {
            logger.error("Error in ActorDeserializer::deserialize :",e);
        }
        return null;
    }

    /**
     * This method use the class MovieDeserializer to deserialize the Json content from TMDB API.
     *
     * @param apiResults The Json content from TMDB API as a String
     * @return A list of deserialized movies
     */
    public Person[] actorsDeserialize(String apiResults) {
        Person[] actors = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule("ActorDeserializer", new Version(1, 0, 0, null, null, null));
            module.addDeserializer(Person[].class, this);
            mapper.registerModule(module);
            actors = mapper.readValue(apiResults, Person[].class);
        } catch (JsonMappingException e) {
            logger.error("Error in ActorDeserializer::actorsDeserialize :", e);
        } catch (JsonProcessingException e2) {
            logger.error("Error in ActorDeserializer::actorsDeserialize :", e2);
        }

        return actors;
    }

}
