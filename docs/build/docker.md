# Building Pop-Analysis Docker from source
This guide will provide a walkthrough of how to build a JAR file from the Pop-analysis source (for instructions on using a released image, see the [Docker usage guide](../usage/docker.md)).

## 1. Prerequisites
The following tools must be installed on your system to follow this guide:
- [Git](https://git-scm.com/)
- [docker](https://www.docker.com/), [podman](https://podman.io/) or any other container management tool

## 2. Building the image
### 2.1. Installing the source
First, we will need to install the source from the [Pop-analysis repository](https://github.com/jamesross03/pop-analysis) using the following command:

```sh
# In a terminal (Windows/macOS/Linux)

git clone https://github.com/jamesross03/pop-analysis.git
```

### 2.2. Building the image
Navigate into the repository installed in the previous step ([2.1](#21-installing-the-source)) and run the following command to build the Docker image:

```sh
# In a terminal (Windows/MacOs/Linux)
docker build . -t pop-analysis:latest
```

To verify that the image is working, navigate to the directory it is in and run the following command:

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
To run Pop-analysis using this image and the default configuration file (which makes use of the "TD_5k" synthetic dataset), run the following command (still in the root of the installed repository):

Navigate to the root of the installed repository and run the following commands to bind these and run Pop-analysis:
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