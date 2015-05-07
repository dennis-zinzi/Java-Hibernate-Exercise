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

import uk.ac.ncl.cs.csc2024.route.Route;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
        @NamedQuery(name = Operator.SELECT_ALL, query = "SELECT o FROM Operator o ORDER BY o.name ASC")
})
@Table(name = "Operator")
public class Operator {

    public static final String SELECT_ALL =  "Operator.selectAll";
    
    @Id @Column(name = "OperatorName")
	private String name;
    
    @NotNull
    @Column(name = "Street")
    private String street;
    
    @NotNull
    @Column(name = "Town")
    private String town;
    
    @NotNull
    @Column(name = "Postcode")
    private String postcode;
    
    @NotNull
    @Column(name = "Phone")
    private String phone;
    
    @NotNull
    @Column(name = "Email")
    private String email;
    

    @ManyToMany(
    		targetEntity = Route.class)
    @JoinTable(
    		name = "Operates",
    		joinColumns = @JoinColumn(name = "OperatorName"),
    		inverseJoinColumns = @JoinColumn(name = "RouteNumber"))
    private Set<Route> operatorRoutes = new HashSet<Route>();


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getTown() {
		return town;
	}


	public void setTown(String town) {
		this.town = town;
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Set<Route> getRoutes() {
		return operatorRoutes;
	}


	public void setOperatorRoutes(Set<Route> operatorRoutes) {
		this.operatorRoutes = operatorRoutes;
	}

}
