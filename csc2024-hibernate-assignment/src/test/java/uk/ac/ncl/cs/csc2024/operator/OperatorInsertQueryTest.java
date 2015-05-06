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

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.*;
import uk.ac.ncl.cs.csc2024.OperatorPredicates;
import uk.ac.ncl.cs.csc2024.PredicateNotSatisfiedException;
import uk.ac.ncl.cs.csc2024.util.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Class containing tests of the Operator insert queries.
 *
 * **DO NOT EDIT** Except to add and remove @Ignore annotations if you wish to run certain tests in isolation.
 * **REMEMBER** to remove any @Ignore annotations before submission!
 *
 * @author hugofirth
 */
public class OperatorInsertQueryTest {

    private static SessionFactory sessionFactory;

    private Session session;
    private List<Map<String, String>> rows;

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
        URL csvUrl = getClass().getResource("/operators.csv");
        Path csvPath = Paths.get(csvUrl.toURI());
        rows = FileUtils.readSimpleCSV(csvPath);
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
            for (Map<String, String> row : rows) {
                OperatorQueries.insert(row, session);
            }
            session.getTransaction().commit();
            session.beginTransaction();
            Query query = OperatorQueries.selectAll().getQuery(session);
            assertTrue(OperatorPredicates.INSERT_CORRECT_ROWS.isSatisfied(query));
            session.getTransaction().commit();
        } catch (PredicateNotSatisfiedException e) {
            session.getTransaction().rollback();
            fail("The test failed because the inserted rows did not match those expected: " + e.getMessage());
        } catch(Exception e) {
            session.getTransaction().rollback();
            fail("An exception was thrown because some of the Operator rows could not be inserted into the " +
                    "database! " + e.getMessage());
        }
    }
}
