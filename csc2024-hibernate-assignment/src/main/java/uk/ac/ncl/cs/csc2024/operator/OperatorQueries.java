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
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Query;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import uk.ac.ncl.cs.csc2024.query.ExampleQuery;
import uk.ac.ncl.cs.csc2024.route.Route;

import java.util.Map;

/**
 * Collection of Queries relating to Operator entities
 *
 * Task: fill out method bodies to create "Queries" which satisfy the requirements laid out in the coursework spec.
 * Other than the `insert(...)` method, where you should return the session to which an Entity object has been persisted,
 * you should return an ExampleQuery object.
 *
 * The methods of the ExampleQuery objects should return a Query, a Named Query's identifier and a Criteria Query for
 * each task laid out in the coursework spec. You should return every query type for each task.
 *
 * An example of how this should look is provided in the `selectAll(...)` query.
 *
 * @author hugofirth
 */
public class OperatorQueries {

    public static Session insert(final Map<String, String> row, Session session) {
        return null;
    }

    public static ExampleQuery selectAll() {
        return new ExampleQuery() {
            @Override
            public Query getQuery(Session session) {
                return session.createQuery("select o from Operator o order by o.name asc");
            }

            @Override
            public String getNamedQueryName() {
                return Operator.SELECT_ALL;
            }

            @Override
            public Criteria getCriteria(Session session) {
                Criteria criteria = session.createCriteria(Operator.class, "o");
                criteria.addOrder(Order.asc("o.name"));
                return criteria;
            }
        };
    }

    public static ExampleQuery selectAllRoutesByDiamondBuses() {
        return new ExampleQuery() {
            @Override
            public Query getQuery(Session session) {
                return null;
            }

            @Override
            public String getNamedQueryName() {
                return null;
            }

            @Override
            public Criteria getCriteria(Session session) {
                return null;
            }
        };
    }

    public static ExampleQuery selectAllForParkGates() {
        return new ExampleQuery() {
            @Override
            public Query getQuery(Session session) {
                return null;
            }

            @Override
            public String getNamedQueryName() {
                return null;
            }

            @Override
            public Criteria getCriteria(Session session) {
                return null;
            }
        };
    }


}
