package org.sonarsource.plugins.example.web;

import org.sonar.api.web.Footer;
import org.sonarsource.plugins.example.DatabaseAdapter.GitHubAPIAdapter;

public final class ExampleFooter implements Footer {

  @Override
  public String getHtml() {
    GitHubAPIAdapter adapter = new GitHubAPIAdapter();
    return "<p>" + adapter.getUser("MathiasMeuleman") + "</p>";
  }
}
