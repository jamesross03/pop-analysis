#!/bin/sh

# OUTPUT PATH FOR KEY
KEY_PATH="$HOME/.ssh/private_key.pem"

if [ -n "$PRIVATE_KEY_RAW" ]; then
  echo "Rebuilding private key from environment variable..."

  # Make sure .ssh directory exists
  mkdir -p "$(dirname "$KEY_PATH")"

  # Rebuild key file with RSA delimiters
  {
    echo "-----BEGIN RSA PRIVATE KEY-----"
    # Restore newlines if missing
    echo "$PRIVATE_KEY_RAW" | sed 's/\\n/\n/g'
    echo "-----END RSA PRIVATE KEY-----"
  } > "$KEY_PATH"

  # Correct permissions for key file
  chmod 600 "$KEY_PATH"

  echo "Private key rebuilt and ouput to $KEY_PATH"
else
  echo "Environment variable PRIVATE_KEY_RAW is not set. Skipping private key rebuild."
fi

# Run main entrypoint
exec "$@"