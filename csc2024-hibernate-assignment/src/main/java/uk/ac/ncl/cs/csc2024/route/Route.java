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

import uk.ac.ncl.cs.csc2024.busstop.BusStop;
import uk.ac.ncl.cs.csc2024.operator.Operator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Hibernate Operator Entity
 *
 * Task: Create fields, methods and annotations which implicitly define an appropriate database table schema for
 * Operator records.
 *
 * @author hugofirth
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Route.SELECT_ALL, query = "SELECT r FROM Route r ORDER BY r.routeNumber ASC")
})
@Table(name = "Route")
public class Route {
	public static final String SELECT_ALL = "Route.selectAll";

	@Id @Column(name = "RouteNumber")
	private String routeNumber;

	@NotNull
	@Column(name = "StartID")
	private int startID;

	@NotNull
	@Column(name = "DestinationID")
	private int destinationID;
	
	@NotNull
	@Column(name = "Frequency")
	private int bussesPerHour;
	
	
	

	@ManyToOne
	@JoinColumn(name = "StartIDJoin")
	private BusStop start;


	@ManyToOne
	@JoinColumn(name = "DestinationIDJoin")
	private BusStop end;


	@ManyToMany(
			targetEntity = Operator.class)
	@JoinTable(
			name = "Operates",
			joinColumns = @JoinColumn(name = "RouteNumber"),
			inverseJoinColumns = @JoinColumn(name = "OperatorName"))
	private Set<Operator> routeOperators = new HashSet<Operator>();
	
	
	
	public void setRouteNumber(String routeNumber){
		this.routeNumber = routeNumber;
	}
	
	public void setStart(BusStop start){
		this.start = start;
	}
	
	public void setDestionation(BusStop end){
		this.end = end;
	}
	
	public void setBussesPerHour(int bussesPerHour){
		this.bussesPerHour = bussesPerHour;
	}
	
	public String getRouteNumber(){
		return routeNumber;
	
	}
	public BusStop getStart(){
		return start;
	}
	
	public BusStop getEnd(){
		return end;
	}
	
	public int getBussesPerHour(){
		return bussesPerHour;
	}
	
	
	
	public int getStartID() {
		return startID;
	}

	public void setStartID(int startID) {
		this.startID = startID;
	}

	public int getDestinationID() {
		return destinationID;
	}

	public void setDestinationID(int destinationID) {
		this.destinationID = destinationID;
	}
	
	
	
	
	public void setRouteOperators(Set<Operator> routeOperators){
		this.routeOperators = routeOperators;
	}
	
	public Set<Operator> getRouteOperators(){
		return routeOperators;
	}
}
