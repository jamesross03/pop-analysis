# Running Pop-Analysis with Docker Guide
This guide will provide a simple walkthrough of how to run the Pop-analysis application with Docker, using a released Docker image (for instructions on building your own, see the [Docker build guide](../build/docker.md)).

## 1. Prerequisites
The following tools must be installed on your system to follow this guide:
- [Git](https://git-scm.com/)
- [Docker](https://www.oracle.com/uk/java/)

## 2. Installation
To run Pop-analysis with Docker, you only need the Docker image. However in this guide, we will be running Pop-analysis with sample configuration files and test-data, which needs to be installed separately.

### 2.1. Installing the image
To install the latest Pop-analysis image, run the following command:

```sh
# In a terminal (Windows/MacOs/Linux)

docker pull ghcr.io/jamesross03/pop-analysis:main
```

To verify that the JAR file is working, navigate to the directory it is in and run the following command:

```sh
# In a terminal (Windows/MacOs/Linux)

docker run ghcr.io/jamesross03/pop-analysis:main
```

which should print the following message

```txt
No config file given as CLI arg 
Expected usage: pop-analysis <config-filepath>
```

### 2.1. Installing sample resources
We will use the sample resources from the [Pop-analysis repository](https://github.com/jamesross03/pop-analysis). To install this, run the following command:

```sh
# In a terminal (Windows/macOS/Linux)

git clone https://github.com/jamesross03/pop-analysis.git
```

Within this, we will use the following files:
- `src/main/resources/config/config.txt`
- `src/main/resources/inputs/TD_5k/birth_records.csv`


## 3. Running
To run Pop-analysis using the default configuration file (which makes use of the "TD_5k" synthetic dataset), we will also need to link (mount) local directories from the repository to internal paths within the container. In this case:
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
docker run -v .\src:/app/src -v .\results:/app/results pop-analysis:main /app/src/main/resources/config/config.txt

# For MacOs/Linux
docker run -v ./src:/app/src -v ./results:/app/results ghcr.io/jamesross03/pop-analysis:main /app/src/main/resources/config/config.txt
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

### 3.1. Results
The results of the analysis, in this case a csv file containing counts of different surnames within the dataset, will be output to the `./results/` directory  The specific run will be located at a path like:

```
results/default/2025-06-18T13-45-29-345/
```

Running with the above config file will generate a single results table within this at `tables/surname_freq.csv`.

### 3.2. Notes on binding/mounting directories
Some things to consider:
- Paths must be absolute, not relative.
- In above example, filepath of record (defined in config file) is within the resources directory. If not, this will also have to be mounted separately.

### 3.3. Configuration
The above instructions detail how to run with a basic example configuration file, which looks like:

```
# Example default config

## Required Parameters
records_location = ./src/main/resources/inputs/TD_5k/birth_records.csv
record_format = TD
record_type = BIRTH
```

This can be edited to customise/tailor your run, using a number of available configuration options ([read more here](../config/index.md)).
