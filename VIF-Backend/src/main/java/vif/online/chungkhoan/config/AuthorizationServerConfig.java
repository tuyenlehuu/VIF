package vif.online.chungkhoan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	@Value("${security.oath2.client-id}")
	private String CLIEN_ID;
	
	@Value("${security.oath2.client-secret}")
	private String CLIENT_SECRET;
	
	@Value("${security.oath2.grant-type}")
	private String GRANT_TYPE;
	
	@Value("${security.oath2.authorization-code}")
	private String AUTHORIZATION_CODE;
	
	@Value("${security.oath2.refresh-token}")
	private String REFRESH_TOKEN;
	
	@Value("${security.oath2.implicit}")
	private String IMPLICIT;
	
	@Value("${security.oath2.scope-read}")
	private String SCOPE_READ;
	
	@Value("${security.oath2.scope-write}")
	private String SCOPE_WRITE;
	
	@Value("${security.oath2.trust}")
	private String TRUST;
	
	private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
	
	private static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.inMemory()
		.withClient(CLIEN_ID)
		.secret(CLIENT_SECRET)
		.authorizedGrantTypes(GRANT_TYPE, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
		.scopes(SCOPE_READ, SCOPE_WRITE)
		.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
		.refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
		.authenticationManager(authenticationManager);
	}
}
