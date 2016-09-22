package com.spring.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class WorkflowResponse {

	private int jobTrackingId;
	private List<WorkflowActionDetails> actions;
	private String referenceCd;
	private String message;
	private Boolean isActive;

	public List<WorkflowActionDetails> getActions() {
		if (actions == null) {
			actions = new ArrayList<WorkflowActionDetails>();
		}
		return actions;
	}

	public void setActions(List<WorkflowActionDetails> actions) {
		this.actions = actions;
	}

	public int getJobTkId() {
		return jobTrackingId;
	}

	public void setJobTkId(int jobTkId) {
		this.jobTrackingId = jobTkId;
	}

	public String getReferenceCd() {
		return referenceCd;
	}

	public void setReferenceCd(String referenceCd) {
		this.referenceCd = referenceCd;
	}

	public int getJobTrackingId() {
		return jobTrackingId;
	}

	public void setJobTrackingId(int jobTrackingId) {
		this.jobTrackingId = jobTrackingId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
