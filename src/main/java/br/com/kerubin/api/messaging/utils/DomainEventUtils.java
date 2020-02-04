package br.com.kerubin.api.messaging.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.kerubin.api.messaging.core.DomainEventConstants;
import br.com.kerubin.api.messaging.exception.InvalidDtoConvertionException;
import br.com.kerubin.api.messaging.exception.NullOrEmptyValueException;

public class DomainEventUtils {
	
	public static boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}
	
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		
		if (value instanceof String) {
			return ((String) value).trim().isEmpty();
		}
		
		if (value instanceof Collection) {
			return ((Collection<?>) value).isEmpty();
		}
		
		if (value.getClass().isArray()) {
			return Arrays.asList(value).isEmpty();
		}
		
		return false;
	}
	
	public static String getRoutingKeyValue(String value) {
		return isNotEmpty(value) ? value : "*";
	}
	
	public static void checkIsNotEmpty(Object value) {
		checkIsNotEmpty(value, "Value cannot be null or empty.");
	}
	
	public static void checkIsNotEmpty(Object value, String message) {
		if (isEmpty(value)) {
			throw new NullOrEmptyValueException(message);
		}
	}
	
	
	public static String mountRoutingKey(String domain, String service, String primitive) {
		return mountRoutingKey(domain, service, primitive, null);
	}
	
	public static String mountRoutingKey(String domain, String service, String primitive, String routing) {
		String baseRoutingKey = MessageFormat.format("{0}.{1}.{2}", 
				DomainEventConstants.APPLICATION, domain, service);
		
		StringBuilder routingKey = new StringBuilder(baseRoutingKey);
		if (isNotEmpty(routing)) {
			routingKey.append(".").append(routing);
		}
		
		routingKey.append(".").append(primitive);
		
		return routingKey.toString();
	}
	
	public static String mountTopicName(String domain, String service) {
		String topicName = MessageFormat.format("{0}_{1}_{2}_{3}", 
				DomainEventConstants.APPLICATION, 
				domain, service, 
				DomainEventConstants.TOPIC_PREFFIX);
		
		return topicName;
	}
	
	public static <T> byte[] dtoToBytes(T dto) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			byte[] data = objectMapper.writeValueAsBytes(dto);
			return data;
		} catch (IOException e) {
			throw new InvalidDtoConvertionException(MessageFormat.format("Cannot serialize DTO of class {0}. Error: {1}", dto.getClass().getName(), e.getMessage()));
		}
	}
	
	public static <T> T bytesToDto(byte[] data,  Class<T> dtoClass) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			T dto = objectMapper.readValue(data, dtoClass);
			return dto;
		} catch (IOException e) {
			throw new InvalidDtoConvertionException(MessageFormat.format("Cannot deserialize class {0} to a DTO object. Error: {1}", dtoClass.getName(), e.getMessage()));
		}
		
	}

}
