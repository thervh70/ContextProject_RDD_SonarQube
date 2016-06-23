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

import static org.sonarsource.plugins.example.measures.PullRequestMetrics.FILENAME_SIZE;

/**
 * Scanner feeds raw measures on files but must not aggregate values to directories and project.
 * This class emulates loading of file measures from a 3rd-party analyser.
 */
public class SetSizeOnFilesSensor implements Sensor {
  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name("Compute size of file names");
  }

  @Override
  public void execute(SensorContext context) {
    FileSystem fs = context.fileSystem();
    Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasType(InputFile.Type.MAIN));
    for (InputFile file : files) {
      int occurenceNumber = calculateFile(file.file().getName());
      context.<Integer>newMeasure()
        .forMetric(FILENAME_SIZE)
        .on(file)
        .withValue(occurenceNumber)
        .save();
    }
  }

  public int calculateFile(String fileName) {
    int res = 0;
    GitHubAPIAdapter adapter = new GitHubAPIAdapter();
    AaronAPIAdapter aaron = new AaronAPIAdapter();
    ArrayList<User> userList = aaron.getUsers();
    for (User user : userList){
      for (Repository repo : user.getRepositoryList()) {
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
      }
    }
    return res;
  }
}
