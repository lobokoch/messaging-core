package br.com.kerubin.api.messaging.core;

import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_TENANT;
import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_USER;
import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_TENANT_ACCOUNT_TYPE;
import static br.com.kerubin.api.messaging.utils.DomainEventUtils.checkIsNotEmpty;
import static br.com.kerubin.api.messaging.utils.DomainEventUtils.mountRoutingKey;
import static br.com.kerubin.api.messaging.utils.DomainEventUtils.mountTopicName;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DomainEventPublisherImpl implements DomainEventPublisher {
	
	@Autowired
	private RabbitTemplate template;
	
	@Override
	public void publish(DomainMessage message) {
		publish(message, null);
	}
	
	@Override
	public void publish(DomainMessage message, String routing) {
		String routingKey = mountRoutingKey(message.getDomain(), message.getService(), message.getPrimitive(), routing);
		String topicName = mountTopicName(message.getDomain(), message.getService());
				
		publish(topicName, routingKey, message);
	}
	
	@Override
	public void publish(String topicName, String routingKey, DomainMessage message) {
		//org.springframework.amqp.core.Message
		// Convert a Java object to an Amqp Message and send it to a specific exchange with a specific routing key.
		// The consumers will bind each queue to the exchange by one or more rounting key. Can use * or # for routing keys
		// exchange type = topic, can use "*" or "#":
		// * (star) can substitute for exactly one word.
		// # (hash) can substitute for zero or more words.
		// *.orange.*
		// *.*.rabbit
		// lazy.#
		
		validate(topicName, routingKey, message);
		
		template.convertAndSend(topicName, routingKey, message, rabbitMessage -> {
			rabbitMessage.getMessageProperties().getHeaders().put(HEADER_TENANT, message.getTenant());
			rabbitMessage.getMessageProperties().getHeaders().put(HEADER_USER, message.getUser());
			rabbitMessage.getMessageProperties().getHeaders().put(HEADER_TENANT_ACCOUNT_TYPE, message.getTenantAccountType());
			return rabbitMessage;
		});
		
		log.info("Published message at topic: {} routingKey: {}, envelope: {}", topicName, routingKey, message);
	}
	
	private void validate(String topicName, String routingKey, DomainMessage message) {
		checkIsNotEmpty(topicName, "topicName is null or empty.");
		checkIsNotEmpty(routingKey, "routingKey is null or empty.");
		checkIsNotEmpty(message, "message cannot be null.");
		checkIsNotEmpty(message.getApplication(), "message.application is null or empty.");
		checkIsNotEmpty(message.getDomain(), "message.domain is null or empty.");
		checkIsNotEmpty(message.getService(), "message.service is null or empty.");
		checkIsNotEmpty(message.getPrimitive(), "message.primitive is null or empty.");
	}
	
	@Override
	public void publish(DomainEventEnvelope<DomainEvent> envelope) {
		publish(envelope, null);
	}
	
	@Override
	public void publish(DomainEventEnvelope<DomainEvent> envelope, String routing) {
		String routingKey = mountRoutingKey(envelope.getDomain(), envelope.getService(), envelope.getPrimitive(), routing);
		String topicName = mountTopicName(envelope.getDomain(), envelope.getService());
				
		publish(topicName, routingKey, envelope);
	}
	
	@Override
	public void publish(String topicName, String routingKey, DomainEventEnvelope<?> envelope) {
		validate(topicName, routingKey, envelope);
		
		template.convertAndSend(topicName, routingKey, envelope, message -> {
			message.getMessageProperties().getHeaders().put(HEADER_TENANT, envelope.getTenant());
			message.getMessageProperties().getHeaders().put(HEADER_USER, envelope.getUser());
			message.getMessageProperties().getHeaders().put(HEADER_TENANT_ACCOUNT_TYPE, envelope.getTenantAccountType());
			return message;
		});
		
		log.info("Published message at topic: {} routingKey: {}, envelope: {}", topicName, routingKey, envelope);
	}
	
	private void validate(String topicName, String routingKey, DomainEventEnvelope<?> envelope) {
		checkIsNotEmpty(topicName, "topicName is null or empty.");
		checkIsNotEmpty(routingKey, "routingKey is null or empty.");
		checkIsNotEmpty(envelope, "envelope is null.");
		checkIsNotEmpty(envelope.getApplication(), "envelope.application is null or empty.");
		checkIsNotEmpty(envelope.getDomain(), "envelope.domain is null or empty.");
		checkIsNotEmpty(envelope.getService(), "envelope.service is null or empty.");
		checkIsNotEmpty(envelope.getPrimitive(), "envelope.primitive is null or empty.");
	}

}
