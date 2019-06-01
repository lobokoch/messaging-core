package br.com.kerubin.api.messaging.core;

public class DomainEventEnvelope<T extends DomainEvent> {
	
private static final String APPLICATION = "kerubin";
	
	final private String application = APPLICATION;
	private String domain;
	private String service;
	private String tenant;
	private String primitive;
	private String key;
	private String user;
	
	private T payload;
	
	public DomainEventEnvelope() {
		
	}
	
	public DomainEventEnvelope(DomainEventEnvelopeBuilder<T> builder) {
		this.domain = builder.getDomain();
		this.service = builder.getService();
		this.primitive = builder.getPrimitive();
		this.tenant = builder.getTenant();
		this.payload = builder.getPayload();
		this.key = builder.getKey();
		this.user = builder.getUser();
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getPrimitive() {
		return primitive;
	}

	public void setPrimitive(String primitive) {
		this.primitive = primitive;
	}

	public String getApplication() {
		return application;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
}
