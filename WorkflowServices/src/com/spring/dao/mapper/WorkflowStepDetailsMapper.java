package com.spring.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.spring.model.WorkflowStepDetails;

public class WorkflowStepDetailsMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		int workflowStepId = rs.getInt("WORKFLOW_STEP_ID");
		int workflowId = rs.getInt("WORKFLOW_ID");
		int taskId = rs.getInt("TASK_ID");
		String preceedingStep = rs.getString("PRECEDING_STEPS");
		int sequnenceNo = rs.getInt("SEQUENCE");
		// String suceedingStep = rs.getString("SUCCEEDING_STEPS");
		int isMandatory = rs.getInt("IS_MANDATORY");
		int isLast = rs.getInt("IS_LAST");
		String pendingActiontask = rs.getString("pending_action_text");
		// String recordReferenceId = rs.getString("RECORD_TYPE_ID");
		WorkflowStepDetails wrkflw = new WorkflowStepDetails(workflowStepId,
				workflowId, taskId, preceedingStep, sequnenceNo, isMandatory,
				isLast, pendingActiontask);

		return wrkflw;

	}

}
