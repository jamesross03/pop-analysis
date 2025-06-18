# Pop-analysis
Pop-analysis is a basic deployable containerised application for population data analysis, designed as a test for future population-linkage experiments. The focus of this is to create a portable image which can be easily deployed on a secure system and pointed towards a population dataset, with minimal setup.

For more in-depth information, see [documentation](./docs/index.md).

## Running Pop-analysis
Pop-analysis can be run from either a JAR file or a Docker container. Basic instructions for these can be found below, for more detailed instructions, see the [usage guides](docs/usage/index.md). Additionally, to build your own JAR files or docker images from the source code, see the [build guides](docs/build/index.md), and for configuration options, see the [configuration guide](docs/config/index.md)

### Running using JAR file
To run Pop-analysis from a JAR file using the default configuration file (which makes use of the "TD_5k" synthetic dataset), run the following command:

```sh
# Install repository for resources
git clone https://github.com/jamesross03/pop-analysis.git

# Navigate into repository
cd pop-analysis 

# Install latest JAR file
wget https://github.com/jamesross03/pop-analysis/releases/latest/download/pop-analysis.jar

# Run Pop-analysis
java -jar pop-analysis.jar src/main/resources/config/config.txt
```

### Running using Docker container
To run Pop-analysis as a docker container using the default configuration file (which makes use of the "TD_5k" synthetic dataset), run the following commands:

```sh
# Install latest docker image
docker pull ghcr.io/jamesross03/pop-analysis:main

# Install repository for resources
git clone https://github.com/jamesross03/pop-analysis.git

# Navigate into repository
cd pop-analysis

# Make results directory
mkdir results

# Run Pop-analysis (Windows)
docker run -v .\src:/app/src -v .\results:/app/results pop-analysis:main /app/src/main/resources/config/config.txt
# Run Pop-analysis (MacOS/Linux)
docker run -v ./src:/app/src -v ./results:/app/results ghcr.io/jamesross03/pop-analysis:main /app/src/main/resources/config/config.txt
```