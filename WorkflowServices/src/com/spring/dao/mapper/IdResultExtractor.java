package com.spring.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class IdResultExtractor implements ResultSetExtractor<Integer> {

	public Integer extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		// TODO Auto-generated method stub
		int jobId = 0;
		if (rs.next()) {
			jobId = rs.getInt(1);
		}
		return jobId;
	}

}
