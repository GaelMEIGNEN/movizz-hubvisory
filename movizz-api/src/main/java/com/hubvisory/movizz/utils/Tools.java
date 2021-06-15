package com.hubvisory.movizz.utils;

import java.util.List;

public class Tools {

    /**
     * Concatenates the URI the method calling is trying to access with the API key and an optional list of parameters
     * If the URI contains "{", this method will use the first parameters to set the id inside the URI, and when there is no more "{id}" to set, the last parameters are written as query parameters
     * at the end of the URI
     *
     * @param uriApi     URI the method calling is trying to access
     * @param parameters Optional list of String for URI parameters
     * @return If one of these string is null, we return null
     */
    public static String concatenateUriWithTMDBApiKey(String uriApi, String key, List<String> parameters) {
        String finalUri = "";

        if (key != null && uriApi != null) {
            if (!key.equals("") && !uriApi.equals("")) {
                //If an id is needed inside the URI, we put the first parameter in the list as the id
                if (parameters != null) {
                    if (!parameters.isEmpty()) {
                        for (int i = 0; i < parameters.size(); i++) {
                            int locationIdFirstBracket = uriApi.indexOf("{");
                            int locationIdSecondBracket = uriApi.indexOf("}");
                            if (locationIdFirstBracket != -1 && locationIdSecondBracket != -1) {
                                finalUri = uriApi.substring(0, locationIdFirstBracket) + uriApi.replaceFirst(".*\\{.*\\}.*", parameters.get(i)) + uriApi.substring(locationIdSecondBracket+1);
                                parameters.remove(i);
                            }
                        }
                    }
                    if (finalUri.equals("")) {
                        finalUri = uriApi.concat("?api_key=" + key);
                    } else {
                        finalUri = finalUri.concat("?api_key=" + key);
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
        return finalUri;
    }

    /**
     * Gets a random object from an array of object
     *
     * @param objects The table of objects
     * @return A randomly chosen object from the list given
     */
    public static Object getRandomObject(Object[] objects) {
        if (objects != null) {
            if (objects.length > 0) {
                return objects[(int) (Math.random() * objects.length-1)];
            }
        }
        return null;
    }
}
