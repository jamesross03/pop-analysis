# Private key insertion guide
The pop-analysis Docker container supports private-key insertion at runtime, should your use-case require this. This feature is facilitated securely through passing the private key into the container as a CLI parameter, ensuring that the key is only ever stored within the container (and never written to the machine the container is being run on) and is erased as soon as the container stops.

## 1. Assumptions
### 1.1. Prerequisites and Docker images
This guide assumes you have a pop-analysis Docker image (along with all of the relevant prerequisites) installed on your system. If this is inaccurate, please consult either of the following guides first:
- [Docker usage guide](./docker.md)
- [Docker build guide](../build/docker.md)

### 1.2. RSA encrypted pem key
The key reconstructor assumes that it is reconstructing an **RSA-encrypted PEM** private-key into the file "~/.ssh/private_key.pem" within the container.

This key should look something like this (but not redacted):

```txt
-----BEGIN RSA PRIVATE KEY-----
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxx
-----END RSA PRIVATE KEY-----
```

The delimiters may vary, depending on the tool used to generate the key, but for the following steps, we will only be using the raw key text between the delimiters.

## 2. Running with a private key
To run the Docker container with a private key, run the following command, replacing \<private_key_text\> with the body of your key:

```sh
docker run --rm -e PRIVATE_KEY_RAW="<private_key_text>" -v ./src:/app/src -v ./results:/app/results ghcr.io/jamesross03/pop-analysis:main /app/src/main/resources/config/config.txt
```

This will produce output similar to the following (albeit with different timestamps):
```txt
Rebuilding private key from environment variable...
Private key rebuilt and ouput to /root/.ssh/private_key.pem
Running analysis with /app/src/main/resources/config/config.txt
2025/06/27 09:34:34.532 :: Adding records to surname frequency table
Elapsed time: 00:00:00
2025/06/27 09:34:34.540 :: Outputting surname frequency table
Elapsed time: 00:00:00
```

## 3. Warnings
This feature was developed with a focus on security, avoiding ever having to write a key to the host machine (as is required by other methods such as DOCKER_SECRETS). However, it is still not 100% secure and care must be taken when using this feature:

1. **Always use `--rm`** - failure to include this flag could result in the container logs being retained on the system, from which the private key text can easily be extracted.
2. **Be aware of other users on the machine** - while the above flag ensures records of the container aren't stored after runtime, this method is still susceptible to snooping from other users during execution, either via accessing the docker container logs or through using `proc` or other similar system tools.
3. **Command history** - be aware that if other users have access to your terminal history, they may be able to find your 'docker run' command and extract your private key. Thus it is recommended to clear your terminal history after use:
    - Running `history -c` to clear your session history is insufficient, you should also delete the persistent cross-session history file for your terminal (location varies depending on terminal, e.g `~/.bash_history` or `~/.zsh_history`).

If you are trust the file system on the machine enough to install your private key and mount the .ssh directory into the container instead (using `-v ~/.ssh:/root/.ssh`), this would a more secure approach.
