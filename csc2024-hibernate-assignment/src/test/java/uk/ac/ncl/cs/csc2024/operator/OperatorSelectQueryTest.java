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
package uk.ac.ncl.cs.csc2024.operator;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.*;
import uk.ac.ncl.cs.csc2024.OperatorPredicates;
import uk.ac.ncl.cs.csc2024.PredicateNotSatisfiedException;
import uk.ac.ncl.cs.csc2024.SelectQueriesTestSuite;
import uk.ac.ncl.cs.csc2024.query.ExampleQuery;

import static org.junit.Assert.*;

/**
 * Class containing tests of the Select queries relating to Operator entries in the database.
 *
 * Should be executed as part of the SelectQueriesTestSuite only.
 *
 * **DO NOT EDIT** Except to add and remove @Ignore annotations if you wish to run certain tests in isolation.
 * **REMEMBER** to remove any @Ignore annotations before submission!
 *
 * @author hugofirth
 */
public class OperatorSelectQueryTest {

    private Session session;

    @Before
    public void setUp() {
        session = SelectQueriesTestSuite.sessionFactory.getCurrentSession();
    }

    @Test
    public void testSelectAllRoutesByDiamondBuses() {
        try {
            session.beginTransaction();
            ExampleQuery q = OperatorQueries.selectAllRoutesByDiamondBuses();
            Query query = q.getQuery(session);
            Query namedQuery = session.getNamedQuery(q.getNamedQueryName());
            Criteria criteria = q.getCriteria(session);
            assertTrue(OperatorPredicates.SELECT_ALL_BY_DIAMOND_BUSES.isSatisfied(query));
            assertTrue(OperatorPredicates.SELECT_ALL_BY_DIAMOND_BUSES.isSatisfied(namedQuery));
            assertTrue(OperatorPredicates.SELECT_ALL_BY_DIAMOND_BUSES.isSatisfied(criteria));
            session.getTransaction().commit();
        } catch (PredicateNotSatisfiedException e) {
            session.getTransaction().rollback();
            fail("The test failed because one or more of the queries did not satisfy the requirements: " + e.getMessage());
        } catch(Exception e) {
            session.getTransaction().rollback();
            fail("There was some unknown error with the test. " + e.getMessage());
        }
    }

    @Test
    public void testSelectAllForParkGates() {
        try {
            session.beginTransaction();
            ExampleQuery q = OperatorQueries.selectAllForParkGates();
            Query query  = q.getQuery(session);
            Query namedQuery = session.getNamedQuery(q.getNamedQueryName());
            Criteria criteria = q.getCriteria(session);
            assertTrue(OperatorPredicates.SELECT_ALL_FOR_PARK_GATES.isSatisfied(query));
            assertTrue(OperatorPredicates.SELECT_ALL_FOR_PARK_GATES.isSatisfied(namedQuery));
            assertTrue(OperatorPredicates.SELECT_ALL_FOR_PARK_GATES.isSatisfied(criteria));
            session.getTransaction().commit();
        } catch (PredicateNotSatisfiedException e) {
            session.getTransaction().rollback();
            fail("The test failed because one or more of the queries did not satisfy the requirements: " + e.getMessage());
        } catch(Exception e) {
            session.getTransaction().rollback();
        }
    }
}
