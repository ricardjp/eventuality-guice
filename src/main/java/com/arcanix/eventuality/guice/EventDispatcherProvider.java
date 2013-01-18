package com.arcanix.eventuality.guice;

import javax.inject.Inject;

import com.arcanix.eventuality.EventDispatcher;
import com.arcanix.eventuality.Events;
import com.arcanix.eventuality.Instantiator;
import com.arcanix.eventuality.conf.EventualityUnit;
import com.arcanix.eventuality.internal.EventCollector;
import com.google.inject.Provider;

/**
 * @author ricardjp@arcanix.com (Jean-Philippe Ricard)
 */
public final class EventDispatcherProvider implements Provider<EventDispatcher> {

	private final Instantiator instantiator;
	private final EventualityUnit[] modules;
	
	@Inject
	public EventDispatcherProvider(final Instantiator instantiator, final EventualityUnit[] modules) {
		this.instantiator = instantiator;
		this.modules = modules;
	}
	
	@Override
	public EventDispatcher get() {
		EventDispatcher dispatcher = new EventDispatcher(this.instantiator);
		EventCollector eventCollector = new EventCollector();
		Events events = new Events(eventCollector);
		for (EventualityUnit eventualityModule : this.modules) {
			eventualityModule.configure(events);
		}
		eventCollector.configureDispatcher(dispatcher);
		return dispatcher;
	}
	
}
