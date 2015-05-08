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
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Operators;

import uk.ac.ncl.cs.csc2024.busstop.BusStop;
import uk.ac.ncl.cs.csc2024.operator.Operator;
import uk.ac.ncl.cs.csc2024.query.ExampleQuery;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Collection of Queries relating to Route entities
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
public class RouteQueries {

    public static Session insert(final Map<String, String> row, final Session session) {
        //Create new Route object to be persisted
    	Route r = new Route();
        
    	//Assign Route's appropriate fields
        r.setRouteNumber(row.get("number"));
        r.setBussesPerHour(Integer.parseInt(row.get("frequency")));
        
        //r.setStartID(Integer.parseInt(row.get("start")));
        //r.setDestinationID(Integer.parseInt(row.get("destination")));
        
        //Create new BusStop to be mapped with Route's start
        BusStop start = new BusStop();
        start.setID(Integer.parseInt(row.get("start")));
        r.setStart(start);
        
        //Create new BusStop to be mapped with Route's destination
        BusStop destination = new BusStop();
        destination.setID(Integer.parseInt(row.get("destination")));
        r.setDestination(destination);
        
        //Get set of strings from operator field, in case more than one present
        Set<String> routeOperatorsStrings = new HashSet<String>(Arrays.asList(row.get("operators").split("\\|")));
        //Create new Set of Operators
        Set<Operator> routeOperators = new HashSet<Operator>();
        //Assign each operator a given name in the String Set
        for(String s:routeOperatorsStrings){
        	Operator o = new Operator();
        	o.setName(s);
        	//Add Operator to Operator Set
        	routeOperators.add(o);
        }
        //Assign Operator Set to relationship with Route
        r.setRouteOperators(routeOperators);
        
        //Save Route Object
        session.save(r);
    	
    	return session;
    }

    public static ExampleQuery selectAll() {
        return new ExampleQuery() {
            @Override
            public Query getQuery(Session session) {
                return session.createQuery("select r from Route r order by r.routeNumber asc");
            }

            @Override
            public String getNamedQueryName() {
                return Route.SELECT_ALL;
            }

            @Override
            public Criteria getCriteria(Session session) {
                Criteria criteria = session.createCriteria(Route.class, "r");
                criteria.addOrder(Order.asc("r.number"));
                return criteria;
            }
        };
    }


    public static ExampleQuery selectAllForRailwayStation() {
        return new ExampleQuery() {
            @Override
            public Query getQuery(Session session) {
//SELECT r.* FROM Route r,BusStop b WHERE b.Description = 'Railway Station' AND (r.StartID = b.ID OR r.DestinationID = b.ID)
                return session.createQuery("SELECT r FROM Route r, BusStop b WHERE b.description = 'Railway Station' "
                		+ "AND (r.start = b.ID OR r.destination = b.ID)");
            }

            @Override
            public String getNamedQueryName() {
                return Route.SELECT_ALL_FOR_RAILWAY_STATION;
            }

            @Override
            public Criteria getCriteria(Session session) {
                return null;
            }
        };
    }

    public static ExampleQuery cumulativeFrequencyByOkTravel() {
        return new ExampleQuery() {
            @Override
            public Query getQuery(Session session) {
//SELECT SUM(r.bussesPerHour) FROM Route r, Operates op WHERE op.name = 'OK Travel' AND r.routeNumber = op.routeNumber
                return session.createQuery("SELECT SUM(r.bussesPerHour/r.routeOperators.size) FROM Route r JOIN r.routeOperators ro "
                		+ "WHERE ro.name = 'OK Travel'");
            }

            @Override
            public String getNamedQueryName() {
                return Route.CUMULATIVE_FREQUNCY_BY_OK_TRAVEL;
            }

            @Override
            public Criteria getCriteria(Session session) {
            	return null;
            }
        };
    }


}
