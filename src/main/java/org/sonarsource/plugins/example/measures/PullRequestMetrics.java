package org.sonarsource.plugins.example.measures;

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.List;

import static java.util.Arrays.asList;

public class PullRequestMetrics implements Metrics {

  public static final Metric<Integer> PULLREQUEST_SIZE = new Metric.Builder("filename_size", "Filename Size", Metric.ValueType.INT)
    .setDescription("Number of characters of file names")
    .setDirection(Metric.DIRECTION_BETTER)
    .setQualitative(false)
    .setDomain(PluginMetrics.DOMAIN_PULLREQUESTS)
    .create();

  @Override
  public List<Metric> getMetrics() {
    return asList(PULLREQUEST_SIZE);
  }
}
