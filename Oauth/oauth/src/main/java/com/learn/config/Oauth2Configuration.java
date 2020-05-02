package com.learn.config;

import javax.sql.DataSource;

import org.apache.catalina.util.CustomObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class Oauth2Configuration extends AuthorizationServerConfigurerAdapter {

	@Value("${check-user-scopes}")
	private boolean checkUsersScope;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private ClientDetailsService clientDetailService;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Bean
	public OAuth2RequestFactory requestFactory() {
		CustomOauth2RequestFactory requestFactory = new CustomOauth2RequestFactory(clientDetailService);
		requestFactory.setCheckUserScopes(true);
		return requestFactory;
	}

	@Bean
	public TokenStore tokenStore()
	{
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter()
	{
		JwtAccessTokenConverter converter=new JwtAccessTokenConverter();
		converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource("jwk.jts"),"password".toCharArray()).getKeyPair("jwt"));
		return converter;
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer client) throws Exception
	{
		client.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}
	
	
	@Bean
	public TokenEndpointAuthenticationFilter tokenEndPointAuthenticationFilter()
	{
		return new TokenEndpointAuthenticationFilter(authenticationManager, requestFactory());
	}
	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception
	{
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isTokenAuthenticated()"); 
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
	{
		endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter())
		.authenticationManager(authenticationManager).userDetailsService(userDetailService);
if (checkUsersScope)
	endpoints.requestFactory(requestFactory());
	}
}
