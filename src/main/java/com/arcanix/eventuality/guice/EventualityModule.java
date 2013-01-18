package com.arcanix.eventuality.guice;

import java.util.ArrayList;
import java.util.List;

import com.arcanix.eventuality.EventDispatcher;
import com.arcanix.eventuality.Instantiator;
import com.arcanix.eventuality.conf.EventualityUnit;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author ricardjp@arcanix.com (Jean-Philippe Ricard)
 */
public abstract class EventualityModule extends AbstractModule {

	private final List<EventualityUnit> modules = new ArrayList<>();
	
	protected abstract void configureEvents();

	protected void install(final EventualityUnit eventualityModule) {
		this.modules.add(eventualityModule);
	}
	
	@Override
	protected final void configure() {
		configureEvents();
		bind(Instantiator.class).to(GuiceInstantiator.class).in(Singleton.class);
		bind(EventualityUnit[].class).toInstance(this.modules.toArray(new EventualityUnit[0]));
		bind(EventDispatcher.class).toProvider(EventDispatcherProvider.class).in(Singleton.class);
	}
		
}
