package br.com.kerubin.api.messaging.core;

public class DomainEventEnvelopeBuilder<T extends DomainEvent> {
	
	private String domain;
	private String service;
	private String primitive;
	private String key;
	private String tenant;
	private String user;
	private T payload;
	
	private DomainEventEnvelopeBuilder(String primitive, T payload) {
		this.primitive = primitive;
		this.payload = payload;
	}
	
	public static <T> DomainEventEnvelopeBuilder<DomainEvent> getBuilder(String primitive, DomainEvent payload) {
		return new DomainEventEnvelopeBuilder<DomainEvent>(primitive, payload);
	}

	public String getDomain() {
		return domain;
	}

	public DomainEventEnvelopeBuilder<T> domain(String domain) {
		this.domain = domain;
		return this;
	}

	public String getService() {
		return service;
	}

	public DomainEventEnvelopeBuilder<T> service(String service) {
		this.service = service;
		return this;
	}

	public String getTenant() {
		return tenant;
	}

	public DomainEventEnvelopeBuilder<T> tenant(String tenant) {
		this.tenant = tenant;
		return this;
	}

	public String getPrimitive() {
		return primitive;
	}
	
	public String getKey() {
		return key;
	}
	
	public DomainEventEnvelopeBuilder<T> key(String key) {
		this.key = key;
		return this;
	}
	
	public DomainEventEnvelopeBuilder<T> primitive(String primitive) {
		this.primitive = primitive;
		return this;
	}

	public T getPayload() {
		return payload;
	}

	public DomainEventEnvelopeBuilder<T> payload(T payload) {
		this.payload = payload;
		return this;
	}
	
	public DomainEventEnvelope<T> build() {
		return new DomainEventEnvelope<T>(this);
	}

	public DomainEventEnvelopeBuilder<T> user(String user) {
		this.user = user;
		return this;
	}
	
	public String getUser() {
		return user;
	}

}
