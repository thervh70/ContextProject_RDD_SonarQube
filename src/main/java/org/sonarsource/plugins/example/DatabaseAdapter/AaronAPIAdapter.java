package org.sonarsource.plugins.example.DatabaseAdapter;

import org.json.JSONObject;

/**
 * Created by Mathias on 2016-06-21.
 */
public class AaronAPIAdapter {
    private final static String IP = "http://146.185.128.124/api/";
    private final static String FORMAT = "/?format=json";

    public JSONObject getUsers() {
        return JSONReader.getJSON(IP + "users" + FORMAT);
    }
}
