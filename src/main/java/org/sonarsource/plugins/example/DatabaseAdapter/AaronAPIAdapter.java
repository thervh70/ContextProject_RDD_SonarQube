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

    /**
     * Get all the Users from the API
     * @return
     */
    public ArrayList<User> getUsers() {
        JSONObject json = JSONReader.getJSON(IP + "users" + FORMAT);
        JSONArray array = json.getJSONArray("results");
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            String username = ((JSONObject) array.get(i)).getString("username");
            users.add(new User(username));
        }
        users = getRepositories(users);
        return users;
    }

    /**
     * Set all the repositories in the given Users
     * @param userList
     * @return
     */
    public ArrayList<User> getRepositories(ArrayList<User> userList) {
        JSONObject json = JSONReader.getJSON(IP + "repositories" + FORMAT);
        JSONArray array = json.getJSONArray("results");
        for (User user : userList) {
            for (int i = 0; i < array.length(); i++) {
                String platform = ((JSONObject) array.get(i)).getString("platform");
                String owner = ((JSONObject) array.get(i)).getString("owner");
                if (platform.equals("GitHub") && owner.equals(user.getName())) {
                    String reponame = ((JSONObject) array.get(i)).getString("name");
                    user.addRepository(new Repository(reponame, user, ""));
                }
            }
        }
        return userList;
    }

    /**
     * Set all the PullRequests in the given Repository
     * @param repo
     * @return
     */
    public Repository getPullRequests(Repository repo) {
        JSONObject json = JSONReader.getJSON(IP + "pull-requests" + FORMAT);
        JSONArray array = json.getJSONArray("results");
        for (int i = 0; i < array.length(); i++) {
            JSONObject repositoryObject = ((JSONObject) array.get(i)).getJSONObject("repository");
            String platform = repositoryObject.getString("platform");
            String reponame = repositoryObject.getString("name");
            if (platform.equals("GitHub") && repo.getName().equals(reponame)) {
                int pullNumber = ((JSONObject) array.get(i)).getInt("pull_request_number");
                repo.addPullRequest(new PullRequest(pullNumber));
            }
        }
        return repo;
    }
}
