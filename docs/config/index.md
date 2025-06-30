# Pop-analysis configuration guide
The pop-analysis application takes a single CLI argument, providing the path to a configuration file. Examples files are provided in [src/main/resources/config](../../src/main/resources/config/) and typically take the form:

```txt
# Example default config

## Required Parameters
records_location = ./src/main/resources/inputs/TD_5k/birth_records.csv
record_format = TD
record_type = BIRTH
```

However, these can be edited/created from scratch to suit your needs.

## Configuration options
**\*\** denotes required parameters.

[Input options](#input-options):
- [`records_location`](#records_location-) **\***
- [`record_format`](#record_format-) **\***
- [`record_type`](#record_type-) **\***

[Analysis options](#analysis-options):
- [`analysis`](#analysis)

[Output options](#output-options):
- [`result_save_location`](#result_save_location)
- [`purpose`](#purpose)

### Input options
#### `records_location` **\***
Path to the records CSV file to analyse.

#### `record_format` **\***
Format of the target population records. Can be one of:
- `TD` Custom record format created by [Tom Dalton](https://github.com/tomsdalton).
- `UMEA` Record format used by the Swedish UMEA data.

#### `record_type` **\***
Type of records being analysed. Can be one of:
- `BIRTH` 

### Analysis options
#### `analysis`
Analysis operation being performed on the records. Can be one of:
- `FORENAME_FREQ` Generates a table of counts of different forenames
- `SURNAME_FREQ` Generates a table of counts of different surnames

Defaults to `SURNAME_FREQ`.

### Output options
#### `result_save_location`
Path to the root results directory.

Defaults to `./results`

#### `purpose`
A name used to categorise runs, must be a valid name for files and directories.

Results of a specific run are written to `<results_save_location>/<run_purpose>/<timestamp>`, where:
- Timestamp represents the datetime the runs was executed at in the form yyyy-mm-ddThh-mm-ss-sss.

Defaults to `default`.