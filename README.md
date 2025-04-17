# Fylbyte

**Fylbyte** is a simple command-line utility for generating dummy files of specified sizes using either null or random bytes.

## Description

Fylbyte enables users to quickly create dummy files, whether for cleaning up free disk space to prevent data recovery, testing storage limitations, simulating file transfer speeds, or simply creating placeholder files of specific sizes. Fylbyte provides a fast and intuitive way to generate these files with minimal effort.

## Features

- Generate files of specified sizes (KB, MB, GB)
- Fill files with either null bytes (all zeros) or random bytes
- Create single or multiple files in a single operation
- Interactive command-line interface with robust input validation
- Colorized output for enhanced user experience

## Requirements

- Java 17 or higher
- Maven 3.6 or higher (for building from source)

## Installation

### Using Pre-Built JAR

1. Download the latest release JAR file.
2. Run the application using Java:

   ```bash
   java -jar fylbyte-1.0.jar
   ```

### Building from Source

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/Fylbyte.git
   cd Fylbyte
   ```

2. Build the project using Maven:

   ```bash
   mvn clean package
   ```

3. Run the generated JAR file:

   ```bash
   java -jar target/fylbyte-1.0-SNAPSHOT.jar
   ```

## Usage

When you run Fylbyte, it guides you through an interactive setup:

1. Enter the desired file size (e.g., `10KB`, `5MB`, `1GB`)
2. Choose the byte type:
   - `n` for null bytes (zeros)
   - `r` for random bytes
3. Select the file creation mode:
   - `s` for a single file
   - `m` for multiple files
4. If multiple files are selected, enter the number of files to generate

## Use Cases

- Securely wiping free disk space by filling it with dummy files before deletion
- Testing file upload/download functionality
- Benchmarking storage or file system performance
- Validating backup and recovery processes
- Simulating full disk conditions for development or QA
- Exploring file system size limits and behaviors
