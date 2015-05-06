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
package uk.ac.ncl.cs.csc2024.query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 * The interface which each object returned from a task method should implement.
 *
 * @author hugofirth
 */
public interface ExampleQuery {

    Query getQuery(Session session);
    String getNamedQueryName();
    Criteria getCriteria(Session session);

}
