package com.oracle.poc.coherence.aop;

import com.oracle.poc.coherence.model.Employee;
import com.tangosol.coherence.federation.ChangeRecord;
import com.tangosol.coherence.federation.ChangeRecordUpdater;
import com.tangosol.coherence.federation.events.AbstractFederatedInterceptor;
import com.tangosol.net.events.annotation.Interceptor;
import com.tangosol.net.events.federation.FederatedChangeEvent;

@Interceptor(identifier = "EmpConflictHandler", federatedChangeEvents = FederatedChangeEvent.Type.COMMITTING_REMOTE)
public class EmpComittingRemoteEventsInterceptor extends AbstractFederatedInterceptor<String, Employee> {
	public EmpComittingRemoteEventsInterceptor() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tangosol.coherence.federation.events.AbstractFederatedInterceptor#
	 * getChangeRecordUpdater()
	 */
	@Override
	public ChangeRecordUpdater<String, Employee> getChangeRecordUpdater() {
		return new ChangeRecordUpdater<String, Employee>() {
			@Override
			public void update(String participant, String cacheName, ChangeRecord<String, Employee> changeRecord) {
				System.out.println("*** Commiting Remote:: Participant: " + participant + " | Cache: " + cacheName
						+ " | Key: " + changeRecord.getKey());
				System.out.println(
						"*** Commiting Remote:: Original Entity: " + changeRecord.getOriginalEntry().getValue());
				System.out.println("*** Commiting Remote:: Local Entity: " + changeRecord.getLocalEntry().getValue());
				System.out.println(
						"*** Commiting Remote:: Modified Entity: " + changeRecord.getModifiedEntry().getValue());
			}
		};
	}
}
