package com.arcanix.eventuality.guice;

import javax.inject.Inject;

import com.arcanix.eventuality.Instantiator;
import com.google.inject.Injector;

/**
 * @author ricardjp@arcanix.com (Jean-Philippe Ricard)
 */
public final class GuiceInstantiator implements Instantiator {

	private final Injector injector;
	
	@Inject
	public GuiceInstantiator(final Injector injector) {
		this.injector = injector;
	}
	
	@Override
	public <T> T instantiate(final Class<T> eventListenerClass) {
		return this.injector.getInstance(eventListenerClass);
	}
	
}
