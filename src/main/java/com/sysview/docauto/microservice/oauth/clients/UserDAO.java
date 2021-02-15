package com.sysview.docauto.microservice.oauth.clients;

import com.sysview.docauto.microservice.oauth.model.User;

public interface UserDAO {
	
	public User findByUsername(String username);

}
