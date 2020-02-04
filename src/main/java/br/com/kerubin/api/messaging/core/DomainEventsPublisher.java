package br.com.kerubin.api.messaging.core;

public interface DomainEventsPublisher {
	
	public static final String ENTITY_EVENTS_TOPIC_NAME = "kerubin.entity.events.topic";
	public static final String ENTITY_EVENTS = "entity.events";
	public static final String TOPIC_PREFFIX = "topic";

	void publish(DomainEventEnvelope<DomainEvent> envelope);
	
}
