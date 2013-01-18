package com.arcanix.eventuality.guice;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;

import com.arcanix.eventuality.EventDispatcher;
import com.arcanix.eventuality.conf.AbstractEventualityUnit;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author ricardjp@arcanix.com (Jean-Philippe Ricard)
 */
public final class TestUsage {

	public static interface ThreadStartEvent {
		void onThreadStart(EventParameter parameter);
	}
	
	public static class EventParameter {
		
		private String result;
		
		public void setResult(final String result) {
			this.result = result;
		}
		
		public String getResult() {
			return this.result;
		}
		
	}
	
	public static class ThreadStartEventListener implements ThreadStartEvent {
		
		private final String injected;
		
		@Inject
		public ThreadStartEventListener(final String injected) {
			this.injected = injected;
		}
		
		@Override
		public void onThreadStart(final EventParameter parameter) {
			parameter.setResult("called: " + this.injected);
		}
		
	}
	
	public static final class MyEventualityUnit extends AbstractEventualityUnit {
		
		@Override
		public void configure() {
			declare(ThreadStartEvent.class);
			on(ThreadStartEvent.class).bind(ThreadStartEventListener.class);
		}
	}
	
	@Test
	public void testUsage() {
		Injector injector = Guice.createInjector(new EventualityModule() {
			
			@Override
			protected void configureEvents() {
				install(new MyEventualityUnit());
				bind(String.class).toInstance("Foo bar!");
			}
		});
		EventDispatcher dispatcher = injector.getInstance(EventDispatcher.class);
		
		EventParameter parameter = new EventParameter();
		
		dispatcher.dispatch(ThreadStartEvent.class).onThreadStart(parameter);
		
		// ensure the listener was called and successfully injected
		assertEquals("called: Foo bar!", parameter.getResult());
	}
	
}
