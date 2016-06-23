package org.sonarsource.plugins.example.measures;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonarsource.plugins.example.DatabaseAdapter.AaronAPIAdapter;
import org.sonarsource.plugins.example.DatabaseAdapter.GitHubAPIAdapter;
import org.sonarsource.plugins.example.entities.File;
import org.sonarsource.plugins.example.entities.PullRequest;
import org.sonarsource.plugins.example.entities.Repository;
import org.sonarsource.plugins.example.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Scanner feeds raw measures on files but must not aggregate values to directories and project.
 * This class emulates loading of file measures from a 3rd-party analyser.
 */
public class SetSizeOnFilesSensor implements Sensor {

    private static HashMap<String, Integer> occurrences = new HashMap<>();

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("Compute size of file names");
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasType(InputFile.Type.MAIN));
        GitHubAPIAdapter adapter = new GitHubAPIAdapter();
        AaronAPIAdapter aaron = new AaronAPIAdapter();
        User user = initUser(aaron);
        Repository repo = initRepo(user);
        ArrayList<PullRequest> pullRequestList = initPullRequest(adapter, user, repo);
        for (InputFile file : files) {
            occurrences.put(file.file().getName(), 0);
        }
        calculateFile(user, repo, pullRequestList);
    }

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
