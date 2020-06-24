/*
 * Copyright 2018 Crown Copyright
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

import org.springframework.stereotype.Component;

import uk.gov.gchq.palisade.data.serialise.AvroSerialiser;
import uk.gov.gchq.palisade.data.serialise.Serialiser;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;
import uk.gov.gchq.palisade.example.perf.analysis.PerfFileSet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * This test performs a native file read of small file in the 1st file set. This is done without going via Palisade, but
 * does try to deserialise the data.
 */
@Component
public class ReadSmallNativeTrial extends PerfTrial {
    public String name() {
        return "read_small_native";
    }

    public String description() {
        return "performs a native read and deserialise of the small file";
    }

    public void accept(final PerfFileSet fileSet, final PerfFileSet noPolicySet) {
        requireNonNull(fileSet, "fileSet");
        requireNonNull(noPolicySet, "noPolicySet");

        //create the serialiser
        Serialiser<Employee> serialiser = new AvroSerialiser<>(Employee.class);

        //read from file
        try (InputStream bis = Files.newInputStream(fileSet.smallFile);
             Stream<Employee> dataStream = serialiser.deserialise(bis)) {

            //now read everything in the file
            sink(dataStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
