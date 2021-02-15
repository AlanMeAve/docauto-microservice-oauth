package com.sysview.docauto.microservice.oauth.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sysview.docauto.microservice.oauth.clients.UserDAO;
import com.sysview.docauto.microservice.oauth.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

	@Autowired JdbcTemplate jdbcTemplate;
	
	public class UserMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setName(rs.getString("USUARIOPROPIONOMBRE"));
			user.setFirstSurname(rs.getString("USUARIOPROPIOPATERNO"));
			user.setSecondSurname(rs.getString("USUARIOPROPIOMATERNO"));
			user.setUserName(rs.getString("USUARIOPROPIO"));
			user.setPassword(rs.getString("USUARIOPROPIOPASSWORD"));
			user.setEmail(rs.getString("USUARIOPROPIOCORREO"));
			user.setIsActive(rs.getBoolean("ACTIVO"));
			user.setRole(rs.getString("ROL"));
			return user;
		}
	}
	
	private static final String SQL_FIND_BY_USERNAME = "SELECT USUARIOPROPIO, USUARIOPROPIONOMBRE, USUARIOPROPIOPATERNO, USUARIOPROPIOMATERNO, USUARIOPROPIOPASSWORD, USUARIOPROPIOCORREO, ROL, ACTIVO FROM USUARIOPROPIO WHERE USUARIOPROPIO = ?";
	@Override
	public User findByUsername(String username) {
		try {
			return jdbcTemplate.queryForObject(SQL_FIND_BY_USERNAME, new UserMapper(), username);
		} catch (EmptyResultDataAccessException ex) {
			log.error("User not found: {0} ", ex);
			return null;
		}
	}
	
}
