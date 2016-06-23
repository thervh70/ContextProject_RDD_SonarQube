package org.sonarsource.plugins.example.entities;

import java.util.ArrayList;

/**
 * Created by Robin on 22-6-2016.
 */
public class User {
    private String name;
    private ArrayList<Repository> repositoryList;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", repositoryList=" + repositoryList +
                '}';
    }

    public User(String name) {
        this.name = name;
        this.repositoryList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addRepository(Repository repository) {
        this.repositoryList.add(repository);
    }

    public ArrayList<Repository> getRepositoryList() {
        return repositoryList;
    }
}
