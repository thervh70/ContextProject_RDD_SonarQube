package org.sonarsource.plugins.example.DatabaseAdapter;


import java.io.IOException;

/**
 * Created by Mathias on 2016-06-21.
 */
public class GitHubAPIAdapter {

    private String ghAPI = "https://api.github.com/";
    public String getUser(String username) {
        return JSONReader.getJSON(ghAPI + "users/" + username).toString();
    }
}
