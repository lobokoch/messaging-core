package br.com.kerubin.api.messaging.core;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainEntityEventsPublisher  {
	
	public static final String ENTITY_EVENTS_TOPIC_NAME = "kerubin.entity.events.topic";
	public static final String ENTITY_EVENTS = "entity.events";
	public static final String TOPIC_PREFFIX = "topic";
	
	@Autowired
	private DomainEventPublisher domainEventPublisher;
	
	public void publish(DomainEventEnvelope<DomainEvent> envelope) {
		/*String routingKey = MessageFormat.format("{0}.{1}.{2}.{3}", envelope.getApplication(), 
				envelope.getDomain(), envelope.getService(), ENTITY_EVENTS);*/
		
		String baseRoutingKey = MessageFormat.format("{0}.{1}.{2}", envelope.getApplication(), 
				envelope.getDomain(), envelope.getService());
		
		StringBuilder routingKey = new StringBuilder(baseRoutingKey);
		if (envelope.getKey() != null && !envelope.getKey().isEmpty()) {
			routingKey.append(".").append(envelope.getKey());
		}
		
		String topicName = MessageFormat.format("{0}_{1}_{2}_{3}", 
				envelope.getApplication(), envelope.getDomain(), envelope.getService(), TOPIC_PREFFIX);
				
		//domainEventPublisher.publish(ENTITY_EVENTS_TOPIC_NAME, routingKey.toString(), envelope);
		domainEventPublisher.publish(topicName, routingKey.toString(), envelope);
	}

}
