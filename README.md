# Pop-analysis
Pop-analysis is a basic deployable containerised application for population data analysis, designed as a test for future population-linkage experiments. The focus of this is to create a portable image which can be easily deployed on a secure system and pointed towards a population dataset, with minimal setup.

For more in-depth information, see [documentation](./docs/index.md).

**Note: This branch contains a modified version of the pop-analysis application for use with the UMEA application, so documentation may be inaccurate. For documentation corresponding to the modified version, see below**

## Running Pop-analysis (UMEA) Guide
Pop-analysis can be built and run as either a JAR file or a Docker container. For the purpose of the following guide, we shall be using a docker container but similar steps can be followed for building a JAR package using Maven.

### 1. Pre-requisites
The following tools must be installed on your system to follow this guide:
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/), [Podman](https://podman.io/) or any other container management tool
- An instance of the data-umea JAR package, corresponding to your private key.

### 2. Building the Docker image
#### 2.1. Installing the repository
First, we will need to install the source from the [Pop-analysis repository](https://github.com/jamesross03/pop-analysis) and checkout the `umea-analysis` branch using the following command:

```sh
# In a terminal (Windows/macOS/Linux)
git clone https://github.com/jamesross03/pop-analysis.git
git checkout umea-analysis
```

#### 2.2. Authenticating Github packages
As the application requires dependencies which are hosted on Github packages, you will need to add an authentication token to build the docker image. To do this, edit [settings.xml](../../settings.xml) and insert your Github username and access-token. The file should look like this:


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

#### 2.3. Adding the data-umea jar
For the final step before building the container, you will need to place your copy of the umea-data jar within the root of the repository installed in step ([2.1](#21-installing-the-source)).

The [Dockerfile](Dockerfile) runs the following command:
```sh
mvn install:install-file \
  -Dfile=data-umea-1.0-SNAPSHOT-jar-with-dependencies.jar \
  -DgroupId=uk.ac.standrews.cs \
  -DartifactId=data-umea \
  -Dversion=1.0-SNAPSHOT \
  -Dpackaging=jar
```

If your instance of the JAR package doesn't match this name or version, adjust this accordingly in the [Dockerfile](Dockerfile) and [pom.xml](pom.xml).

#### 2.4. Building the image
Having completed all the previous steps, navigate into the repository installed in the previous step ([2.1](#21-installing-the-source)) and run the following command to build the Docker image:

```sh
# In a terminal (Windows/MacOs/Linux)
docker build . -t pop-analysis-umea:latest
```

To verify that the image is working, navigate to the directory it is in and run the following command:

```sh
# In a terminal (Windows/MacOs/Linux)
docker run pop-analysis-umea:latest
```

which should print the following message

```txt
No config file given as CLI arg 
Expected usage: pop-analysis-umea <config-filepath>
```

### 3. R
To run Pop-analysis using the default configuration file, we will also need to link (mount) local directories from the repository to internal paths within the container. In this case:
- `./src` to `/app/src`
    - To access config file and input distributions
- `./results` to `/app/results`
    - To receive the results
    - Note: Local directory `./results` must be created prior to execution

Navigate to the root of the installed repository and run the following commands to bind these and run Pop-analysis:

```sh
# If './results' doesn't yet exist
mkdir results

# For Windows
docker run <KEY_AUTHENTICATION> -v .\src:/app/src -v .\results:/app/results pop-analysis-umea:latest /app/src/main/resources/config/config.txt

# For MacOs/Linux
docker run <KEY_AUTHENTICATION> -v ./src:/app/src -v ./results:/app/results pop-analysis-umea:latest /app/src/main/resources/config/config.txt
```
#### 3.1. Key-authentication
The `data-umea` JAR requires an approved RSA-encrypted PEM private key to operate. The package expects this key to be located in `~/.ssh/private_key.pem`, so this will need to be loaded into the container using one of two methods.

Note: the JAR also expects key to have delimiters the following format:
```txt
-----BEGIN RSA PRIVATE KEY-----
<key content here>
-----END RSA PRIVATE KEY-----
```

**Method 1: Mounting `~/.ssh`**
If your private key is located within `~/.ssh` on the host machine, you can simply mount this by replacing \<KEY_AUTHENTICATION\> with the following mount in the run command:

```sh
-v ~/.ssh:/root/.ssh
```

**Method 2: Key-insertion functionality**
If your private key is not installed on the host machine and you wish to avoid this, the Pop-Analysis Docker image implements a command-line key insertion function, which can be used by replacing \<KEY_AUTHENTICATION\> with the following environment variable in the run command (Warning: This does come with it's own security complications, as detailed in the [key-insertion documentation](docs/usage/key_insertion.md#3-warnings)):

```sh
-e PRIVATE_KEY_RAW="<private_key_text>"
```

#### 3.2. Results
The results of the analysis, in this case a csv file containing counts of different surnames within the dataset, will be output to the `./results/` directory  The specific run will be located at a path like:

```txt
results/default/2025-06-18T13-45-29-345/
```

Running with the above config file will generate a single results table within this at `tables/surname_freq.csv`.

#### 3.3. Configuration
The above instructions detail how to run with a basic example configuration file, which looks like:

```txt
# Example default config

## Required Parameters
# Note: Records_location overridden to UMEA JAR
records_location = ./src/main/resources/inputs/TD_5k/birth_records.csv
# ------------

record_format = UMEA
record_type = BIRTH
```

This can be edited to customise/tailor your run, using a number of available configuration options ([read more here](../config/index.md)).
