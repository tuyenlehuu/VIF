package vif.online.chungkhoan.config;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3456397758992071022L;

	public CustomOauthException(String msg) {
        super(msg);
    }
}
