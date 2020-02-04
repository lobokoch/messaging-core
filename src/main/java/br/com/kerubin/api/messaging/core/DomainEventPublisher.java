package br.com.kerubin.api.messaging.core;

public interface DomainEventPublisher {
	
	void publish(String topicName, String routingKey, DomainEventEnvelope<?> envelope);

	void publish(DomainEventEnvelope<DomainEvent> envelope);

	void publish(DomainEventEnvelope<DomainEvent> envelope, String topicPreffix);

	void publish(DomainMessage message);

	void publish(DomainMessage message, String routing);
	
	void publish(String topicName, String routingKey, DomainMessage message);

}
