package org.sonarsource.plugins.example.entities;

import java.util.ArrayList;

/**
 * Created by Robin on 22-6-2016.
 */
public class Repository {
    private String name;
    private String url;
    private ArrayList<PullRequest> pullRequestList;

    public Repository(String name, String url) {
        this.name = name;
        this.url = url;
        this.pullRequestList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void addPullRequest(PullRequest pull) {
        this.pullRequestList.add(pull);
    }

    public ArrayList<PullRequest> getPullRequestList() {
        return pullRequestList;
    }
}
