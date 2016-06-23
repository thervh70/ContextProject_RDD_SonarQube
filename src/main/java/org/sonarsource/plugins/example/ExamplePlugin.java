package org.sonarsource.plugins.example;

import org.sonar.api.Plugin;
import org.sonarsource.plugins.example.measures.ComputeSizeAverage;
import org.sonarsource.plugins.example.measures.PullRequestMetrics;
import org.sonarsource.plugins.example.measures.SetSizeOnFilesSensor;
import org.sonarsource.plugins.example.web.ExampleFooter;
import org.sonarsource.plugins.example.web.ExampleWidget;

/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class ExamplePlugin implements Plugin {

  @Override
  public void define(Context context) {
//    // tutorial on hooks
//    // http://docs.sonarqube.org/display/DEV/Adding+Hooks
//    context.addExtensions(DisplayIssuesInScanner.class, DisplayQualityGateStatus.class);
//
//    // tutorial on languages
//    context.addExtensions(FooLanguage.class, FooQualityProfile.class);

    // tutorial on measures
    context
      .addExtensions(PullRequestMetrics.class, SetSizeOnFilesSensor.class, ComputeSizeAverage.class);

//    // tutorial on rules
//    context.addExtensions(JavaRulesDefinition.class, CreateIssuesOnJavaFilesSensor.class);
//    context.addExtensions(FooLintRulesDefinition.class, FooLintIssuesLoaderSensor.class);
//
//    // tutorial on settings
//    context
//      .addExtensions(ExampleProperties.definitions())
//      .addExtension(SayHelloFromScanner.class);
//
//    // tutorial on web extensions
    context.addExtensions(ExampleFooter.class, ExampleWidget.class);
  }
}
