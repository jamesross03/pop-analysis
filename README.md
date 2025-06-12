# Pop-analysis
Pop-analysis is a basic deployable containerised application for population data analysis, designed as a test for future population-linkage experiments. The focus of this is to create a portable image which can be easily deployed on a secure system and pointed towards a population dataset, with minimal setup.

## Building image from source
To build pop-analysis from source, the following need to be installed:
- Git
- Java 21+
- Maven
- Docker/equivalent

```sh
# Clone the repository
git clone https://github.com/jamesross03/valipop.git

# Navigate to repository directory
cd pop-analysis

# Build docker image
docker build . -t pop-analysis:latest

# Run image
docker run pop-analysis:latest
```