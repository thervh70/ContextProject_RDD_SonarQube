package org.sonarsource.plugins.example.DatabaseAdapter;


import org.json.JSONArray;
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

    public String[] getReposByUsername(String user) {
        String target = ghAPI + "users/" + user + "/repos";
        JSONObject userdata = JSONReader.getJSON(target);
        JSONArray repoarray = userdata.getJSONArray("array");
        String[] result = new String[repoarray.length()];
        for (int i = 0; i < repoarray.length(); i++) {
            JSONObject repo = (JSONObject) repoarray.get(i);
            System.out.println("Repo " + i + " : " + repo);
            System.out.println("Name " + i + " : " + repo.getString("name"));
            result[i] = repo.getString("name");
        }
        return result;
    }
}
