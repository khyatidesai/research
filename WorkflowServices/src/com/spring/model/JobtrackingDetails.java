package com.spring.model;

public class JobtrackingDetails {

	int WORKFLOW_ID;
	int WORKFLOW_STEP_ID;
	char STATUS;
	int IS_ACTIVE;
	int IS_COMPLETE;
	String CREATED_BY;
	String UPDATED_BY;
	int JOB_TRACKING_ID;

	JobtrackingDetails(int pWorkflowId, int pWorkflowStepId, char pStatus,
			int pIsActive, int pIsComplete) {

	}

	public int getJOB_TRACKING_ID() {
		return JOB_TRACKING_ID;
	}

	public int getWORKFLOW_ID() {
		return WORKFLOW_ID;
	}

	public int getWORKFLOW_STEP_ID() {
		return WORKFLOW_STEP_ID;
	}

	public char getSTATUS() {
		return STATUS;
	}

	public int getIS_ACTIVE() {
		return IS_ACTIVE;
	}

	public int getIS_COMPLETE() {
		return IS_COMPLETE;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public String getUPDATED_BY() {
		return UPDATED_BY;
	}

}
