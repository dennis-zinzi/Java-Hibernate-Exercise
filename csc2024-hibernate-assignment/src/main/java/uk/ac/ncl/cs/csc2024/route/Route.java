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
	private int routeNumber;

	@NotNull
	@ManyToOne
	//@Column(name = "StartID")
	private BusStop start;

	@NotNull
	@ManyToOne
	//@Column(name = "DestinationID")
	private BusStop end;

	@NotNull
	@Column(name = "Frequency")
	private int bussesPerHour;

	@ManyToMany(
			targetEntity = Operator.class)
	@JoinTable(
			name = "Operates",
			joinColumns = @JoinColumn(name = "RouteNumber"),
			inverseJoinColumns = @JoinColumn(name = "OperatorName"))
	private Set<Operator> routeOperators = new HashSet<Operator>();
}
