<!---
Copyright 2020 Crown Copyright

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--->

# Performance

Palisade includes a performance tool for testing some simple scenarios.
It uses the example rules and generates some fake HR data using the [HR data generator](../hr-data-generator/README.md).

The tool works on the following data-set sizes:
* *large* - 10,000 records in a single ~20MB file resource
* *small* - 1,000 records in a single ~2MB file resource
* *many* - 1 record per resource, with 10,000 such unique resources

Each data-set is then duplicated as the following variants:
* *no-policy* - a directory with no such rules applied (in reality, a single do-nothing rule is required)
* *with-policy* - a directory subject to a number of example rules

The following trials are tested for each size and variant of data-set:
* *read-native* - baseline test of a native file-read and deserialise, used for normalisation
* *request* - test of just palisade service with no data read or deserialise, providing an indication of palisade latency
* *read* - test of palisade and data services read, deserialise and coarse/fine-grain rule application, providing an indication of palisade overhead

The general naming scheme is "trial_size_variant".

All above values and more can be tweaked through the [config yaml](src/main/resources/application.yaml).



## Usage

### Automated
For an automated way to perform these tests, see the [services-manager](https://github.com/gchq/Palisade-services/blob/develop/services-manager/README.md) for more details.

From the Palisade-services directory, start the discovery-service:  
`java -jar -Dspring.profiles.active=discovery services-manager/target/services-manager-*-exec.jar`

Create some test data for the performance-test, start the palisade-services and run the performance-test:  
`java -jar -Dspring.profiles.active=example-perf services-manager/target/services-manager-*-exec.jar --manager.schedule=performance-create-task,palisade-task,performance-test-task`
 * Services will start up with their cache/persistence-store prepopulated with example data
 * The performance-test will run once all services have started
 * Check `performance-test.log` for output data

Once the create-perf-data task has been run once, it does not need to be re-run:
 * If running the performance tests repeatedly, the above command can be sped up to the default configuration of:  
   `java -jar -Dspring.profiles.active=example-perf services-manager/target/services-manager-*-exec.jar`  
 * If the palisade services are also still running, the above command can be sped up again to exclude starting the already-running services:  
   `java -jar -Dspring.profiles.active=example-perf services-manager/target/services-manager-*-exec.jar --manager.schedule=performance-test-task`  
   Or run just the performance-test manually as below...


### Manual

#### Creation of test data
Create a collection of Employee records in the [resources directory](/resources/data)
```bash
java -jar performance/target/performance-*-exec.jar --performance.action=create
# or similarly
java -Dspring.profiles.active=create -jar performance/target/performance-*-exec.jar
```
This may take a long time to run, depending upon the requested sizes of the test data (up to 5 minutes).

#### Running performance tests
Ensure first the [Palisade services](https://github.com/gchq/Palisade-services/) are running, and have been populated with the appropriate example data.
The profile for prepopulating the services can be found [here](../example-library/src/main/resources/application-example-perf.yaml).

Once all services have started, run the following:
```bash
java -jar performance/target/performance-*-exec.jar
```

Again, this may take some time, depending upon test data size.
Be aware of any running antivirus software that may scan files in real time - eg. McAfee will contribute a factor of ~5x slow-down to bulk file tests.


### Analysis of results
The tool reports several statistics, but the most useful are the norm, mean and standard deviation.
The percentage columns are the various percentile levels.
The "Norm" column is the normalised column, showing how long various tests took compared to reading the files natively (without Palisade).
Reads of `large`, `small` and `many` files are normalised against their corresponding native read.
Requests for `with-policy` are normalised against their corresponding `no-policy` requests.
