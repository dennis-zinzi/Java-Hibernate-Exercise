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
package uk.ac.ncl.cs.csc2024.route;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.*;
import uk.ac.ncl.cs.csc2024.OperatorPredicates;
import uk.ac.ncl.cs.csc2024.PredicateNotSatisfiedException;
import uk.ac.ncl.cs.csc2024.RoutePredicates;
import uk.ac.ncl.cs.csc2024.SelectQueriesTestSuite;
import uk.ac.ncl.cs.csc2024.query.ExampleQuery;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Class containing tests of the Select queries relating to Route entries in the database.
 *
 * Should be executed as part of the SelectQueriesTestSuite only.
 *
 * **DO NOT EDIT** Except to add and remove @Ignore annotations if you wish to run certain tests in isolation.
 * **REMEMBER** to remove any @Ignore annotations before submission!
 *
 * @author hugofirth
 */
public class RouteSelectQueryTest {

    private Session session;

    @Before
    public void setUp() {
        session = SelectQueriesTestSuite.sessionFactory.getCurrentSession();
    }

    @Test
    public void testSelectAllForRailwayStation() {
        try {
            session.beginTransaction();
            ExampleQuery q = RouteQueries.selectAllForRailwayStation();
            Query query  = q.getQuery(session);
            Query namedQuery = session.getNamedQuery(q.getNamedQueryName());
            Criteria criteria = q.getCriteria(session);
            assertTrue(RoutePredicates.SELECT_ALL_FOR_RAILWAY_STATION.isSatisfied(query));
            assertTrue(RoutePredicates.SELECT_ALL_FOR_RAILWAY_STATION.isSatisfied(namedQuery));
            assertTrue(RoutePredicates.SELECT_ALL_FOR_RAILWAY_STATION.isSatisfied(criteria));
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
    public void testCumulativeFrequencyByOkTravel() {
        try {
            session.beginTransaction();
            ExampleQuery q = RouteQueries.cumulativeFrequencyByOkTravel();
            Query query  = q.getQuery(session);
            Query namedQuery = session.getNamedQuery(q.getNamedQueryName());
            Criteria criteria = q.getCriteria(session);
            assertTrue(RoutePredicates.CUMULATIVE_FREQ_BY_OK_TRAVEL.isSatisfied(query));
            assertTrue(RoutePredicates.CUMULATIVE_FREQ_BY_OK_TRAVEL.isSatisfied(namedQuery));
            assertTrue(RoutePredicates.CUMULATIVE_FREQ_BY_OK_TRAVEL.isSatisfied(criteria));
            session.getTransaction().commit();
        } catch (PredicateNotSatisfiedException e) {
            session.getTransaction().rollback();
            fail("The test failed because one or more of the queries did not satisfy the requirements: " + e.getMessage());
        } catch(Exception e) {
            session.getTransaction().rollback();
            fail("There was some unknown error with the test. " + e.toString());
        }
    }
}
