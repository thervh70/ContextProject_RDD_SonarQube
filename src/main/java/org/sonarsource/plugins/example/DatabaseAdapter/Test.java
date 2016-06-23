package org.sonarsource.plugins.example.DatabaseAdapter;

import org.sonarsource.plugins.example.entities.File;
import org.sonarsource.plugins.example.entities.PullRequest;
import org.sonarsource.plugins.example.entities.Repository;
import org.sonarsource.plugins.example.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Mathias on 2016-06-23.
 */
public class Test {

    private static HashMap<String, Integer> occurrences = new HashMap<>();

    public static User initUser(AaronAPIAdapter aaron) {
        User user = null;
        ArrayList<User> userList = aaron.getUsers();
        for (User u : userList) {
            if (u.getName().equals("thervh70")) {
                user = u;
            }
        }
        return user;
    }

    public static Repository initRepo(User user) {
        Repository repo = null;
        for (Repository r : user.getRepositoryList()) {
            if (r.getName().equals("ContextProject_RDD")) {
                repo = r;
            }
        }
        return repo;
    }

    public static ArrayList<PullRequest> initPullRequest(GitHubAPIAdapter adapter, User user, Repository repo) {
        return adapter.getOpenPullsByReponame(user.getName(), repo.getName());
    }

    private static void printHashmap() {
        Set<String> keys = occurrences.keySet();
        for (String key : keys) {
            System.out.println(key + " : " + occurrences.get(key));
        }
    }

    public static void calculateFile(User user, Repository repo, ArrayList<PullRequest> pullRequestList) {
        GitHubAPIAdapter adapter = new GitHubAPIAdapter();
        for (PullRequest pullRequest : pullRequestList) {
            System.out.println(user.getName() + " " + repo.getName() + " " + pullRequest.getId());
            ArrayList<File> files = adapter.getFilesByPullID(user.getName(), repo.getName(), pullRequest.getId());
            for (File file : files) {
                occurrences.put(file.getName(), occurrences.get(file.getName()) + 1);
            }
        }
        printHashmap();
    }
}
