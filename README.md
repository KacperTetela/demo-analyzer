# Demo Analyzer

**Status:** 🚧 In development

Demo Analyzer is a modular tool designed for parsing and analyzing Counter-Strike 2 demo files (`.dem`).  
It leverages the [AWPY](https://awpy.readthedocs.io/en/latest/index.html) library to extract detailed in-game events, player data, and round statistics.

## Features (in progress)

- `analyzer` domain: under active development; performs higher-level analysis such as:
  - First kill detection and its impact on round outcome
  - Clutch detection (1vX situations)
  - Server metadata and round flow tracking

## Technologies

- Java 17+
- Maven
- AWPY (Python-based parser used via integration)
- Modular architecture with domain-driven design (DDD) principles

---

**Note:** This project is still in its early stages. Functionality is being actively developed and tested.
