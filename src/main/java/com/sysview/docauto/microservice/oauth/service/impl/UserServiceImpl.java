package com.sysview.docauto.microservice.oauth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sysview.docauto.microservice.oauth.clients.UserDAO;
import com.sysview.docauto.microservice.oauth.model.User;
import com.sysview.docauto.microservice.oauth.service.UserService;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired UserDAO userFeignClient;
	@Autowired BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userFeignClient.findByUsername(username);
			if(user == null) {
				throw new UsernameNotFoundException("No existe el usuario '".concat(username).concat("' en el sistema."));
			} else if (user.getIsActive().equals(Boolean.FALSE)) {
				throw new UsernameNotFoundException("Usuario inactivo");
			}
			
			if(user.getRole() == null || user.getRole().isEmpty()) {
				throw new UsernameNotFoundException("No tienes suficientes privilegios.");
			}
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_".concat(user.getRole())));
			return new org.springframework.security.core.userdetails.User(user.getUserName(), passwordEncoder.encode(user.getPassword()), true, true, true, true, authorities);
		} catch (Exception e) {
			String msg = "Error al iniciar sesión. ".concat(e.getMessage());
			log.error(msg);
			throw new UsernameNotFoundException(msg);
		}
	}

	@Override
	public User findByUsername(String username) {
		return userFeignClient.findByUsername(username);
	}

}
