package com.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.spring.dao.JobTrackingDaoImpl;
import com.spring.dao.WorkflowDetailDaoImpl;
import com.spring.model.WorkflowActionDetails;
import com.spring.model.WorkflowRequest;
import com.spring.model.WorkflowResponse;
import com.spring.model.WorkflowStepDetails;

@Service
public class WorkFlowService {
	@Autowired
	WorkflowDetailDaoImpl wrkflDao;
	@Autowired
	JobTrackingDaoImpl jobTrackDao;
	@Autowired
	WorkflowResponse workflowResp;

	public WorkflowStepDetails getWorkflowStepDetails(int pWorkFlowId,
			boolean forWorkflowDetails) {
		return wrkflDao.getWorkflowStepDetails(pWorkFlowId, forWorkflowDetails);
	}

	public WorkflowResponse getResponseForWorkflowId(int pWorkflowStepId,
			int pJobTrackingId, String preferenceCode) {
		workflowResp.setJobTkId(pJobTrackingId);
		workflowResp.setActions(
				wrkflDao.getActionDetailsforWorkflowStep(pWorkflowStepId));
		workflowResp.setReferenceCd(preferenceCode);
		return workflowResp;

	}

	public int updateAndGetNextStep(WorkflowRequest workflowReq) {
		jobTrackDao.updateJobTrackingId(workflowReq.getJobTrackingId(),
				workflowReq.getActionId(), workflowReq.getUsername());
		int currentWorkflowStepId = jobTrackDao
				.getWorkflowStepId(workflowReq.getJobTrackingId());
		int suceedingStep = wrkflDao.getSuceedingStepDetails(
				currentWorkflowStepId, workflowReq.getActionId());
		System.out.println("suceedingStep" + suceedingStep);

		return suceedingStep;

	}

	public int createJob(int pWorkflowId, String pUserName, String pRecordId,
			WorkflowStepDetails pWorkFlowStepObj) {
		int jobId = getExistingJobId(pRecordId, pWorkflowId);
		System.out.println("jobID" + jobId);

		if (jobId == 0) {
			jobId = jobTrackDao.insertJob(pWorkflowId, pRecordId);
		} else {
			jobId = -1;
		}
		return jobId;

	}

	public int createJobTracking(WorkflowStepDetails pWorkFlowStepObj,
			String pUserName, String pRecordId, int pJobKey) {
		return jobTrackDao.populateJobTracking(pWorkFlowStepObj, pUserName,
				pRecordId, pJobKey);
	}

	public boolean checkTaskPermissionForRole(int ptaskId, int pRoleId) {
		return wrkflDao.checkRoleTaskPermissions(ptaskId, pRoleId);
	}

	public void updateJobOnCompletion(int pJobId) {
		jobTrackDao.updateJobOnCompletion(pJobId);
	}

	public int getExistingJobId(String recordId, int pWorkflowId) {
		return jobTrackDao.getExistingJobId(recordId, pWorkflowId);
	}

	public String getStringForView(int pWorkflowId, int pRoleId,
			String pReferenceCd, Gson gson) {
		String returnString = null;
		int jobId = getExistingJobId(pReferenceCd, pWorkflowId);
		System.out.println("jobId : " + jobId);

		int workflowStepId = jobTrackDao.getCurrentJobStatus(jobId);
		System.out.println("workflowStepId : " + workflowStepId);

		int jobTrackingId = jobTrackDao.getIncompleteJobId(workflowStepId,
				pReferenceCd);
		System.out.println("jobTrackingId : " + jobTrackingId);

		WorkflowStepDetails workFlowStepObj = wrkflDao
				.getWorkflowStepDetails(workflowStepId, false);

		if (checkTaskPermissionForRole(workFlowStepObj.getTaskId(), pRoleId)) {
			workflowResp = getResponseForWorkflowId(workflowStepId,
					jobTrackingId, pReferenceCd);
			workflowResp.setIsActive(true);
			returnString = gson.toJson(workflowResp);
		} else {
			List<WorkflowActionDetails> list = new ArrayList<WorkflowActionDetails>();
			workflowResp.setIsActive(false);
			list.add(new WorkflowActionDetails(0,
					workFlowStepObj.getPendingActionTask()));
			workflowResp.setActions(list);
			returnString = gson.toJson(workflowResp);
		}

		return returnString;

	}

	public WorkflowResponse getWorkflowStatus(int pWorkflowId, int pRoleId,
			String pReferenceCd) {

		String returnString = null;
		int jobId = getExistingJobId(pReferenceCd, pWorkflowId);
		System.out.println("jobId : " + jobId);

		int workflowStepId = jobTrackDao.getCurrentJobStatus(jobId);
		System.out.println("workflowStepId : " + workflowStepId);

		int jobTrackingId = jobTrackDao.getIncompleteJobId(workflowStepId,
				pReferenceCd);
		System.out.println("jobTrackingId : " + jobTrackingId);

		WorkflowStepDetails workFlowStepObj = wrkflDao
				.getWorkflowStepDetails(workflowStepId, false);

		if (checkTaskPermissionForRole(workFlowStepObj.getTaskId(), pRoleId)) {
			workflowResp = getResponseForWorkflowId(workflowStepId,
					jobTrackingId, pReferenceCd);
			workflowResp.setIsActive(true);

		} else {
			List<WorkflowActionDetails> list = new ArrayList<WorkflowActionDetails>();
			workflowResp.setIsActive(false);
			list.add(new WorkflowActionDetails(0,
					workFlowStepObj.getPendingActionTask()));
			workflowResp.setActions(list);

		}

		return workflowResp;

	}

}
