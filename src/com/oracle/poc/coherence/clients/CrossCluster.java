/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.poc.coherence.clients;

import static com.tangosol.net.cache.TypeAssertion.withTypes;

import java.util.Collection;

import com.oracle.poc.coherence.model.Employee;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.filter.AlwaysFilter;

/***************************************************************************
 * <PRE>
 *  Project Name    : FederatedCachingDemo
 * 
 *  Package Name    : com.oracle.poc.coherence.clients
 * 
 *  File Name       : CrossCluster.java
 * 
 *  Creation Date   : 2016Äê7ÔÂ10ÈÕ
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
public class CrossCluster {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NamedCache<String, Employee> cache = CacheFactory.getTypedCache("Employee",
				withTypes(String.class, Employee.class));
		System.out.println("*** Employee cache size: " + cache.size());
		Collection<Employee> results = cache.values(AlwaysFilter.INSTANCE);
		if (results != null) {
			System.out.println("*** Details: ");
			for (Employee emp : results) {
				System.out.println("*** Emp: " + emp);
			}
		}
	}
}
