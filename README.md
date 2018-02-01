# ChangeDistiller 
[![Build Status](https://travis-ci.org/ftrautsch/tools-changedistiller.svg?branch=master)](https://travis-ci.org/ftrautsch/tools-changedistiller)
[![codecov](https://codecov.io/gh/ftrautsch/tools-changedistiller/branch/master/graph/badge.svg)](https://codecov.io/gh/ftrautsch/tools-changedistiller)


### Description 
This is a fork of [changedistiller](http://www.ifi.uzh.ch/en/seal/research/tools/changeDistiller.html) with 
some adaptions like, e.g., updated dependencies or newer Java version compatibility.


### Test
You can test the project by calling
```bash
mvn test
```

### Build
You can build the project by calling
```bash
mvn package
```

### Install
```bash
mvn install
```

### Use
You can include changedistiller in the following way in your maven pom:
```xml
<dependency>
    <groupId>ch.uzh.ifi.seal</groupId>
	<artifactId>changedistiller</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```

