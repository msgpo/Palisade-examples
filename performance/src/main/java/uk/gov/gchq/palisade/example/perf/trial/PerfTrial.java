/*
 * Copyright 2020 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.palisade.example.perf.trial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.example.perf.analysis.PerfFileSet;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Abstract superclass for all performance trials.
 */
public abstract class PerfTrial {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerfTrial.class);

    /**
     * Normal trial name.
     */
    protected String normal = "";

    /**
     * Returns the name for this performance test.
     *
     * @return test name
     */
    public abstract String name();

    /**
     * Provides a one line description of this performance test.
     *
     * @return the usage line
     */
    public abstract String description();

    /**
     * Consume a pair of {@link PerfFileSet}s, the first for resources with a policy attached, the second without.
     * Run the appropriate trial, this method will be timed so any set-up and tear-down should be in the appropriate
     * methods to ensure meaningful and useful results (we don't want to be timing JVM object constructor times etc...)
     *
     * @param fileSet     a collection of resources in a location that does not have any attached policies in palisade
     * @param noPolicySet a collection of resources in a location that does have a number of attached policies
     */
    public abstract void runTrial(PerfFileSet fileSet, PerfFileSet noPolicySet);

    /**
     * Provide a trial instance to normalise performance times to.
     *
     * @return trial instance name to normalise to
     */
    public String getNameForNormalisation() {
        return normal;
    }

    /**
     * Sets the name of the performance trial that this one should have times normalised against when reporting times.
     *
     * @param normalTrialName the trial name to normalise against
     * @return this object
     */
    public PerfTrial setNameForNormalisation(final String normalTrialName) {
        requireNonNull(normalTrialName, "normalTrialName");
        this.normal = normalTrialName;
        return this;
    }

    /**
     * Sets the performance trial that this one should have times normalised against when reporting times.
     *
     * @param trial the trial to normalise against
     * @return this object
     */
    public PerfTrial setNameForNormalisation(final PerfTrial trial) {
        requireNonNull(trial, "trial");
        return this.setNameForNormalisation(trial.name());
    }

    /**
     * Force evaluation of a stream, ie. make the data-service read-deserialise-applyRules-serialise-send
     * all data we have requested.
     * Sinks all data, does not return any (we don't make any checks that the services return the 'correct' data)
     * Evaluating the top-level stream returns open connections to the data-service to read records.
     * Evaluating the sub-streams reads records from the data-service.
     *
     * @param stream the stream to sink
     */
    public void sink(final Stream<Stream<Employee>> stream) {
        // Count gives a suggestion of whether or not the query returned a reasonable result
        // Since we are sinking all records, we can safely open connections for every resource
        // However, a flatMap here may open up concurrent connections, so try to keep streams lazily separated
        Long recordCount = stream.map(Stream::count).mapToLong(l -> l).sum();
        LOGGER.info("Sink consumed {} records", recordCount);
    }

    /**
     * Perform any setup functionality that is needed before each trial.
     *
     * @param fileSet     the file set of small and large data files
     * @param noPolicySet the file set of files with no policy set
     */
    public void setup(final PerfFileSet fileSet, final PerfFileSet noPolicySet) {
    }

    /**
     * Perform any tear down and clean functionality that is needed after each trial.
     *
     * @param fileSet     the file set of small and large data files
     * @param noPolicySet the file set of files with no policy set
     */
    public void tearDown(final PerfFileSet fileSet, final PerfFileSet noPolicySet) {
    }
}
