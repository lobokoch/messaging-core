package br.com.kerubin.api.messaging.core;

public interface DomainEventPublisher {
	
	void publish(String topicName, String routingKey, DomainEventEnvelope<?> envelope);

}
