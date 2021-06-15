package com.hubvisory.movizz;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.hubvisory.movizz.utils.Tools.concatenateUriWithTMDBApiKeyAndParams;

public class ToolsTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(new TestSuite(ToolsTest.class));
    }

    @Test
    public void testConcatenateUriWithTMDBApiKeyAndParams() {
        //Test case 1 : discover movie OK
        String uri = "/discover/movie";
        String key = "12ds3q4fd5sc123xw";
        List<String> parameters = new ArrayList<>();
        parameters.add("sort_by=popularity.desc");
        parameters.add("vote_count.gte=2500");

        Assertions.assertEquals("/discover/movie?api_key=12ds3q4fd5sc123xw&sort_by=popularity.desc&vote_count.gte=2500", concatenateUriWithTMDBApiKeyAndParams(uri, key, parameters));

        //Test case 2 : movie credits OK
        uri = "/movie/{movie_id}/credits";
        parameters.clear();
        parameters.add("1524");

        Assertions.assertEquals("/movie/1524/credits?api_key=12ds3q4fd5sc123xw", concatenateUriWithTMDBApiKeyAndParams(uri, key, parameters));

        //Test case 3 : discover movie KO
        parameters.clear();
        key = "";
        Assertions.assertNotEquals("/discover/movie?api_key=12ds3q4fd5sc123xw&sort_by=popularity.desc&vote_count.gte=2500", concatenateUriWithTMDBApiKeyAndParams(uri, key, parameters));

        //Test case 4 : movie credits KO

        Assertions.assertNotEquals("/movie/1524/credits?api_key=12ds3q4fd5sc123xw", concatenateUriWithTMDBApiKeyAndParams(uri, key, parameters));

    }
}
