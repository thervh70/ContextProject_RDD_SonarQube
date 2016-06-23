package org.sonarsource.plugins.example.DatabaseAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mathias on 2016-06-21.
 */
public class AaronAPIAdapter {
    private final static String IP = "http://146.185.128.124/api/";
    private final static String FORMAT = "/?format=json";

    public ArrayList<String> getUsernames() {
        JSONObject json = JSONReader.getJSON(IP + "users" + FORMAT);
        JSONArray array = json.getJSONArray("results");
        ArrayList<String> names = new ArrayList<>();
        for (int user = 0; user < array.length(); user++) {
            names.add(((JSONObject) array.get(user)).get("username").toString());
        }
        return names;
    }

}
