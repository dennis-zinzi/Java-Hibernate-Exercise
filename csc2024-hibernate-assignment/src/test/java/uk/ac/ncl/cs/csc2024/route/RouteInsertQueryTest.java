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

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.*;
import uk.ac.ncl.cs.csc2024.PredicateNotSatisfiedException;
import uk.ac.ncl.cs.csc2024.RoutePredicates;
import uk.ac.ncl.cs.csc2024.busstop.BusStopQueries;
import uk.ac.ncl.cs.csc2024.operator.Operator;
import uk.ac.ncl.cs.csc2024.operator.OperatorQueries;
import uk.ac.ncl.cs.csc2024.util.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Class containing tests of the Route insert queries.
 *
 * **DO NOT EDIT** Except to add and remove @Ignore annotations if you wish to run certain tests in isolation.
 * **REMEMBER** to remove any @Ignore annotations before submission!
 *
 * @author hugofirth
 */
public class RouteInsertQueryTest {

    private static SessionFactory sessionFactory;

    private Session session;
    private List<Map<String, String>> routesRows;
    private List<Map<String, String>> busStopsRows;
    private List<Map<String, String>> operatorsRows;

    @BeforeClass
    public static void setUpClass() {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @AfterClass
    public static void tearDownClass() {
        if(sessionFactory != null)
        {
            sessionFactory.close();
        }
    }

    @Before
    public void setUp() throws IOException, URISyntaxException {
        URL operatorsCsvUrl = getClass().getResource("/operators.csv");
        Path operatorsCsvPath = Paths.get(operatorsCsvUrl.toURI());
        operatorsRows = FileUtils.readSimpleCSV(operatorsCsvPath);
        URL busStopsCsvUrl = getClass().getResource("/busstops.csv");
        Path busStopsCsvPath = Paths.get(busStopsCsvUrl.toURI());
        busStopsRows = FileUtils.readSimpleCSV(busStopsCsvPath);
        URL routesCsvUrl = getClass().getResource("/routes.csv");
        Path routesCsvPath = Paths.get(routesCsvUrl.toURI());
        routesRows = FileUtils.readSimpleCSV(routesCsvPath);
        session = sessionFactory.openSession();
    }

    @After
    public void tearDown() {
        if(session != null)
        {
            session.close();
        }
    }

    @Test
    public void testInsertedCorrectRows() {
        try {
            session.beginTransaction();
            for (Map<String, String> busStopRow : busStopsRows) {
                BusStopQueries.insert(busStopRow, session);
            }
            for (Map<String, String> operatorRow : operatorsRows) {
                OperatorQueries.insert(operatorRow, session);
            }
            for (Map<String, String> routeRow : routesRows) {
                RouteQueries.insert(routeRow, session);
            }
            session.getTransaction().commit();
            session.beginTransaction();
            Query query = RouteQueries.selectAll().getQuery(session);
            assertTrue(RoutePredicates.INSERT_CORRECT_ROWS.isSatisfied(query));
            session.getTransaction().commit();
        } catch (PredicateNotSatisfiedException e) {
        	e.printStackTrace();
            session.getTransaction().rollback();
            fail("The test failed because the inserted rows did not match those expected: " + e.getMessage());
        }  catch(Exception e) {
        	e.printStackTrace();
            session.getTransaction().rollback();
            Assert.fail("An exception was thrown because some of the Routes rows could not be inserted into the " +
                    "database! "+e.getMessage());
        }
    }
}
