package com.spring.dao;

import java.util.concurrent.atomic.AtomicInteger;

import com.spring.model.WorkflowStepDetails;

public interface IJobTrackingDao {

	String SELECT_JOBTRACKINGID = "select IFNULL(max(JOB_TRACKING_ID),0) job_id from job_tracking";
	String SELECT_JOBID = "select IFNULL(max(ID),0) job_id from job";
	String SELECT_PREVIOUSJOBID = "select IFNULL(max(JOB_TRACKING_ID),0) job_id from job_tracking where is_complete=0 and workflow_step_id=:wstepId and RECORD_REFERENCE=:recordId";

	String INSERT_JOBTRACKINGQUERY = "insert into job_tracking (WORKFLOW_ID,WORKFLOW_STEP_ID,IS_ACTIVE,"
			+ "IS_COMPLETE,START_DATE,CREATED_BY,CREATED_ON,JOB_TRACKING_ID,job_id,record_reference) values (:wrkflid,:wrkflwStepId,:isActive,"
			+ ":isComplete,curdate(),:userCreated,curdate(),:jobTrackingId,:jobId,:refernceCd)";

	String UPDATE_QUERY = "update job_tracking set action_id=:actionId, is_complete=1 , end_date=curdate(), updated_by=:updateUser,"
			+ " updated_on=curdate(),is_active=0 where JOB_TRACKING_ID=:jobTrackId";

	String INSERT_JOBQUERY = "insert into JOB (ID,WORKFLOW_ID,RECORD_REFERENCE,IS_COMPLETE) values (:jobId,:workflowId,:recordRefernceId,:isComplete)";

	String CHECKEXISTINGJOB_QUERY = "select IFNULL(id,0) from job where RECORD_REFERENCE=:recordId and WORKFLOW_ID=:workflowId and is_complete=0";

	String UPDATE_JOBTABLE = "update job set IS_COMPLETE=1 where id=:id";

	String SELECT_JOBSTATUS = "select WORKFLOW_STEP_ID from job_tracking where JOB_ID=:jobId and IS_COMPLETE=0";

	String SELECT_WORKFLOWSTEPID = "select workflow_step_id from job_tracking where JOB_TRACKING_ID=:jobTrackingId";

	public static AtomicInteger jobTrackingKey = new AtomicInteger();

	public static AtomicInteger jobKey = new AtomicInteger();

	public int getJobTrackingId();

	public int getJobID();

	public boolean updateJobTrackingId(int jobTrackid, int actionId,
			String user);

	public int getIncompleteJobId(int workflowStepId, String recordId);

	public int populateJobTracking(WorkflowStepDetails pWorkflowStepDetailsObj,
			String userName, String refernceCd, int jobKey);

	public int insertJob(int pWorkflowId, String recordRefernce);

	public int getExistingJobId(String pRecordId, int workflowId);

	public void updateJobOnCompletion(int pJobId);

	public int getCurrentJobStatus(int pJobId);

	public int getWorkflowStepId(int pJobTrackigId);

}
