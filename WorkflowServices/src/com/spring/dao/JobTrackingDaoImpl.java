package com.spring.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.dao.mapper.IdResultExtractor;
import com.spring.model.WorkflowStepDetails;

@Repository
public class JobTrackingDaoImpl extends NamedParameterJdbcDaoSupport
		implements IJobTrackingDao {

	@Autowired
	private NamedParameterJdbcTemplate daoTemp;

	@Autowired
	public JobTrackingDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
	}

	public int getJobTrackingId() {
		// TODO Auto-generated method stub
		return daoTemp.query(IJobTrackingDao.SELECT_JOBTRACKINGID,
				new IdResultExtractor());
	}

	public int getJobID() {
		// TODO Auto-generated method stub
		return daoTemp.query(IJobTrackingDao.SELECT_JOBID,
				new IdResultExtractor());
	}

	public boolean updateJobTrackingId(int jobTrackid, int actionId,
			String user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("actionId", actionId);
		params.put("jobTrackId", jobTrackid);
		params.put("updateUser", user);
		if (daoTemp.update(IJobTrackingDao.UPDATE_QUERY, params) > 0) {
			return true;
		} else {
			return false;
		}

	}

	public int getIncompleteJobId(int workflowStepId, String recordId) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wstepId", workflowStepId);
		params.put("recordId", recordId);
		return daoTemp.query(IJobTrackingDao.SELECT_PREVIOUSJOBID, params,
				new IdResultExtractor());
	}

	public int populateJobTracking(WorkflowStepDetails workflowObj,
			String userName, String refernceCd, int jobKey) {
		jobTrackingKey.set(getJobTrackingId());
		System.out.println(jobTrackingKey.get());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wrkflid", workflowObj.getWorkflowId());
		params.put("wrkflwStepId", workflowObj.getWorkflowStepId());
		params.put("userCreated", userName);
		params.put("isActive", 1);
		params.put("isComplete", 0);
		params.put("jobTrackingId", jobTrackingKey.incrementAndGet());
		params.put("jobId", jobKey);
		params.put("refernceCd", refernceCd);
		if (daoTemp.update(IJobTrackingDao.INSERT_JOBTRACKINGQUERY,
				params) > 0) {
			return jobTrackingKey.get();
		} else {
			return 0;
		}
	}

	public int insertJob(int pWorkflowId, String recordRefernce) {
		// TODO Auto-generated method stub
		jobKey.set(getJobID());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workflowId", pWorkflowId);
		params.put("recordRefernceId", recordRefernce);
		params.put("jobId", jobKey.incrementAndGet());
		params.put("isComplete", 0);
		if (daoTemp.update(IJobTrackingDao.INSERT_JOBQUERY, params) > 0) {
			return jobKey.get();
		} else {
			return 0;
		}
	}

	public int getExistingJobId(String pRecordId, int pWorkflowId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("recordId", pRecordId);
		params.put("workflowId", pWorkflowId);
		return daoTemp.query(IJobTrackingDao.CHECKEXISTINGJOB_QUERY, params,
				new IdResultExtractor());
	}

	public void updateJobOnCompletion(int pJobId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", pJobId);
		daoTemp.update(IJobTrackingDao.UPDATE_JOBTABLE, params);

	}

	public int getCurrentJobStatus(int pJobId) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobId", pJobId);
		return daoTemp.query(IJobTrackingDao.SELECT_JOBSTATUS, params,
				new IdResultExtractor());

	}

	public int getWorkflowStepId(int pJobTrackigId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobTrackingId", pJobTrackigId);
		return daoTemp.query(IJobTrackingDao.SELECT_WORKFLOWSTEPID, params,
				new IdResultExtractor());
	}

}
