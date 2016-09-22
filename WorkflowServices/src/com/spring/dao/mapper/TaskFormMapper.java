package com.spring.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TaskFormMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		return rs.getString("form_view");
	}
}