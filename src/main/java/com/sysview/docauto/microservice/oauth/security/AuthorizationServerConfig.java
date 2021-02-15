package com.sysview.docauto.microservice.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired BCryptPasswordEncoder passwordEncoder;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired AdditionalInfoToken additionalInfoToken;
	
	@Value("${config.security.oauth.jwt.signing.key}")
	private String signingKey;
	
	@Value("${app.angular.name}")
	private String appAngularName;
	
	@Value("${app.angular.password}")
	private String appAngularPassword;
	
	@Value("${app.angular.authorized.grant.types}")
	private String appAngularAuthorizedGrantTypes;
	
	@Value("${app.angular.access.token.validity.seconds}")
	private Integer appAngularAccessTokenValiditySeconds;
	
	@Value("${app.angular.scope.one}")
	private String appAngularScopeOne;
	
	@Value("${app.angular.scope.two}")
	private String appAngularScopeTwo;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(appAngularName)
		.secret(passwordEncoder.encode(appAngularPassword)) 
		.scopes(appAngularScopeOne, appAngularScopeTwo)
		.authorizedGrantTypes(appAngularAuthorizedGrantTypes, "refresh_token")
		.accessTokenValiditySeconds(appAngularAccessTokenValiditySeconds);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(additionalInfoToken, accessTokenConverter()));
		endpoints.authenticationManager(authenticationManager)
				 .accessTokenConverter(accessTokenConverter())
				 .tokenEnhancer(tokenEnhancerChain);
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(signingKey);
		return tokenConverter;
	}
	
}
