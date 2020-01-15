package br.com.kerubin.api.messaging.core;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainEntityEventsPublisherImpl implements DomainEntityEventsPublisher {
	
	@Autowired
	private DomainEventPublisher domainEventPublisher;
	
	@Override
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
				envelope.getApplication(), envelope.getDomain(), envelope.getService(), DomainEntityEventsPublisher.TOPIC_PREFFIX);
				
		//domainEventPublisher.publish(ENTITY_EVENTS_TOPIC_NAME, routingKey.toString(), envelope);
		domainEventPublisher.publish(topicName, routingKey.toString(), envelope);
	}

}
