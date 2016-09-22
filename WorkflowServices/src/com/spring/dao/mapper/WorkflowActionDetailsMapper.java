package com.spring.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.spring.model.WorkflowActionDetails;

public class WorkflowActionDetailsMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		int actionId = rs.getInt("ACTION_ID");
		String buttonText = rs.getString("BUTTON_TEXT");

		WorkflowActionDetails wrkflw = new WorkflowActionDetails(actionId,
				buttonText);

		return wrkflw;

	}

}
