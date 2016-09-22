package com.spring.model;

import java.util.List;

public class WorkflowRequest {

	private int actionId;
	private final int workflowId;
	private int jobTrackingId;
	private String username;
	private final int roleId;
	private String referenceCd;
	private List<String> referenceCdList;

	public WorkflowRequest(int actionId, int workflowId, int jobTrackingId,
			String username, int roleId, String referencecd) {
		super();
		this.actionId = actionId;
		this.workflowId = workflowId;
		this.jobTrackingId = jobTrackingId;
		this.username = username;
		this.roleId = roleId;
		this.referenceCd = referencecd;
	}

	public WorkflowRequest(int workflowId, int roleId,
			List<String> referencecdList) {
		this.workflowId = workflowId;
		this.roleId = roleId;
		this.referenceCdList = referencecdList;
	}

	public List<String> getReferenceCdList() {
		return referenceCdList;
	}

	public int getActionId() {
		return actionId;
	}

	public int getWorkflowId() {
		return workflowId;
	}

	public int getJobTrackingId() {
		return jobTrackingId;
	}

	public String getUsername() {
		return username;
	}

	public int getRoleId() {
		return roleId;
	}

	public String getReferenceCd() {
		return referenceCd;
	}

}
