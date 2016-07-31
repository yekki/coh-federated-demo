package com.oracle.poc.coherence.aop;

import com.tangosol.internal.federation.service.FederatedCacheServiceDispatcher;
import com.tangosol.net.events.EventDispatcher;
import com.tangosol.net.events.EventDispatcherAwareInterceptor;
import com.tangosol.net.events.annotation.Interceptor;
import com.tangosol.net.events.federation.FederatedConnectionEvent;

@Interceptor(identifier = "FederatedConnectionInterceptor", federatedConnectionEvents = {
		FederatedConnectionEvent.Type.BACKLOG_EXCESSIVE, FederatedConnectionEvent.Type.BACKLOG_NORMAL,
		FederatedConnectionEvent.Type.ERROR, FederatedConnectionEvent.Type.CONNECTING,
		FederatedConnectionEvent.Type.DISCONNECTED })
public class FederatedConnectionInterceptor implements EventDispatcherAwareInterceptor<FederatedConnectionEvent> {

	@Override
	public void onEvent(FederatedConnectionEvent event) {
		FederatedConnectionEvent.Type type = event.getType();
		if (type.equals(FederatedConnectionEvent.Type.ERROR)) {
			System.err.println("!!! Participant in error: " + event.getParticipantName());
		} else if (type.equals(FederatedConnectionEvent.Type.BACKLOG_EXCESSIVE)) {
			System.err.println("!!! Participant in backlogged excessive state: " + event.getParticipantName());
		} else {
			System.out.println("*** FederatedConnectionEvent: " + event.getParticipantName() + " | " + event.getType());
		}
	}

	@Override
	public void introduceEventDispatcher(String sIdentifier, EventDispatcher dispatcher) {
		if (dispatcher instanceof FederatedCacheServiceDispatcher) {
			System.out.println("*** introduceEventDispatcher: " + sIdentifier);
			dispatcher.addEventInterceptor(sIdentifier, this);
		}
	}
}
