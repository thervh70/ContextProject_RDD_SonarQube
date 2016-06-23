package org.sonarsource.plugins.example.DatabaseAdapter;


import org.json.JSONArray;
import org.json.JSONObject;
import org.sonarsource.plugins.example.entities.File;
import org.sonarsource.plugins.example.entities.PullRequest;

import java.util.ArrayList;


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

    //This is now done in the AaronAPIAdapter

//    public Repository[] getReposByUsername(String user) {
//        String target = ghAPI + "users/" + user + "/repos";
//        JSONArray repoarray = JSONReader.getJSON(target).getJSONArray("array");
//        Repository[] result = new Repository[repoarray.length()];
//        for (int i = 0; i < repoarray.length(); i++) {
//            JSONObject repo = (JSONObject) repoarray.get(i);
//            String repoName = repo.getString("name");
//            String repoUrl = repo.getString("html_url");
//            ArrayList<PullRequest> pulls = getOpenPullsByReponame(user, repoName);
//            result[i] = new Repository(repoName, user, repoUrl);
//        }
//        return result;
//    }

//    public File[] getFilesByReponame(String repo, String user, String pr) {
//        String target = ghAPI + "repos/" + user + "/" + repo + "/pulls/" + pr;
//        JSONArray pull = JSONReader.getJSON(target).getJSONArray("array");
//    }

//    public String getBaseBranchByPullnumber(int pull) {
//        getOpenPullsByReponame();
//        findPullByPullNumber();
//        String target = "";
//        JSONReader.getJSON(target);
//    }

    public ArrayList<PullRequest> getOpenPullsByReponame(String user, String repo) {
        String target = ghAPI + "repos/" + user + "/" + repo + "/pulls";
        JSONArray pulls = JSONReader.getJSON(target).getJSONArray("array");
        ArrayList<PullRequest> result = new ArrayList<>();
        for (int i = 0; i < pulls.length(); i++) {
            JSONObject pullObject = (JSONObject) pulls.get(i);
            int pullID = pullObject.getInt("number");
            PullRequest pull = new PullRequest(pullID, getFilesByPullID(user, repo, pullID));
            result.add(pull);
        }
        return result;
    }

    public ArrayList<File> getFilesByPullID(String user, String repo, int pullnumber) {
        String target = ghAPI + "repos/" + user + "/" + repo + "/pulls/" + pullnumber + "/files";
        JSONArray files = JSONReader.getJSON(target).getJSONArray("array");
        ArrayList<File> result = new ArrayList<>();
        for (int i = 0; i < files.length(); i++) {
            JSONObject fileObject = (JSONObject) files.get(i);
            File file = new File(fileObject.getString("filename"));
            result.add(file);
        }
        return result;
    }
}
