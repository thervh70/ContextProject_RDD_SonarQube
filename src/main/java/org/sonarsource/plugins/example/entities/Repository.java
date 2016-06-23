package org.sonarsource.plugins.example.entities;

import java.util.ArrayList;

/**
 * Created by Robin on 22-6-2016.
 */
public class Repository {
    private String name;
    private String url;
    private ArrayList<PullRequest> pullRequestList;

    public Repository(String name, String url, ArrayList<PullRequest> pullRequestList) {
        this.name = name;
        this.url = url;
        this.pullRequestList = pullRequestList;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<PullRequest> getPullRequestList() {
        return pullRequestList;
    }
}
