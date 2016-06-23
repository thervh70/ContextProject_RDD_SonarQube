package org.sonarsource.plugins.example.DatabaseAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sonarsource.plugins.example.entities.PullRequest;
import org.sonarsource.plugins.example.entities.Repository;
import org.sonarsource.plugins.example.entities.User;

import java.util.ArrayList;

/**
 * Created by Mathias on 2016-06-21.
 */
public class AaronAPIAdapter {
    private final static String IP = "http://146.185.128.124/api/";
    private final static String FORMAT = "/?format=json";

    public ArrayList<User> getUsers() {
        JSONObject json = JSONReader.getJSON(IP + "users" + FORMAT);
        JSONArray array = json.getJSONArray("results");
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            String username = ((JSONObject) array.get(i)).get("username").toString();
            users.add(new User(username));
        }
        users = getRepositories(users);

        return users;
    }

    public ArrayList<User> getRepositories(ArrayList<User> userList) {
        JSONObject json = JSONReader.getJSON(IP + "repositories" + FORMAT);
        JSONArray array = json.getJSONArray("results");
        for (User user : userList) {
            for (int i = 0; i < array.length(); i++) {
                String platform = ((JSONObject) array.get(i)).get("platform").toString();
                String owner = ((JSONObject) array.get(i)).get("owner").toString();
                if (platform.equals("GitHub") && owner.equals(user.getName())) {
                    String reponame = ((JSONObject) array.get(i)).get("name").toString();
                    user.addRepository(new Repository(reponame, user, ""));
                }
            }
        }
        return userList;
    }

    public ArrayList<Repository> getPullRequests(ArrayList<Repository> repoList) {
        JSONObject json = JSONReader.getJSON(IP + "pull-requests" + FORMAT);
        JSONArray array = json.getJSONArray("results");
        for (Repository repo : repoList) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject repoObject = (JSONObject) ((JSONObject) array.get(i)).get("repository");
                String platform = ((JSONObject) array.get(i)).get("platform").toString();
                String reponame = ((JSONObject) array.get(i)).get("name").toString();
                if (platform.equals("GitHub") && repo.getName().equals(reponame)) {
                    int pullNumber = ((JSONObject) array.get(i)).getInt("pull_request_number");
                    repo.addPullRequest(new PullRequest(pullNumber));
                }
            }
        }
        return repoList;
    }
}
