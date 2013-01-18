eventuality-guice
=================

Guice extension for Eventuality

Usage
-----

<code>
	public class MyEventualityUnit extends AbstractEventualityUnit {
		
		@Override
		public void configure() {
			declare(MyEvent.class);
			on(MyEvent.class).bind(MyEventListener.class);
		}
	}
	
	public static void main(String[] args) {
	
		Injector injector = Guice.createInjector(new EventualityModule() {
			
			@Override
			protected void configureEvents() {
				install(new MyEventualityUnit());
				bind(String.class).toInstance("Foo bar!");
			}
		});
		
		EventDispatcher dispatcher = injector.getInstance(EventDispatcher.class);
		
		dispatcher.dispatch(MyEvent.class).onMyEvent();
	}
</code>