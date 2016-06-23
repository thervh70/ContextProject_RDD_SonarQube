package org.sonarsource.plugins.example.entities;

import java.util.ArrayList;

/**
 * Created by Robin on 22-6-2016.
 */
public class Repository {
    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", owner=" + owner.getName() +
                ", pullRequestList=" + pullRequestList +
                '}';
    }

    private String name;
    private String url;
    private User owner;
    private ArrayList<PullRequest> pullRequestList;

    public Repository(String name, User owner, String url) {
        this.name = name;
        this.url = url;
        this.owner = owner;
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
