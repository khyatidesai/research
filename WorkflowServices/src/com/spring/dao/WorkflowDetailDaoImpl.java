package com.spring.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.dao.mapper.IdResultExtractor;
import com.spring.dao.mapper.TaskFormMapper;
import com.spring.dao.mapper.WorkflowActionDetailsMapper;
import com.spring.dao.mapper.WorkflowStepDetailsMapper;
import com.spring.model.WorkflowActionDetails;
import com.spring.model.WorkflowStepDetails;

@Repository
public class WorkflowDetailDaoImpl extends NamedParameterJdbcDaoSupport
		implements IWorkflowDetailDao {

	@Autowired
	private NamedParameterJdbcTemplate daoTemp;

	@Autowired
	public WorkflowDetailDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	public WorkflowStepDetails getWorkflowStepDetails(int pWorkflowId,
			boolean isStepDetail) {
		WorkflowStepDetails workflowStepDetailsObject;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", pWorkflowId);
		if (isStepDetail) {
			workflowStepDetailsObject = daoTemp.queryForObject(
					IWorkflowDetailDao.SELECT, params,
					new WorkflowStepDetailsMapper());
		} else {
			workflowStepDetailsObject = daoTemp.queryForObject(
					IWorkflowDetailDao.SELECT_WORKSTEPID, params,
					new WorkflowStepDetailsMapper());
		}

		return workflowStepDetailsObject;

	}

	public List<WorkflowActionDetails> getActionDetailsforWorkflowStep(
			int pWorkflowStpId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wrflStpId", pWorkflowStpId);
		List<WorkflowActionDetails> workflowActionDetailsObject = daoTemp.query(
				IWorkflowDetailDao.SELECT_ACTION, params,
				new WorkflowActionDetailsMapper());
		return workflowActionDetailsObject;
	}

	public List<String> getFormDetails(int ptaskId) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tskId", ptaskId);
		List<String> listOfFormName = daoTemp.query(
				IWorkflowDetailDao.SELECT_FORM, params, new TaskFormMapper());
		return listOfFormName;
	}

	public Integer getSuceedingStepDetails(int workflowStepId, int actionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wrflStpId", workflowStepId);
		params.put("actionId", actionId);
		Integer suceedinStep = daoTemp.queryForObject(
				IWorkflowDetailDao.SELECT_SUCEEDINGSTEP, params, Integer.class);
		return suceedinStep;
	}

	public boolean checkRoleTaskPermissions(int pTaskId, int pRoleId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", pTaskId);
		params.put("roleId", pRoleId);
		if (daoTemp.query(IWorkflowDetailDao.SELECT_TASKPERMISSIONS, params,
				new IdResultExtractor()) == 1) {
			return true;
		} else {
			return false;
		}
	}

}
