Language detection pipeline
---

Implementation of a modular language detection pipeline that uses a chain of responsibility to process a detection request in stages.
The current implementation uses two stages, one that trains on a set of known texts and builds dictionaries, and a second stage that computes a simple score based on the number of matches between tokens in the target text and the training dictionaries.

The dictionary implementation uses a space-efficient probabilistic data structure: bloom filter. See https://github.com/magnuss/java-bloomfilter.

Compiling
---

```
mvn package
```

Running examples
---
./run.sh ENGLISH.TEXT

or

java -jar target/langid-0.0.1-SNAPSHOT-jar-with-dependencies.jar ENGLISH.TEXT