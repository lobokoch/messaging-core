package br.com.kerubin.api.messaging.core;

import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_TENANT;
import static br.com.kerubin.api.messaging.constants.MessagingConstants.HEADER_USER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisher {
	
	private static final Logger log = LoggerFactory.getLogger(DomainEventPublisher.class);
	
	@Autowired
	private RabbitTemplate template;
	
	public void publish(String topicName, String routingKey, DomainEventEnvelope<?> envelope) {
		//org.springframework.amqp.core.Message
		template.convertAndSend(topicName, routingKey, envelope, message -> {
			message.getMessageProperties().getHeaders().put(HEADER_TENANT, envelope.getTenant());
			message.getMessageProperties().getHeaders().put(HEADER_USER, envelope.getUser());
			return message;
		});
		log.info("Published message at topic: {} rounting: {}, primitive: {}", topicName, routingKey, envelope.getPrimitive());
	}

}
