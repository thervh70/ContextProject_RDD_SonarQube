package org.sonarsource.plugins.example.DatabaseAdapter;


import org.json.JSONObject;


/**
 * Created by Mathias on 2016-06-21.
 */
public class GitHubAPIAdapter {

    private String ghAPI = "https://api.github.com/";

    /**
     * Get the user and all of it's relevant data.
     * @param username
     * @return
     */
    public String getUser(String username) {
        JSONObject json = JSONReader.getJSON(ghAPI + "users/" + username);
        String name = json.getString("login");
        return name;
    }
}
