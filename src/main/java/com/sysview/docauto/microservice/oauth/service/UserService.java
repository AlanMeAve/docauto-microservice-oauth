package com.sysview.docauto.microservice.oauth.service;

import com.sysview.docauto.microservice.oauth.model.User;

public interface UserService {
	
	User findByUsername(String username);

}
