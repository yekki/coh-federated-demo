package com.oracle.poc.coherence.loadbalancer;

import java.util.Comparator;

import com.tangosol.net.Member;
import com.tangosol.net.proxy.DefaultProxyServiceLoadBalancer;

public class MyProxyServiceLoadBalancer extends DefaultProxyServiceLoadBalancer {
	// private static final String CLUSTER_A = "ClusterA";
	private static final String CLUSTER_B = "ClusterB";

	public MyProxyServiceLoadBalancer() {
		super(new Comparator<Member>() {
			@Override
			public int compare(Member m1, Member m2) {
				if (m1.getClusterName().equals(m2.getClusterName())) {
					return 0;
				} else if (m1.getClusterName().equals(CLUSTER_B)) {
					return -1;
				} else if (m2.getClusterName().equals(CLUSTER_B)) {
					return 1;
				} else {
					return 0;
				}
			}
		});
	}
}
