# Pop-Analysis Usage Instructions
Pop-analysis can be run (by building from source or downloading the latest release), using either:
- [Docker](./docker.md)
- [Java](./docker.md)

The above guides use existing releases, if you're looking to build your own JAR files or Docker images, see [build guides](../build/index.md).

## Configuration
When run, Pop-analysis takes the filepath of a configuration text file as a CLI argument. Examples files are provided in [src/main/resources/config](../../src/main/resources/config/), although the application can be run with any configuration you like, using a number of available options. For more details, see the [configuration guide](../config/index.md).