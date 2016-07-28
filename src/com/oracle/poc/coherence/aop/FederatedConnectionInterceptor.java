/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.poc.coherence.aop;

import com.tangosol.internal.federation.service.FederatedCacheServiceDispatcher;
import com.tangosol.net.events.EventDispatcher;
import com.tangosol.net.events.EventDispatcherAwareInterceptor;
import com.tangosol.net.events.annotation.Interceptor;
import com.tangosol.net.events.federation.FederatedConnectionEvent;

/***************************************************************************
 * <PRE>
 *  Project Name    : FederatedCachingDemo
 * 
 *  Package Name    : com.oracle.poc.coherence.aop
 * 
 *  File Name       : MyConnectionInterceptor.java
 * 
 *  Creation Date   : 2016Äê7ÔÂ6ÈÕ
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
@Interceptor(identifier = "FederatedConnectionInterceptor", federatedConnectionEvents = {
		FederatedConnectionEvent.Type.BACKLOG_EXCESSIVE, FederatedConnectionEvent.Type.BACKLOG_NORMAL,
		FederatedConnectionEvent.Type.ERROR, FederatedConnectionEvent.Type.CONNECTING,
		FederatedConnectionEvent.Type.DISCONNECTED })
public class FederatedConnectionInterceptor implements EventDispatcherAwareInterceptor<FederatedConnectionEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tangosol.net.events.EventInterceptor#onEvent(com.tangosol.net.events.
	 * Event)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tangosol.net.events.EventDispatcherAwareInterceptor#
	 * introduceEventDispatcher(java.lang.String,
	 * com.tangosol.net.events.EventDispatcher)
	 */
	@Override
	public void introduceEventDispatcher(String sIdentifier, EventDispatcher dispatcher) {
		if (dispatcher instanceof FederatedCacheServiceDispatcher) {
			System.out.println("*** introduceEventDispatcher: " + sIdentifier);
			dispatcher.addEventInterceptor(sIdentifier, this);
		}
	}
}
