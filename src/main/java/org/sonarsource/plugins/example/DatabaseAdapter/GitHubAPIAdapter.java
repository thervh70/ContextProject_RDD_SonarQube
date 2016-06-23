package org.sonarsource.plugins.example.DatabaseAdapter;


import org.json.JSONArray;
import org.json.JSONObject;
import org.sonarsource.plugins.example.entities.File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Mathias on 2016-06-21.
 */
public class GitHubAPIAdapter {

    private String ghAPI = "https://api.github.com/";

    /**
     * Send a message to the GitHub API while being authenticated.
     * @param target
     * @return
     */
    public JSONObject sendWithAuth(String target) {
        BufferedReader reader;
        try {
            String location = System.getProperty("user.dir");
            System.out.println(location);
            reader = new BufferedReader(new FileReader("src/auth-secrets.txt"));
            String client_id = reader.readLine();
            String client_secret = reader.readLine();
            reader.close();
            client_id = client_id.substring(client_id.indexOf(':') + 1).trim();
            client_secret = client_secret.substring(client_secret.indexOf(':') + 1).trim();
            target = target + "?client_id=" + client_id + "&client_secret=" + client_secret;
            return JSONReader.getJSON(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all the files that are changed in a certain PullRequest
     * @param user
     * @param repo
     * @param pullnumber
     * @return
     */
    public ArrayList<File> getFilesByPullID(String user, String repo, int pullnumber) {
        String target = ghAPI + "repos/" + user + "/" + repo + "/pulls/" + pullnumber + "/files";
        JSONArray files = sendWithAuth(target).getJSONArray("array");
        ArrayList<File> result = new ArrayList<>();
        for (int i = 0; i < files.length(); i++) {
            JSONObject fileObject = (JSONObject) files.get(i);
            File file = new File(fileObject.getString("filename"));
            result.add(file);
        }
        return result;
    }

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
