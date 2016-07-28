/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.poc.coherence.model;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;

/***************************************************************************
 * <PRE>
 *  Project Name    : FederatedCachingDemo
 * 
 *  Package Name    : com.oracle.poc.coherence.model
 * 
 *  File Name       : Employee.java
 * 
 *  Creation Date   : 2016Äê7ÔÂ5ÈÕ
 * 
 *  Author          : Hysun He
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Portable
public class Employee {
	@PortableProperty(0)
	private String id;

	@PortableProperty(1)
	private String firstName;

	@PortableProperty(2)
	private String lastName;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
