package org.sonarsource.plugins.example.entities;

/**
 * Created by Robin on 22-6-2016.
 */
public class PullRequest {
    private int id;

    public PullRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
