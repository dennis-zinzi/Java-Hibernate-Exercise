/**
 * csc2024-hibernate-assignment
 *
 * Copyright (c) 2015 Newcastle University
 * Email: <h.firth@ncl.ac.uk/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.ac.ncl.cs.csc2024.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * A FileUtils class for reading sample rows from files in a limited csv format.
 *
 * **DO NOT EDIT**
 *
 * @author hugofirth
 */
public final class FileUtils {
    public static List<Map<String, String>> readSimpleCSV(Path csv) throws IOException{
        Queue<String> lines = new LinkedList<>(Files.readAllLines(csv, Charset.defaultCharset()));
        List<String> keys = Arrays.asList(lines.poll().split(","));
        if(keys.isEmpty())
        {
            throw new IllegalArgumentException("The csv at "+csv.toString()+" is of an unrecognised format and could " +
                    "not be parsed");
        }
        List<Map<String, String>> rows = new LinkedList<>();
        while(lines.peek() != null)
        {
            List<String> entries = Arrays.asList(lines.poll().split(","));
            if(entries.size() != keys.size())
            {
                throw new IllegalArgumentException("The csv at "+csv.toString()+" is of an unrecognised format and " +
                        "could not be parsed");
            }
            Map<String, String> row = new HashMap<>();
            for (int i=0; i<keys.size(); i++) {
                row.put(keys.get(i), entries.get(i));
            }
            rows.add(row);
        }
        return rows;
    }
}
