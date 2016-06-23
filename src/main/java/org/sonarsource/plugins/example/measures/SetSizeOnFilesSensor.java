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

import static org.sonarsource.plugins.example.measures.PullRequestMetrics.FILENAME_SIZE;

/**
 * Scanner feeds raw measures on files but must not aggregate values to directories and project.
 * This class emulates loading of file measures from a 3rd-party analyser.
 */
public class SetSizeOnFilesSensor implements Sensor {

    private HashMap<String, Integer> occurences = new HashMap<>();

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("Compute size of file names");
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasType(InputFile.Type.MAIN));
        AaronAPIAdapter aaron = new AaronAPIAdapter();
        User user = initUser(aaron);
        Repository repo = initRepo(user);
        for (InputFile file : files) {
            int occurenceNumber = calculateFile(file.file().getName(), user, repo);
            context.<Integer>newMeasure()
                    .forMetric(FILENAME_SIZE)
                    .on(file)
                    .withValue(occurenceNumber)
                    .save();
        }
    }

    private User initUser(AaronAPIAdapter aaron) {
        User user = null;
        ArrayList<User> userList = aaron.getUsers();
        for (User u : userList) {
            if (u.getName().equals("thervh70")) {
                user = u;
            }
        }
        return user;
    }

    private Repository initRepo(User user) {
        Repository repo = null;
        for (Repository r : user.getRepositoryList()) {
            if (r.getName().equals("ContextProject_RDD")) {
                repo = r;
            }
        }
        return repo;
    }

    public int calculateFile(String fileName, User user, Repository repo) {
        int res = 0;
        GitHubAPIAdapter adapter = new GitHubAPIAdapter();
        ArrayList<PullRequest> pullRequestList = adapter.getOpenPullsByReponame(user.getName(), repo.getName());
        for (PullRequest pullRequest : pullRequestList) {
            System.out.println(user.getName() + " " + repo.getName() + " " + pullRequest.getId());
            ArrayList<File> files = adapter.getFilesByPullID(user.getName(), repo.getName(), pullRequest.getId());
            for (File file : files) {
                if (file.getName().equals(fileName)) {
                    res++;
                }
            }
        }
        return res;
    }
}
