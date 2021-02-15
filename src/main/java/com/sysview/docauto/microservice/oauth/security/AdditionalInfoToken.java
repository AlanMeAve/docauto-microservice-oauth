package com.sysview.docauto.microservice.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.sysview.docauto.microservice.oauth.model.User;
import com.sysview.docauto.microservice.oauth.service.UserService;

@Component
public class AdditionalInfoToken implements TokenEnhancer {

	@Autowired UserService userService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = userService.findByUsername(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("id", user.getId());
		info.put("name", user.getName());
		info.put("firstSurname", user.getFirstSurname());
		info.put("secondSurname", user.getSecondSurname());
		info.put("userName", user.getUserName());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
