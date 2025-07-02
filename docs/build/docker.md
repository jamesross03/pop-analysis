# Building Pop-Analysis Docker from source
This guide will provide a walkthrough of how to build a Docker image from the Pop-analysis source (for instructions on using a released image, see the [Docker usage guide](../usage/docker.md)).

## 1. Prerequisites
The following tools must be installed on your system to follow this guide:
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/), [Podman](https://podman.io/) or any other container management tool

## 2. Building the image
### 2.1. Installing the source
First, we will need to install the source from the [Pop-analysis repository](https://github.com/jamesross03/pop-analysis) using the following command:

```sh
# In a terminal (Windows/macOS/Linux)
git clone https://github.com/jamesross03/pop-analysis.git
```
### 2.2. Authenticate Github packages
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

### 2.3. Building the image
Navigate into the repository installed in the previous step ([2.1](#21-installing-the-source)) and run the following command to build the Docker image:

```sh
# In a terminal (Windows/MacOs/Linux)
docker build . -t pop-analysis:latest
```

To verify that the Docker image is successfully built, run:

```sh
# In a terminal (Windows/MacOs/Linux)
docker run pop-analysis:latest
```

which should print the following message

```txt
No config file given as CLI arg 
Expected usage: pop-analysis <config-filepath>
```

## 3. Running
To run Pop-analysis using the default configuration file (which makes use of the "TD_5k" synthetic dataset), we will also need to link (mount) local directories from the repository to internal paths within the container. In this case:
- `./src` to `/app/src`
    - To access config file and input distributions
- `./results` to `/app/results`
    - To receive the results
    - Note: Local directory `./results` must be created prior to execution

Run the following commands (from the root of the repository) to bind these and run Pop-analysis:
```sh
# If './results' doesn't yet exist
mkdir results

# For Windows
docker run -v .\src:/app/src -v .\results:/app/results pop-analysis:latest /app/src/main/resources/config/config.txt

# For MacOs/Linux
docker run -v ./src:/app/src -v ./results:/app/results pop-analysis:latest /app/src/main/resources/config/config.txt
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

For more detailed information on how to run and configure the application, see the [Usage guides](../usage/index.md).
