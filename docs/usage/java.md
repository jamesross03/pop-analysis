# Running Pop-Analysis with Java Guide
This guide will provide a simple walkthrough of how to run the Pop-analysis application with Java, using a released JAR file (for instructions on building your own, see the [Java build guide](../build/java.md)).

## 1. Prerequisites
The following tools must be installed on your system to follow this guide:
- [Git](https://git-scm.com/)
- [Java 21 or greater](https://www.oracle.com/uk/java/)

## 2. Installation
To run Pop-analysis with Java, you only need the JAR file. However in this guide, we will be running Pop-analysis with sample configuration files and test-data, which needs to be installed separately.

### 2.1. Installing sample resources
We will use the sample resources from the [Pop-analysis repository](https://github.com/jamesross03/pop-analysis). To install this, run the following command:

```sh
# In a terminal (Windows/macOS/Linux)

git clone https://github.com/jamesross03/pop-analysis.git
```

Within this, we will use the following files:
- `src/main/resources/config/config.txt`
- `src/main/resources/inputs/TD_5k/birth_records.csv`

### 2.2. Installing the JAR file
Navigate into the repository installed in the previous step ([2.1](#21-installing-sample-resources)) and run the following command to install the latest JAR file:

```sh
# In a terminal (Windows/MacOs/Linux)
wget https://github.com/jamesross03/pop-analysis/releases/latest/download/pop-analysis.jar
```

To verify that the JAR file is working, navigate to the directory it is in and run the following command:

```sh
# In a terminal (Windows/MacOs/Linux)

java -jar pop-analysis.jar
```

which should print the following message

```txt
No config file given as CLI arg 
Expected usage: pop-analysis <config-filepath>
```

## 3. Running
To run Pop-analysis using the default configuration file (which makes use of the "TD_5k" synthetic dataset), run the following command (from within the root of the repository):

```shell
# In a terminal (Windows/MacOs/Linux)

java -jar pop-analysis.jar src/main/resources/config/config.txt
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
The results of the analysis, in this case a csv file containing counts of different surnames within the dataset, will be output to a `results/` directory within the repository (created on run if not already existing). The specific run will be located at a path like:

```
results/default/2025-06-18T13-45-29-345/
```

Running with the above config file will generate a single results table within this at `tables/surname_freq.csv`.

### 3.2. Configuration
The above instructions detail how to run with a basic example configuration file, which looks like:

```
# Example default config

## Required Parameters
records_location = ./src/main/resources/inputs/TD_5k/birth_records.csv
record_format = TD
record_type = BIRTH
```

This can be edited to customise/tailor your run, using a number of available configuration options ([read more here](../config/index.md)).