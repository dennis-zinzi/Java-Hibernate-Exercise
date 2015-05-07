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
package uk.ac.ncl.cs.csc2024.busstop;

import uk.ac.ncl.cs.csc2024.route.Route;

import javax.validation.constraints.NotNull;
import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Hibernate BusStop Entity
 *
 * Task: Create fields, methods and annotations which implicitly define an appropriate database table schema for
 * BusStop records.
 *
 * @author hugofirth
 */
@Entity
@NamedQueries({
	@NamedQuery(name = BusStop.SELECT_ALL, query = "SELECT b FROM BusStop b ORDER BY b.id ASC"),
	@NamedQuery(name = BusStop.MAX_ID, query = "SELECT MAX(ID) FROM BusStop")
})
@Table(name = "BusStop")
public class BusStop {

	public static final String SELECT_ALL = "BusStop.selectAll";
	public static final String MAX_ID = "BusStop.maxID";

	@Id @Column(name = "ID")
	private int ID;

	@NotNull
	@Column(name = "Description")
	private String description;
	
	public void setID(int ID){
		this.ID = ID;
	}
	public void setDescription(String description){
		this.description = description;
	}
	
	public int getId(){
		return ID;
	}
	
	public String getDescription(){
		return description;
	}

	@OneToMany(mappedBy="start")
	private Set<Route> routeStarts = new HashSet<Route>();
	
	@OneToMany(mappedBy="destination")
	private Set<Route> routeEnd = new HashSet<Route>();
	
}
