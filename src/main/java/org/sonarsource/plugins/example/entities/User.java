package org.sonarsource.plugins.example.entities;

import java.util.ArrayList;

/**
 * Created by Robin on 22-6-2016.
 */
public class User {
    private String name;
    private ArrayList<Repository> repositoryList;

    public User(String name, ArrayList<Repository> repositoryList) {
        this.name = name;
        this.repositoryList = repositoryList;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Repository> getRepositoryList() {
        return repositoryList;
    }
}
