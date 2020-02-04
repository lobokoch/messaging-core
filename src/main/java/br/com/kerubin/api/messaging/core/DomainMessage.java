package br.com.kerubin.api.messaging.core;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DomainMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String APPLICATION = "kerubin";
	
	@Builder.Default
	private String application = APPLICATION;
	
	private String tenant;
	private String domain;
	private String service;
	private String primitive;
	private String user;
	private String tenantAccountType;
	
	private byte[] payload;

}
