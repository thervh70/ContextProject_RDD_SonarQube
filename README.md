# ContextProject_RDD_SonarQube

## Setup
- Download the [SonarQube server](http://www.sonarqube.org/downloads/)
- Start the server.
- Get a [scanner](http://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner)
- Clone this repo and build it using `mvn clean package`
- Place the .jar from the `/target` folder in `sonarqube-x.x/extensions/plugins`
- Scan your project with the scanner and enjoy your pull request metrics.
