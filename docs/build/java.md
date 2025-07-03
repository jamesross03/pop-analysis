# Building Pop-Analysis JAR from source
This guide will provide a walkthrough of how to build a JAR file from the Pop-analysis source (for instructions on using a released JAR, see the [Java usage guide](../usage/java.md)).

## 1. Prerequisites
The following tools must be installed on your system to follow this guide:
- [Git](https://git-scm.com/)
- [Java 21](https://www.oracle.com/uk/java/)
- [Maven](https://maven.apache.org/)

## 2. Building the JAR
### 2.1. Installing the source
First, we will need to install the source from the [Pop-analysis repository](https://github.com/jamesross03/pop-analysis) using the following command:

```sh
# In a terminal (Windows/macOS/Linux)

git clone https://github.com/jamesross03/pop-analysis.git
```
### 2.2. Authenticate Github packages
As the application requires dependencies which are hosted on Github packages, you will need to add an authentication token to build the JAR package. To do this, edit your Maven settings file, usually located at `~/.m2/settings.xml` and insert your Github username and access-token. The file should look like this:

```xml
<settings>
  <servers>
    <server>
      <id>github</id>
      <username>YOUR_GITHUB_USERNAME</username>
      <password>GITHUB_READ_TOKEN</password>
    </server>
  </servers>
</settings>
```

To generate a Github token, go to [github.com/settings/tokens](https://github.com/settings/tokens) and generate a new token with read permissions for public repositories.

### 2.3. Building the JAR using Maven
Navigate into the repository installed in step ([2.1](#21-installing-the-source)) and run the following command to build the JAR file:

```sh
# In a terminal (Windows/MacOs/Linux)
mvn clean package
```

This will create two JAR files in the `target/` directory but we will want to use the JAR file with dependencies, which will be named similar to `pop-analysis-<VERSION_NUMBER>-jar-with-dependencies.jar`

To verify that the JAR file is working, navigate to the directory it is in and run the following command (adjusted for the correct file name):

```sh
# In a terminal (Windows/MacOs/Linux)

java -jar target/pop-analysis-<VERSION_NUMBER>-jar-with-dependencies.jar
```

which should print the following message

```txt
No config file given as CLI arg 
Expected usage: pop-analysis <config-filepath>
```

## 3. Running
To run Pop-analysis using this image and the default configuration file (which makes use of the "TD_5k" synthetic dataset), move the image to the respository root and run the following command:

```shell
# In a terminal (Windows/MacOs/Linux)

java -jar pop-analysis-<VERSION_NUMBER>-jar-with-dependencies.jar src/main/resources/config/config.txt
```

This will produce output similar to the following (albeit with different timestamps):
```
Running analysis with src/main/resources/config/config.txt
2025/06/18 12:44:12.423 :: Reading records from ./src/main/resources/inputs/TD_5k/birth_records.csv
Elapsed time: 00:00:00
2025/06/18 12:44:12.591 :: Adding records to surname frequency table
Elapsed time: 00:00:00
2025/06/18 12:44:12.599 :: Outputting surname frequency table
Elapsed time: 00:00:00
```

For more detailed information on how to run and configure the application, see the [usage guides](../usage/index.md).
