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

package uk.gov.gchq.palisade.example.perf.trial.small;

import org.springframework.stereotype.Component;

import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.example.perf.analysis.PerfFileSet;
import uk.gov.gchq.palisade.example.perf.trial.PalisadeTrial;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Sets up a data request through Palisade, but doesn't read any data back.
 */
@Component
public class RequestSmallNoPolicyTrial extends PalisadeTrial {
    protected static final String NAME = "request_small_no_policy";

    public RequestSmallNoPolicyTrial(final Function<String, Stream<Stream<Employee>>> client) {
        super(client);
        normal = NAME;
    }

    public String name() {
        return NAME;
    }

    public String description() {
        return "makes a request for the small file with no policy set without reading data";
    }

    public void runTrial(final PerfFileSet fileSet, final PerfFileSet noPolicySet) {
        try (Stream<Stream<Employee>> ignored = getDataStream(noPolicySet.smallFile)) {
            //do nothing
        }
    }
}
