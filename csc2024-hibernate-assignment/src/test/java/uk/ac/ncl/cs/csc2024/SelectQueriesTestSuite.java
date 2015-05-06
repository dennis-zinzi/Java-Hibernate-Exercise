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
package uk.ac.ncl.cs.csc2024;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import uk.ac.ncl.cs.csc2024.busstop.BusStopQueries;
import uk.ac.ncl.cs.csc2024.busstop.BusStopSelectQueryTest;
import uk.ac.ncl.cs.csc2024.operator.OperatorQueries;
import uk.ac.ncl.cs.csc2024.operator.OperatorSelectQueryTest;
import uk.ac.ncl.cs.csc2024.route.RouteQueries;
import uk.ac.ncl.cs.csc2024.route.RouteSelectQueryTest;
import uk.ac.ncl.cs.csc2024.util.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * A suite of tests to verify that the provided Select queries meet the specified requirements.
 *
 * **DO NOT EDIT**
 *
 * @author hugofirth
 */
@RunWith(Suite.class)
@SuiteClasses({BusStopSelectQueryTest.class, OperatorSelectQueryTest.class, RouteSelectQueryTest.class})
public class SelectQueriesTestSuite {

    public static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @BeforeClass
    public static void setUpClass() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            URL operatorsCsvUrl = SelectQueriesTestSuite.class.getResource("/operators.csv");
            Path operatorsCsvPath = Paths.get(operatorsCsvUrl.toURI());
            List<Map<String, String>> operatorsRows = FileUtils.readSimpleCSV(operatorsCsvPath);
            URL busStopsCsvUrl = SelectQueriesTestSuite.class.getResource("/busstops.csv");
            Path busStopsCsvPath = Paths.get(busStopsCsvUrl.toURI());
            List<Map<String, String>> busStopsRows = FileUtils.readSimpleCSV(busStopsCsvPath);
            URL routesCsvUrl = SelectQueriesTestSuite.class.getResource("/routes.csv");
            Path routesCsvPath = Paths.get(routesCsvUrl.toURI());
            List<Map<String, String>> routesRows = FileUtils.readSimpleCSV(routesCsvPath);
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
        } catch(Exception e) {
            session.getTransaction().rollback();
            Assert.fail("An exception was thrown because some of the entries could not be inserted into the " +
                    "database! " + e.getMessage());
        }
    }

    @AfterClass
    public static void tearDownClass() {
        sessionFactory.close();
    }
}
